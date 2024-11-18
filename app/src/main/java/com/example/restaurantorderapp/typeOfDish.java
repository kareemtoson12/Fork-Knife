package com.example.restaurantorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class typeOfDish extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_type_of_dish);

        initializeUI();
    }

    private void initializeUI() {
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);


        button1.setOnClickListener(v -> {
            Intent intent = new Intent(typeOfDish.this, Home.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(typeOfDish.this, sideDishActivity.class);
            startActivity(intent);
        });


    }
}