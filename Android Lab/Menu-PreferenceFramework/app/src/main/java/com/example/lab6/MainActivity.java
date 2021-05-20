package com.example.lab6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name, email, phone;
    Button submit, show;
    SharedPreferences sp;
    TextView show_name, show_email, show_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE );

        submit = findViewById(R.id.submit);
        show = findViewById(R.id.show);
        show_name = findViewById(R.id.show_name);
        show_email = findViewById(R.id.show_email);
        show_phone = findViewById(R.id.show_phone);

        submit.setOnClickListener(v -> {
            name = findViewById(R.id.name);
            email = findViewById(R.id.email);
            phone = findViewById(R.id.phone);

            String nameStr = name.getText().toString();
            String emailStr = email.getText().toString();
            String phoneStr = phone.getText().toString();

            SharedPreferences.Editor editor=sp.edit();
            editor.putString("name", nameStr);
            editor.putString("email", emailStr);
            editor.putString("phone", phoneStr);
            editor.apply();

            name.setText("");
            email.setText("");
            phone.setText("");

            Toast.makeText(MainActivity.this,"Information Saved",Toast.LENGTH_LONG).show();
        });

        show.setOnClickListener(v -> {
            String nameS = sp.getString("name", "Enter data above");
            String emailS = sp.getString("email", "Enter data above");
            String phoneS = sp.getString("phone", "Enter data above");
            show_name.setText("Name: " + nameS);
            show_email.setText("Email: " + emailS);
            show_phone.setText("Phone: "+ phoneS);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.help:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://support.google.com/android/?hl=en#topic=7313011"));
                startActivity(browserIntent);
                break;
            case R.id.settings:
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                break;
            case R.id.control:
                Intent i = new Intent(this, Preference.class);
                startActivity(i);
                break;
            case R.id.exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}