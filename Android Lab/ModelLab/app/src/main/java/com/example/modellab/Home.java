package com.example.modellab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    Button donate, receive;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sp = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String username = sp.getString("username","");

        title = findViewById(R.id.welcome);
        title.setText("Welcome:" + username);

        donate = findViewById(R.id.donate);
        receive = findViewById(R.id.receive);

        donate.setOnClickListener(v -> {
            Intent i = new Intent(this, Donor.class);
            startActivity(i);
        });

        receive.setOnClickListener(v -> {
            Intent i = new Intent(this, Receive.class);
            startActivity(i);
        });
    }
}