package com.example.samplesqliteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnGoToSignUp, btnLogin;
    private EditText etUserName, etPassword;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnGoToSignUp = findViewById(R.id.btnGoToSignUpActivity);
        btnLogin = findViewById(R.id.btnlogin);
        etUserName = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);

       dbHelper = new  DBHelper(this);

        btnGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()){ // checks if the field is empty or not
                    Toast.makeText(MainActivity.this, "Please input all the fields!", Toast.LENGTH_SHORT).show();
                }else {

                    boolean login = dbHelper.login(username,password); // calls the login method from th DBHelper

                    if (login){ // if true  go to the Profile Activity
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra("username", username); // send data to ProfileActivity;
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}