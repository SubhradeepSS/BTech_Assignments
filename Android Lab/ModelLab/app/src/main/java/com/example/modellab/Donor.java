package com.example.modellab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Donor extends AppCompatActivity {

    Button all;
    DatabaseHelper myDb;
    TextView match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
        myDb=new DatabaseHelper(this);
        match = findViewById(R.id.match);

        SharedPreferences sp = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        String bg = sp.getString("bg","O+");
        Toast.makeText(this, bg, Toast.LENGTH_SHORT).show();

        match.setText(myDb.getRequest(bg));

        all = findViewById(R.id.all);

        all.setOnClickListener(v -> {
            Cursor res= myDb.getAllDataRequest();
            if (res.getCount()==0)
            {
                Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
            }
            else {
                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("Blood Group:").append(res.getString(0)).append("\n");
                    buffer.append("Address:").append(res.getString(1)).append("\n\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("DATA");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}