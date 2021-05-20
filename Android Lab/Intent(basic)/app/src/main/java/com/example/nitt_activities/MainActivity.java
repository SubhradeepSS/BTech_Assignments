package com.example.nitt_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText rollnumber,name;
    private String user_name,roll_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.submit1);
        rollnumber = findViewById(R.id.rollnumber);
        name = findViewById(R.id.name);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                user_name = name.getText().toString();
                roll_number = rollnumber.getText().toString();
                if(roll_number.matches("")){
                    Toast.makeText(getApplicationContext(),"Enter valid Roll Number",Toast.LENGTH_LONG).show();
                    return;
                }
                if(user_name.matches("")){
                    Toast.makeText(getApplicationContext(),"Enter valid name",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"Welcome " +user_name+",Launching activity2",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),activity2.class);
                startActivity(i);
            }

        });
    }
    public void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(name.getWindowToken(),0);
    }
}