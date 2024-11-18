package com.example.restaurantorderapp;

import android.os.Bundle;
import android.widget.TextView; // Use TextView instead of EditText

import androidx.appcompat.app.AppCompatActivity;

public class ShowsSelectedData extends AppCompatActivity {
    private TextView summaryTextView; // Rename variable to better reflect its type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_selected_data);

        initializeUI();

        // Retrieve the data passed from the previous activity
        String selectedItems = getIntent().getStringExtra("SELECTED_ITEMS");

        // Format the data to display vertically
        if (selectedItems != null) {
            String formattedItems = selectedItems.replace(", ", "\n");
            summaryTextView.setText(formattedItems);
        } else {
            summaryTextView.setText("No items selected");
        }
    }

    private void initializeUI() {
        summaryTextView = findViewById(R.id.textView16); // Match the variable name
    }
}
