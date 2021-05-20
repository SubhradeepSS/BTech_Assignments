package com.example.nitt_activities;


import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;


public class activity2 extends AppCompatActivity {
    private RadioButton sub1,sub2,sub3;
    public Button submit,back;
    private RadioGroup rg;
    public EditText message;
    public String msgText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        rg = findViewById(R.id.radioGroup);
        sub1 = findViewById(R.id.sub1);
        sub2 = findViewById(R.id.sub2);
        sub3 = findViewById(R.id.sub3);
        submit = findViewById(R.id.submit);
        back = findViewById(R.id.back);

        submit.setOnClickListener(v -> {
            message = findViewById(R.id.message);
            msgText = message.getText().toString();
//            Log.d("msgV", msgText);

            if(rg.getCheckedRadioButtonId()==-1){
                Toast.makeText(getApplicationContext(),"Choose anyone option",Toast.LENGTH_LONG).show();
                return;
            }
            if(sub1.isChecked()){
                String number = "";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, msgText));
                i.putExtra("sms_body", msgText);
                startActivity(i);
            }
            else if(sub2.isChecked()){
                Intent sendMail = new Intent(Intent.ACTION_SEND);
                sendMail.putExtra(Intent.EXTRA_TEXT, msgText);
                sendMail.setType("message/rfc822");
                startActivity(sendMail);
            }
            else{
                Intent i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY, msgText);
                startActivity(i);
            }
        });

        back.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),"Welcome Back to activity1",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }
}