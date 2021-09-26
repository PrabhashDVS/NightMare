package com.example.orderapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderapp.Database.OrderContract;

public class nokiamacActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // first of all we will get the views that are  present in the layout of info

    ImageView imageView;
    ImageButton plusquantity, minusquantity;
    TextView quantitynumber, phoneName, mobilePrice;
    CheckBox adddbackcover, addtemperd;
    Button addtoCart;
    int quantity;
    public Uri mCurrentCartUri;
    boolean hasAllRequiredValues = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = findViewById(R.id.imageViewInfo);
        plusquantity = findViewById(R.id.addquantity);
        minusquantity  = findViewById(R.id.subquantity);
        quantitynumber = findViewById(R.id.quantity);
        phoneName = findViewById(R.id.PhoneNameinInfo);
        mobilePrice = findViewById(R.id.mobilePrice);
        adddbackcover = findViewById(R.id.addbackcover);
        addtoCart = findViewById(R.id.addtocart);
        addtemperd = findViewById(R.id.addTemperdGlass);

        // setting the name of drink

        phoneName.setText("NOKIA Mac");
        imageView.setImageResource(R.drawable.nokiyamac);

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nokiamacActivity.this, SummaryActivity.class);
                startActivity(intent);
                // once this button is clicked we want to save our values in the database and send those values
                // right away to summary activity where we display the order info

                SaveCart();
            }
        });

        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // phone price
                int basePrice = 100000;
                quantity++;
                displayQuantity();
                int mPrice = basePrice * quantity;
                String setnewPrice = String.valueOf(mPrice);
                mobilePrice.setText(setnewPrice);


                // checkBoxes functionality

                int ifCheckBox = CalculatePrice(addtemperd, adddbackcover);
                mobilePrice.setText("Rs " + ifCheckBox);

            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int basePrice = 100000;
                // because we dont want the quantity go less than 0
                if (quantity == 0) {
                    Toast.makeText(nokiamacActivity.this, "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                    int mPrice = basePrice * quantity;
                    String setnewPrice = String.valueOf(mPrice);
                    mobilePrice.setText(setnewPrice);


                    // checkBoxes functionality

                    int ifCheckBox = CalculatePrice(addtemperd, adddbackcover);
                    mobilePrice.setText("Rs " + ifCheckBox);
                }
            }
        });



    }

    private boolean SaveCart() {

        // getting the values from our views
        String name = phoneName.getText().toString();
        String price = mobilePrice.getText().toString();
        String quantity = quantitynumber.getText().toString();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME, name);
        values.put(OrderContract.OrderEntry.COLUMN_PRICE, price);
        values.put(OrderContract.OrderEntry.COLUMN_QUANTITY, quantity);

        if (addtemperd.isChecked()) {
            values.put(OrderContract.OrderEntry.COLUMN_CREAM, "Black: YES");
        } else {
            values.put(OrderContract.OrderEntry.COLUMN_CREAM, "Black: NO");

        }

        if (adddbackcover.isChecked()) {
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING, "Dark Blue: YES");
        } else {
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING, "Dark Blue: NO");

        }

        if (mCurrentCartUri == null) {
            Uri newUri = getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI, values);
            if (newUri==null) {
                Toast.makeText(this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Success  adding to Cart", Toast.LENGTH_SHORT).show();

            }
        }

        hasAllRequiredValues = true;
        return hasAllRequiredValues;

    }

    private int CalculatePrice(CheckBox addBackcover, CheckBox addTemperdGlass) {

        int basePrice = 100000;

        if (addBackcover.isChecked()) {
            // add the backcover cost rs400
            basePrice = basePrice + 700;
        }

        if (addTemperdGlass.isChecked()) {
            // topping cost is rs 700
            basePrice = basePrice + 400;
        }

        return basePrice * quantity;
    }

    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {OrderContract.OrderEntry._ID,
                OrderContract.OrderEntry.COLUMN_NAME,
                OrderContract.OrderEntry.COLUMN_PRICE,
                OrderContract.OrderEntry.COLUMN_QUANTITY,
                OrderContract.OrderEntry.COLUMN_CREAM,
                OrderContract.OrderEntry.COLUMN_HASTOPPING
        };

        return new CursorLoader(this, mCurrentCartUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {

            int name = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_NAME);
            int price = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_PRICE);
            int quantity = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_QUANTITY);
            int backcover = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_CREAM);
            int temperd = cursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_HASTOPPING);


            String nameofphone = cursor.getString(name);
            String priceofphone = cursor.getString(price);
            String quantityofphone = cursor.getString(quantity);
            String yeshasbackcover = cursor.getString(backcover);
            String yeshastemperd = cursor.getString(temperd);

            phoneName.setText(nameofphone);
            mobilePrice.setText(priceofphone);
            quantitynumber.setText(quantityofphone);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


        phoneName.setText("");
        mobilePrice.setText("");
        quantitynumber.setText("");

    }
}