package com.example.samplesqliteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName;
    private Button btnLogout;
    String name;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String username = getIntent().getStringExtra("username");

        tvProfileName = findViewById(R.id.tvProfileName);
        btnLogout = findViewById(R.id.btnLogout);

        dbHelper = new DBHelper(this); // creates new object to access the method inside DBHelper


        Cursor cursor = dbHelper.getProfile(username); // calls the getProfile method based on the logged username
        if (cursor.moveToFirst()) { // if not empty get the data
            do { // We need loop here, because cursor returns a SET or an array
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                username = cursor.getString(cursor.getColumnIndexOrThrow("usernamw"));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Set name of the textview to the retrieved name
        tvProfileName.setText("Welcome: " + name);

        // Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish(); // used to kill the current activity or application
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
}