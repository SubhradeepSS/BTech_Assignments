package com.example.lab6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        findViewById(R.id.open_video).setOnClickListener(v -> {
            String filePath = "/storage/emulated/0/WhatsApp/Media/WhatsApp Audio/AUD-20201128-WA0010.mp3";
            File file = new File(filePath);

            try {
                if (file.exists()) {
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(filePath), "audio/*");
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "The desired music isn't available!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                    startActivity(intent);
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
