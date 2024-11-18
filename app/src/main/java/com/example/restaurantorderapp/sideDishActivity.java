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

public class sideDishActivity extends AppCompatActivity {

    private Spinner spinner4;    // Side dish spinner
    private Spinner spinner6;    // Sizes spinner
    private Spinner spinner7;
    // Extras spinner
    private Button b1;
    private EditText priceEditText;   // Total price display
    private EditText priceEditText2;  // Quantity input
    private List<String> sideCourseItemsWithPrices;
    private List<String> sizesWithPrices;
    private List<String> extrasWithPrices;

    private double sidePrice = 0.0;   // Price of the selected side dish
    private double sizePrice = 0.0;   // Price of the selected size
    private double extrasPrice = 0.0; // Price of the selected extras
    private double count = 0.0;       // Quantity, default to 1
    private double mainDishCost = 0.0; // Cost passed from the main dish

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_dish);

        initializeUI();
        setupSpinner4();
        setupSpinner6();
        setupSpinner3();
        setupCountListener();
        NavigatointoShowData();
        // Get the main dish cost passed from the Home activity
        mainDishCost = getIntent().getDoubleExtra("TOTAL_COST", 0.0);
        updateTotalPrice(); // Update the total price immediately
    }

    private void initializeUI() {
        spinner4 = findViewById(R.id.spinner4);   // Side dish spinner
        spinner6 = findViewById(R.id.spinner6);   // Sizes spinner
        spinner7 = findViewById(R.id.spinner7);   // Extras spinner

        b1=findViewById(R.id.button3);
        priceEditText2 = findViewById(R.id.editTextText3);  // Quantity input
        priceEditText = findViewById(R.id.editTextText4);   // Total price display

        Resturant restaurant = new Resturant();
        sideCourseItemsWithPrices = restaurant.getexSideDishWithPrices();
        sizesWithPrices = restaurant.getSizeItemsWithPrices();
        extrasWithPrices = restaurant.getextrasItemsWithPrices();
    }

    private void setupSpinner4() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sideCourseItemsWithPrices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sidePrice = extractPrice(sideCourseItemsWithPrices.get(position));
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sidePrice = 0.0;
                updateTotalPrice();
            }
        });
    }
    private void NavigatointoShowData() {
        b1.setOnClickListener(v -> {
            // Retrieve data passed from Home activity
            String previousSummary = getIntent().getStringExtra("SELECTED_ITEMS");

            // Add current selections to the summary
            String summary = previousSummary
                    + "Side Dish: " + spinner4.getSelectedItem().toString() + "\n"
                    + "Size: " + spinner6.getSelectedItem().toString() + "\n"
                    + "Extras: " + spinner7.getSelectedItem().toString() + "\n"
                    + "Quantity: " + count + "\n"
                    + "Total Cost: $" + String.format("%.2f", mainDishCost + (count * (sidePrice + sizePrice + extrasPrice))) + "\n";

            // Pass the summary to ShowsSelectedData
            Intent intent = new Intent(sideDishActivity.this, ShowsSelectedData.class);
            intent.putExtra("SELECTED_ITEMS", summary);
            startActivity(intent);
        });
    }

    private void setupSpinner6() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sizesWithPrices
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter);

        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        spinner7.setAdapter(adapter);

        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        // Calculate total price as the sum of main dish cost and side dish components
        double totalPrice = mainDishCost + count * (sidePrice + sizePrice + extrasPrice);
        priceEditText.setText(String.format("%.2f", totalPrice));
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
                Log.e("SideDishActivity", "Invalid price format: " + itemWithPrice, e);
                return 0.0;
            }
        }
        return 0.0;
    }
}
