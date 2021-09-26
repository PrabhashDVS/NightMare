package com.example.nightmare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class logIn extends AppCompatActivity {
    //Register all btn and eText
    EditText username, password;
    Button btn_signIn, btn_signUp;
    DBHelper  myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btn_signIn = (Button) findViewById(R.id.btn_signIn);
        btn_signUp = (Button) findViewById(R.id.btn_signUp);

        myDB = new DBHelper(this);

        //Sign in
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pwd = password.getText().toString();

                if (user.equals("") || pwd.equals("")){
                    Toast.makeText(logIn.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean result = myDB.checkusernamePassword(user,pwd);
                    if(result == true){
                        Toast.makeText(logIn.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),addMyCard.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(logIn.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
            }
        });
    }
}