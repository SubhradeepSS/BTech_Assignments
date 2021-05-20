package com.example.modellab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText name, age, gender, dob, bg, mobile, email, username, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHelper = new DatabaseHelper(this);
        register = findViewById(R.id.button);
        SharedPreferences sp = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        register.setOnClickListener(v -> {
            username=findViewById(R.id.username);
            password=findViewById(R.id.password);
            name=findViewById(R.id.name);
            age=findViewById(R.id.age);
            gender=findViewById(R.id.gender);
            dob=findViewById(R.id.dob);
            bg=findViewById(R.id.bg);
            mobile=findViewById(R.id.mobile);
            email=findViewById(R.id.email);

            if(databaseHelper.checkUsername(username.getText().toString())){
                Toast.makeText(this, "User with username exists", Toast.LENGTH_SHORT).show();
            }
            else{
                boolean r = databaseHelper.insertData(
                        username.getText().toString(),
                        password.getText().toString(),
                        name.getText().toString(),
                        age.getText().toString(),
                        gender.getText().toString(),
                        dob.getText().toString(),
                        bg.getText().toString(),
                        mobile.getText().toString(),
                        email.getText().toString()
                );
                if(r){
                    Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}