package com.example.mad.database;

import android.provider.BaseColumns;

public class PhonesMaster {
    private PhonesMaster(){}

    public static class Phones implements BaseColumns{
        public static final String TABLE_NAME = "phones";
        public static final String COLUMN_NAME_DEVICENAME = "devicename";
        public static final String COLUMN_NAME_MANUFACTURER = "manufacturer";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SPECIALNOTES = "specialnotes";
        public static final String COLUMN_NAME_USERNAME = "username";

    }
}
