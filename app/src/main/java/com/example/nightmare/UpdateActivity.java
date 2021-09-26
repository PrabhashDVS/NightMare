package com.example.adddevices;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText brand_input, model_input, condition_input, price_input, description_input;
    Button update_button, delete_button;

    String title, id, model, brand, condition, price, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        brand_input = findViewById(R.id.brand_input2);
        model_input = findViewById(R.id.model_input2);
        condition_input = findViewById(R.id.condition_input2);
        price_input = findViewById(R.id.price_input2);
        description_input = findViewById(R.id.description_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            brand = brand_input.getText().toString().trim();
            model = model_input.getText().toString().trim();
            condition = condition_input.getText().toString().trim();
            price = price_input.getText().toString().trim();
            description = description_input.getText().toString().trim();
            myDB.updateData(id, condition, model, brand, price, description);

        });
        delete_button.setOnClickListener(view -> confirmDialog());

    }
    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("condition") && getIntent().hasExtra("model") &&
                getIntent().hasExtra("brand") && getIntent().hasExtra("price") && getIntent().hasExtra("description") &&
                getIntent().hasExtra("condition") ){
            //getting data from intent
            id = getIntent().getStringExtra("id");
            brand = getIntent().getStringExtra("brand");
            model = getIntent().getStringExtra("model");
            condition = getIntent().getStringExtra("condition");
            price = getIntent().getStringExtra("price");
            description = getIntent().getStringExtra("description");

            //setting intent data
            brand_input.setText(brand);
            model_input.setText(model);
            condition_input.setText(condition);
            price_input.setText(price);
            description_input.setText(description);
        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + model + " ?");
        builder.setMessage("Are you sure want to delete " + model + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}