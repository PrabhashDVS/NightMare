package com.example.mad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DBHelperIT20149994 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PhoneInfo.db";
    public DBHelperIT20149994( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PhonesMaster.Phones.TABLE_NAME + " (" +
                        PhonesMaster.Phones._ID + " INTEGER PRIMARY KEY," +
                        PhonesMaster.Phones.COLUMN_NAME_DEVICENAME + " TEXT," +
                        PhonesMaster.Phones.COLUMN_NAME_MANUFACTURER + " TEXT," +
                        PhonesMaster.Phones.COLUMN_NAME_YEAR + " TEXT," +
                        PhonesMaster.Phones.COLUMN_NAME_PRICE + " TEXT," +
                        PhonesMaster.Phones.COLUMN_NAME_SPECIALNOTES + " TEXT," +
                        PhonesMaster.Phones.COLUMN_NAME_USERNAME + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public Long addInfo(String deviceName, String manufacturer, String year, String price, String specialNotes, String username){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PhonesMaster.Phones.COLUMN_NAME_DEVICENAME, deviceName);
        values.put(PhonesMaster.Phones.COLUMN_NAME_MANUFACTURER, manufacturer);
        values.put(PhonesMaster.Phones.COLUMN_NAME_YEAR, year);
        values.put(PhonesMaster.Phones.COLUMN_NAME_PRICE, price);
        values.put(PhonesMaster.Phones.COLUMN_NAME_SPECIALNOTES, specialNotes);
        values.put(PhonesMaster.Phones.COLUMN_NAME_USERNAME, username);

        return db.insert(PhonesMaster.Phones.TABLE_NAME, null, values);
    }

    public List readAll(String username){

        SQLiteDatabase db = getReadableDatabase();

        String [] projection = {
                PhonesMaster.Phones._ID,
                PhonesMaster.Phones.COLUMN_NAME_DEVICENAME,
                PhonesMaster.Phones.COLUMN_NAME_MANUFACTURER,
                PhonesMaster.Phones.COLUMN_NAME_YEAR,
                PhonesMaster.Phones.COLUMN_NAME_PRICE,
                PhonesMaster.Phones.COLUMN_NAME_SPECIALNOTES
        };

        String selection = PhonesMaster.Phones.COLUMN_NAME_USERNAME + " = ?";
        String[] stringArgs = {username};

        String sortOrder = PhonesMaster.Phones._ID + " DESC";

        Cursor cursor = db.query(
                PhonesMaster.Phones.TABLE_NAME,
                projection,
                selection,
                stringArgs,
                null,
                null,
                sortOrder

        );

        List info = new ArrayList<>();

        while(cursor.moveToNext()){
            String deviceID = cursor.getString(cursor.getColumnIndexOrThrow(PhonesMaster.Phones._ID));
            String deviceName = cursor.getString(cursor.getColumnIndexOrThrow(PhonesMaster.Phones.COLUMN_NAME_DEVICENAME));
            String manufacturer = cursor.getString(cursor.getColumnIndexOrThrow(PhonesMaster.Phones.COLUMN_NAME_MANUFACTURER));
            String year = cursor.getString(cursor.getColumnIndexOrThrow(PhonesMaster.Phones.COLUMN_NAME_YEAR));
            String price = cursor.getString(cursor.getColumnIndexOrThrow(PhonesMaster.Phones.COLUMN_NAME_PRICE));
            String specialNotes = cursor.getString(cursor.getColumnIndexOrThrow(PhonesMaster.Phones.COLUMN_NAME_SPECIALNOTES));

            info.add(deviceName+":"+manufacturer+":"+year+":"+price+":"+specialNotes+":"+deviceID);
        }
        cursor.close();

        return info;
    }

    public void deleteInfo(String deviceID){
        SQLiteDatabase db = getReadableDatabase();

        String selection = PhonesMaster.Phones._ID + " = ?";
        String[] stringArgs = {deviceID};

        db.delete(PhonesMaster.Phones.TABLE_NAME,selection,stringArgs);
    }

    public void updateInfo(View view, String deviceName, String manufacturer, String year, String price, String specialNotes,String deviceID){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PhonesMaster.Phones.COLUMN_NAME_DEVICENAME, deviceName);
        values.put(PhonesMaster.Phones.COLUMN_NAME_MANUFACTURER, manufacturer);
        values.put(PhonesMaster.Phones.COLUMN_NAME_YEAR, year);
        values.put(PhonesMaster.Phones.COLUMN_NAME_PRICE, price);
        values.put(PhonesMaster.Phones.COLUMN_NAME_SPECIALNOTES, specialNotes);

        String selection = PhonesMaster.Phones._ID + " = ?";
        String[] selectionArgs = {deviceID};

        int count = db.update(
                PhonesMaster.Phones.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        Snackbar snackbar = Snackbar.make(view, "Updated Successfully",Snackbar.LENGTH_LONG);
        snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
