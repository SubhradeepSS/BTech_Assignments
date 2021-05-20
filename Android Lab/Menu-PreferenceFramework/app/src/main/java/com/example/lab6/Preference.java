package com.example.lab6;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

public class Preference extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        Load_setting();
    }

    private void Load_setting()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        android.preference.Preference b = findPreference("brightness");
        b.setOnPreferenceChangeListener((prefs, obj) -> {
            int sb = (int) obj;
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = sb / 100.0f;
            Log.d("bright_val", String.valueOf(sb));
            getWindow().setAttributes(lp);
            return true;
        });

        android.preference.Preference v = findPreference("volume");
        v.setOnPreferenceChangeListener((prefs, obj) -> {
            int vol = (int) obj;
            Log.d("vol", String.valueOf(vol));
            AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol, AudioManager.FLAG_SHOW_UI);
            return true;
        });

        boolean chk_night = sp.getBoolean("NIGHT", false);
        if (chk_night)
        {
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        }
        else
        {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }

        CheckBoxPreference chk_night_instant = (CheckBoxPreference) findPreference("bluetooth");

        chk_night_instant.setOnPreferenceChangeListener((prefs, obj) -> {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            boolean yes = (boolean) obj;

            if (yes) {
                bluetoothAdapter.enable();
            } else {
                bluetoothAdapter.disable();
            }

            return true;
        });

        ListPreference LP = (ListPreference) findPreference("ORIENTATION");

        String orien = sp.getString("ORIENTATION", "false");

        if ("1".equals(orien))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

            LP.setSummary(LP.getEntry());
        }

        else if ("2".equals(orien))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            LP.setSummary(LP.getEntry());
        }

        else if ("3".equals(orien))
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            LP.setSummary(LP.getEntry());
        }

        LP.setOnPreferenceChangeListener((prefs, obj) -> {

            String items = (String) obj;
            if (prefs.getKey().equals("ORIENTATION"))
            {
                switch (items)
                {
                    case "1":
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                        break;
                    case "2":
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                    case "3":
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;
                }

                ListPreference LPP = (ListPreference) prefs;
                LPP.setSummary(LPP.getEntries()[LPP.findIndexOfValue(items)]);

            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }
}
