package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radio = findViewById(R.id.radio);
        findViewById(R.id.submit).setOnClickListener(v -> {
            int id = radio.getCheckedRadioButtonId();
            if(id == R.id.mail) {
                Intent intent = new Intent(MainActivity.this, MailActivity.class);
                startActivity(intent);
            }
            else if(id == R.id.music) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
    }
}

