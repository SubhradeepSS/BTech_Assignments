package com.example.modellab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class Receive extends AppCompatActivity {

    EditText bg, address;
    Button submit;
    NotificationManagerCompat notificationManager;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        db = new DatabaseHelper(this);

        submit = findViewById(R.id. submit);
        submit.setOnClickListener(v -> {
            bg = findViewById(R.id.bgroup);
            address = findViewById(R.id.address);

            db.insertDataRequest(bg.getText().toString(), address.getText().toString());

            notificationManager = NotificationManagerCompat.from(this);
            String title = "Blood Group: " + bg.getText().toString();

            String message = "Address: " + address.getText().toString();
            Intent i = new Intent(this, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this,1,i,PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(1, notification);
        });
    }
}