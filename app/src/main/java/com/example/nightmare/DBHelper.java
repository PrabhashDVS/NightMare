package com.example.nightmare;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


    final class Login{
        private Login(){}

        public static class Payment implements BaseColumns{
            public static final String TABLE_NAME = "payment";
            public static final String COLUMN_NAME_CARDNO = "cardNo";
            public static final String COLUMN_NAME_CARDHOLDER = "cardHolderName";
            public static final String COLUMN_NAME_CVV = "cvv";
            public static final String COLUMN_NAME_EXPIREDATE = "exp";
        }
    }


    public class DBHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "Login.db";
        public DBHelper( Context context) {
            super(context,"DATABASE_NAME",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase myDB) {

            //create users table
            myDB.execSQL("create Table users(username Text primary key, password Text, phoneNo Text, email Text)");

            //create Payment table
            String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + Login.Payment.TABLE_NAME + " (" +
                            Login.Payment._ID + " INTEGER PRIMARY KEY," +
                            Login.Payment.COLUMN_NAME_CARDNO + " TEXT," +
                            Login.Payment.COLUMN_NAME_CARDHOLDER + " TEXT," +
                            Login.Payment.COLUMN_NAME_EXPIREDATE + " TEXT," +
                            Login.Payment.COLUMN_NAME_CVV + " TEXT)";
            myDB.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
            myDB.execSQL("drop Table if exists users");
        }


        //insert data to the database register the user
        public  Boolean insertData(String username, String password, String phoneNo, String email){
            SQLiteDatabase myDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", username);
            contentValues.put("password", password);
            contentValues.put("phoneNo",phoneNo);
            contentValues.put("email", email);
            long result = myDB.insert("users", null,contentValues);

            if (result == -1){
                return false;
            }else {
                return true;
            }
        }


        //check the user already exist in the database
        public Boolean checkusername(String username){
            SQLiteDatabase myDB = this.getWritableDatabase();
            Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[] {username} );

            if (cursor.getCount()>0){
                return true;
            }else{
                return false;
            }
        }


        public Boolean checkusernamePassword(String username, String password){
            SQLiteDatabase myDB = this.getWritableDatabase();
            Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[] {username, password} );
            if(cursor.getCount()>0){
                return true;
            }else{
                return false;
            }
        }


        //add card details to the system
        public Long addCardDetails(String cardNo, String cardHolder, String expireDate, String cvv){
            SQLiteDatabase myDB = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(Login.Payment.COLUMN_NAME_CARDNO, cardNo);
            contentValues.put(Login.Payment.COLUMN_NAME_CARDHOLDER, cardHolder);
            contentValues.put(Login.Payment.COLUMN_NAME_EXPIREDATE, expireDate);
            contentValues.put(Login.Payment.COLUMN_NAME_CVV, cvv);

            return myDB.insert(Login.Payment.TABLE_NAME, null, contentValues);
        }


        //read payment details
        public List readAll(){
            SQLiteDatabase myDB = this.getReadableDatabase();

            String [] projection = {
                    Login.Payment._ID,
                    Login.Payment.COLUMN_NAME_CARDNO,
                    Login.Payment.COLUMN_NAME_CARDHOLDER,
                    Login.Payment.COLUMN_NAME_EXPIREDATE,
                    Login.Payment.COLUMN_NAME_CVV
            };

            String sortOrder = Login.Payment._ID + " DESC";

            Cursor cursor = myDB.query(
                    Login.Payment.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    sortOrder
            );

            List info = new ArrayList<>();

            while(cursor.moveToNext()){
                String cardNo = cursor.getString(cursor.getColumnIndexOrThrow(Login.Payment.COLUMN_NAME_CARDNO));
                String cardHolderName = cursor.getString(cursor.getColumnIndexOrThrow(Login.Payment.COLUMN_NAME_CARDHOLDER));
                String exp = cursor.getString(cursor.getColumnIndexOrThrow(Login.Payment.COLUMN_NAME_EXPIREDATE));
                String cvv = cursor.getString(cursor.getColumnIndexOrThrow(Login.Payment.COLUMN_NAME_CVV));

                info.add(cardNo+ "\n"+ cardHolderName +"\n"+  exp +"\n"+ cvv + "\n" );
            }
            cursor.close();

            return info;
        }


        //delete function
        public void deleteInfo(String cardNo){
            SQLiteDatabase db = getReadableDatabase();

            String selection = Login.Payment.COLUMN_NAME_CARDNO + " LIKE ?";
            String[] StringArgs = {cardNo};
            db.delete(Login.Payment.TABLE_NAME,selection,StringArgs);
        }


        //update function
        public void updateInfo(View view, String cardNo, String cardHolder, String expireDate, String cvv){
            SQLiteDatabase myDB = getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Login.Payment.COLUMN_NAME_CARDNO, cardNo);
            values.put(Login.Payment.COLUMN_NAME_CARDHOLDER, cardHolder);
            values.put(Login.Payment.COLUMN_NAME_EXPIREDATE, expireDate);
            values.put(Login.Payment.COLUMN_NAME_CVV, cvv);

            String selection = Login.Payment.COLUMN_NAME_CARDHOLDER + " LIKE ?";
            String[] selectionArgs = {cardHolder};

            int count = myDB.update(
                    Login.Payment.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
            );

            Snackbar snackbar = Snackbar.make(view, count+ " rows were affected!", Snackbar.LENGTH_LONG);
            snackbar.setAnimationMode(snackbar.ANIMATION_MODE_SLIDE);
            snackbar.show();
        }
    }


