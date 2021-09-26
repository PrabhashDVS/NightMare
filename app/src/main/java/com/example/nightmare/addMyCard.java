package com.example.nightmare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class addMyCard extends AppCompatActivity {

    //register all the buttons and edit text
    EditText cardNoText, cardHolderNameText, monthYearText, cvv;
    Button btn_save;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_card);

        cardNoText = findViewById(R.id.cardNoText);
        cardHolderNameText = findViewById(R.id.cardHolderNameText);
        monthYearText =  findViewById(R.id.monthYearText);
        cvv =  findViewById(R.id.cvv);
        btn_save = findViewById(R.id.btn_save);

        myDB = new DBHelper(this);

        //save button
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNo = cardNoText.getText().toString();
                String cardHolderName = cardHolderNameText.getText().toString();
                String expireDate = monthYearText.getText().toString();
                String cvvCode = cvv.getText().toString();

                if (cardNo.equals("") ||cardHolderName.equals("") || expireDate.equals("")||cvvCode.equals("")){
                    Toast.makeText(addMyCard.this, "Fill all Details", Toast.LENGTH_SHORT).show();
                }else{
                    myDB.addCardDetails(cardNo, cardHolderName, expireDate, cvvCode);//get filled values from editText and store those in the database
                    Toast.makeText(addMyCard.this, "Your Card Details Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //view all inserted data
    public void viewAll(View view){
        DBHelper dbHelper = new DBHelper(this);

        List info = dbHelper.readAll();

        String[] infoArray = (String[]) info.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My Card Details");

        builder.setItems(infoArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String cardNo = infoArray[i].split("\n")[0];
                String cardHolderName = infoArray[i].split("\n")[1];
                String exp = infoArray[i].split("\n")[2];
                String CVV = infoArray[i].split("\n")[3];

                cardNoText.setText(cardNo);
                cardHolderNameText.setText(cardHolderName);
                monthYearText.setText(exp);
                cvv.setText(CVV);
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


    //delete method
    public void deleteCard(View view){
        DBHelper dbHelper = new DBHelper(this);

        String cardNo = cardNoText.getText().toString();

        if(cardNo.isEmpty()){
            Toast.makeText(this,"Select a Card", Toast.LENGTH_SHORT).show();

        }else{
            dbHelper.deleteInfo(cardNo);
            Toast.makeText(this, cardNo+ "Card Details Deleted", Toast.LENGTH_SHORT).show();
        }
    }


    //update method
    public void updateCard(View view){
        DBHelper dbHelper = new DBHelper(this);

        String cardNo = cardNoText.getText().toString();
        String cardHolderName = cardHolderNameText.getText().toString();
        String expireDate = monthYearText.getText().toString();
        String cvvCode = cvv.getText().toString();

        if(cardNo.isEmpty() || cardHolderName.isEmpty() || expireDate.isEmpty() || cvvCode.isEmpty()){
            Toast.makeText(this,"select or type your card details", Toast.LENGTH_SHORT).show();

        }else{
            dbHelper.updateInfo(view, cardNo, cardHolderName, expireDate, cvvCode);
            cardNoText.setText("");
            cardHolderNameText.setText("");
            monthYearText.setText("");
            cvv.setText("");
            Toast.makeText(this,"Your card details updated",Toast.LENGTH_SHORT).show();
        }
    }
}