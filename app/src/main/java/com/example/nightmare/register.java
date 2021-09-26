package com.example.nightmare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText username, password, confirmPwd, email, phoneNo;
    Button btn_signIn, btn_signUp;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.nameText);
        password = (EditText)findViewById(R.id.passwordText);
        confirmPwd = (EditText)findViewById(R.id.confirmPwdText);
        phoneNo = (EditText)findViewById(R.id.phoneText);
        email = (EditText)findViewById(R.id.emailText);

        btn_signUp = (Button) findViewById(R.id.btn_signUp);
        btn_signIn = (Button) findViewById(R.id.btn_signIn);

        myDB = new DBHelper(this);

        btn_signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String  user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = confirmPwd.getText().toString();
                String mail = email.getText().toString();
                String phone = phoneNo.getText().toString();

                //check all details fill or not by user register
                if (user.equals("") || pass.equals("") ||repass.equals("") || phone.equals("") || mail.equals("")){
                    Toast.makeText(register.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                }else{
                    //check if password and confirm password are matched
                    if(pass.equals(repass)){
                        Boolean usercheckResult = myDB.checkusername(user);
                        if(usercheckResult == false){
                            Boolean regResult = myDB.insertData(user, pass, phone, mail);//insert user data in the database

                            if (regResult == true){
                                Toast.makeText(register.this, "User Registration Successfully", Toast.LENGTH_SHORT).show();

                                //return user to login page
                                Intent intent = new Intent(getApplicationContext(),logIn.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(register.this, "Register Fail", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(register.this, "User already exist\n Please Sign in", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(register.this,"Password not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Sign In button event
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),logIn.class);
                startActivity(intent);
            }
        });
    }
}