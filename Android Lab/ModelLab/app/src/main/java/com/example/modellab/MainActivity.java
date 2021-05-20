package com.example.modellab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login,register;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        databaseHelper = new DatabaseHelper(this);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        register.setOnClickListener(v -> {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        });

        login.setOnClickListener(v -> {
            EditText username, password;
            username = findViewById(R.id.username_login);
            password = findViewById(R.id.password_login);

            boolean bg = databaseHelper.checkUser(username.getText().toString(), password.getText().toString());

            if(!bg){
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                ed.putString("bg", databaseHelper.getBg(username.getText().toString()));
                ed.putString("username", username.getText().toString());
                ed.apply();
                Intent i = new Intent(this, Home.class);
                startActivity(i);
            }
        });
    }
}