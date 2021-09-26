package com.example.adddevices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText condition_input, model_input, brand_input, price_input, description_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        condition_input = findViewById(R.id.condition_input);
        model_input = findViewById(R.id.model_input);
        brand_input = findViewById(R.id.brand_input);
        price_input = findViewById(R.id.price_input);
        description_input = findViewById(R.id.description_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addDevices(condition_input.getText().toString().trim(),
                        model_input.getText().toString().trim(),
                        brand_input.getText().toString().trim(),
                        price_input.getText().toString().trim(),
                        description_input.getText().toString().trim());
            }
        });
    }
}