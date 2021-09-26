package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainIt20149994Activity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "com.example.mad.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_it20149994);
    }

    public void logInUser(View view) {
        Intent intent = new Intent(this, UserInterfaceIt20149994Activity.class);
        EditText editText = (EditText) findViewById(R.id.etUserName);
        String username = editText.getText().toString();
        intent.putExtra(EXTRA_USERNAME, username);
        startActivity(intent);
    }
}