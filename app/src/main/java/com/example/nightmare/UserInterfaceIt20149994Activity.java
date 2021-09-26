package com.example.mad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad.database.DBHelperIT20149994;

import java.util.List;

public class UserInterfaceIt20149994Activity extends AppCompatActivity {

    EditText etDeviceName;
    EditText etManufacturer;
    EditText etYear;
    EditText etPrice;
    EditText etSpecialNotes;
    EditText etDeviceID;
    EditText etusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface_it20149994);

        Intent intent = getIntent();
        String username = intent.getStringExtra(MainIt20149994Activity.EXTRA_USERNAME);
        etusername = findViewById(R.id.etusername);
        etusername.setText(username);

        etDeviceName = findViewById(R.id.etDeviceName);
        etManufacturer = findViewById(R.id.etManufacturer);
        etYear = findViewById(R.id.etYear);
        etPrice = findViewById(R.id.etPrice);
        etSpecialNotes = findViewById(R.id.etSpecialNotes);
        etDeviceID = findViewById(R.id.etDeviceID);
        etusername = findViewById(R.id.etusername);
    }


    // Save device details
    public void savePhone(View view) {
        String deviceName = etDeviceName.getText().toString();
        String manufacturer = etManufacturer.getText().toString();
        String year = etYear.getText().toString();
        String price = etPrice.getText().toString();
        String specialNotes = etSpecialNotes.getText().toString();
        String username = etusername.getText().toString();

        DBHelperIT20149994 dbHelper = new DBHelperIT20149994(this);

        if(deviceName.isEmpty()||manufacturer.isEmpty()||price.isEmpty()){
            Toast.makeText(this, "Device name / Manufacturer / Price must be filled", Toast.LENGTH_SHORT).show();
        }else{
            long inserted = dbHelper.addInfo(deviceName,manufacturer,year,price,specialNotes,username);

            if(inserted>0){
                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

                etDeviceName.setText("");
                etManufacturer.setText("");
                etYear.setText("");
                etPrice.setText("");
                etSpecialNotes.setText("");

            }else{
                Toast.makeText(this, "Operation is unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // Display all devices
    public void viewAll(View view){
        DBHelperIT20149994 dbHelper = new DBHelperIT20149994(this);
        String username = etusername.getText().toString();

        List info = dbHelper.readAll(username);

        String[] infoArray = (String[]) info.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Phones Details\nSelect a device to update or delete");

        builder.setItems(infoArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String deviceName = infoArray[i].split(":")[0];
                String manufacturer = infoArray[i].split(":")[1];
                String year = infoArray[i].split(":")[2];
                String price = infoArray[i].split(":")[3];
                String specialNotes = infoArray[i].split(":")[4];
                String deviceID = infoArray[i].split(":")[5];

                etDeviceName.setText(deviceName);
                etManufacturer.setText(manufacturer);
                etYear.setText(year);
                etPrice.setText(price);
                etSpecialNotes.setText(specialNotes);
                etDeviceID.setText(deviceID);

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    // Delete device details

    public void deletePhone(View view){
        DBHelperIT20149994 dbHelper = new DBHelperIT20149994(this);
        String deviceID = etDeviceID.getText().toString();

        if(deviceID.isEmpty()){
            Toast.makeText(this,"Select a phone",Toast.LENGTH_SHORT).show();
        }else{
            dbHelper.deleteInfo(deviceID);
            Toast.makeText(this,"Successfully deleted", Toast.LENGTH_SHORT).show();

            etDeviceName.setText("");
            etManufacturer.setText("");
            etYear.setText("");
            etPrice.setText("");
            etSpecialNotes.setText("");
        }
    }


    // Update device details

    public void updatePhone(View view){
        DBHelperIT20149994 dbHelper = new DBHelperIT20149994(this);
        String deviceName = etDeviceName.getText().toString();
        String manufacturer = etManufacturer.getText().toString();
        String year = etYear.getText().toString();
        String price = etPrice.getText().toString();
        String specialNotes = etSpecialNotes.getText().toString();
        String deviceID = etDeviceID.getText().toString();

        if(deviceName.isEmpty()||manufacturer.isEmpty()||price.isEmpty()){
            Toast.makeText(this, "Device name / Manufacturer / Price must be filled", Toast.LENGTH_SHORT).show();
        }else{
            dbHelper.updateInfo(view, deviceName, manufacturer, year, price, specialNotes, deviceID);
            etDeviceName.setText("");
            etManufacturer.setText("");
            etYear.setText("");
            etPrice.setText("");
            etSpecialNotes.setText("");
        }
    }


}