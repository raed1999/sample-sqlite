package com.example.samplesqliteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText etSignUpName, etSignUpUsername, etSignUpPassword, etSignUpConfirmPassword;
    private Button btnCreateAccount;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etSignUpName = findViewById(R.id.etSignUpName);
        etSignUpUsername = findViewById(R.id.etSignUpUserName);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etSignUpConfirmPassword = findViewById(R.id.etSignUpConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        dbHelper = new DBHelper(this); // creates new object from the class DBHelper

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etSignUpName.getText().toString();
                String username = etSignUpUsername.getText().toString();
                String password = etSignUpPassword.getText().toString();
                String confirmPassword = etSignUpConfirmPassword.getText().toString();


                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) { // checks if all data is empty
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show(); // if empty display a toast
                } else if (!password.equals(confirmPassword)) { // checks if password matches, display a toast if not
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    boolean exists = dbHelper.checkUserNameExists(username); // calls the method that checks if the username is not used
                    if (exists) { // if it exist, display a toast
                        Toast.makeText(SignUpActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean signUp = dbHelper.signUp(name, username, password);  //  calls the method that handles the insertion of the data,
                        if (signUp) {  //if true, no error occurred otherwise error occurred
                            Toast.makeText(SignUpActivity.this, "Profile created successfully", Toast.LENGTH_SHORT).show(); // toast successful message
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class); //
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Unknown error occurred! Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}