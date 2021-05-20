package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6.R;

public class NoteActivity extends AppCompatActivity {

//    EditText hr,min,message;
    TimePicker timePicker;
    EditText msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        findViewById(R.id.submit).setOnClickListener(v -> {
            timePicker = findViewById(R.id.timePicker);

            msg = findViewById(R.id.message);
            int hour = timePicker.getHour();
            int min = timePicker.getMinute();

            String message = msg.getText().toString();
//            Log.d("check", String.valueOf(hour));
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
            intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
            intent.putExtra(AlarmClock.EXTRA_MINUTES, min);
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI,false);
            startActivity(intent);
        });
    }
}
