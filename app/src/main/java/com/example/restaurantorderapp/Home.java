package com.example.restaurantorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Home extends AppCompatActivity {

    private Spinner spinner;    // Main course
    private Spinner spinner2;   // Sizes
    private Spinner spinner3;   // Extras
    private Spinner spinner4;   // Drinks
    private Button GotoSidDish; // Button to navigate to Side Dish
    private EditText priceEditText;   // Total price display
    private EditText priceEditText2;  // Quantity input
    private List<String> mainCourseItemsWithPrices;
    private List<String> sizesWithPrices;
    private List<String> extrasWithPrices;
    private List<String> drinkItemsWithPrices;

    private double itemPrice = 0.0;
    private double sizePrice = 0.0;
    private double drinkPrice = 0.0;
    private double extrasPrice = 0.0;
    private double count = 0.0; // Default to 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeUI();
        setupSpinner();
        setupSpinner2();
        setupSpinner3();
        setupSpinner4();
        setupCountListener();
    }

    private void initializeUI() {
        spinner = findViewById(R.id.spinner);   // Main course spinner
        spinner2 = findViewById(R.id.spinner2); // Sizes spinner
        spinner3 = findViewById(R.id.spinner3); // Extras spinner
        spinner4 = findViewById(R.id.spinner5); // Drinks spinner
        GotoSidDish = findViewById(R.id.button4); // Navigate to Side Dish button
        priceEditText = findViewById(R.id.editTextText);   // Total price display
        priceEditText2 = findViewById(R.id.editTextText2); // Quantity input

        Resturant restaurant = new Resturant();
        mainCourseItemsWithPrices = restaurant.getMainCourseItemsWithPrices();
        sizesWithPrices = restaurant.getSizeItemsWithPrices();
        extrasWithPrices = restaurant.getextrasItemsWithPrices();
        drinkItemsWithPrices = restaurant.getexDrinkItemsWithPrices();

        GotoSidDish.setOnClickListener(v -> {
            // Collect the selected data
            String summary = "Main Course: " + spinner.getSelectedItem().toString() + "\n"
                    + "Size: " + spinner2.getSelectedItem().toString() + "\n"
                    + "Drink: " + spinner4.getSelectedItem().toString() + "\n"
                    + "Extras: " + spinner3.getSelectedItem().toString() + "\n"
                    + "Quantity: " + count + "\n";

            double totalCost = count * (itemPrice + sizePrice + extrasPrice + drinkPrice);

            // Pass the summary and total cost to SideDishActivity
            Intent intent = new Intent(Home.this, sideDishActivity.class);
            intent.putExtra("SELECTED_ITEMS", summary);
            intent.putExtra("TOTAL_COST", totalCost);
            startActivity(intent);
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                mainCourseItemsWithPrices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemPrice = extractPrice(mainCourseItemsWithPrices.get(position));
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemPrice = 0.0;
                updateTotalPrice();
            }
        });
    }

    private void setupSpinner2() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sizesWithPrices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sizePrice = extractPrice(sizesWithPrices.get(position));
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sizePrice = 0.0;
                updateTotalPrice();
            }
        });
    }

    private void setupSpinner3() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                extrasWithPrices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                extrasPrice = extractPrice(extrasWithPrices.get(position));
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                extrasPrice = 0.0;
                updateTotalPrice();
            }
        });
    }

    private void setupSpinner4() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                drinkItemsWithPrices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drinkPrice = extractPrice(drinkItemsWithPrices.get(position));
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                drinkPrice = 0.0;
                updateTotalPrice();
            }
        });
    }

    private void setupCountListener() {
        priceEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                setCount(); // Update count when text changes
            }
        });
    }

    private double extractPrice(String itemWithPrice) {
        if (itemWithPrice == null || !itemWithPrice.contains(" - $")) {
            return 0.0; // Handle invalid format
        }
        String[] parts = itemWithPrice.split(" - \\$");
        if (parts.length == 2) {
            try {
                return Double.parseDouble(parts[1]);
            } catch (NumberFormatException e) {
                Log.e("Home", "Invalid price format: " + itemWithPrice, e);
                return 0.0;
            }
        }
        return 0.0;
    }

    private void setCount() {
        String countText = priceEditText2.getText().toString().trim();
        if (countText.isEmpty()) {
            count = 1.0; // Default to 1 if empty
        } else {
            try {
                count = Double.parseDouble(countText);
            } catch (NumberFormatException e) {
                count = 1.0; // Default to 1 if parsing fails
                priceEditText2.setError("Invalid number format");
            }
        }
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = count * (itemPrice + sizePrice + extrasPrice + drinkPrice);
        priceEditText.setText(String.format("%.2f", totalPrice));
    }
}
