package com.example.moviecataloguefordicoding;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.example.moviecataloguefordicoding.notification.NotificationReceiver;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SwitchCompat dailyRemainder = findViewById(R.id.daily_remainder);
        SwitchCompat todayRemainder = findViewById(R.id.today_remainder);

        final NotificationReceiver notificationReceiver = new NotificationReceiver();

        final SharedPreferences sharedPref = getSharedPreferences("remainder",MODE_PRIVATE);
        dailyRemainder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor sharedpreferance = sharedPref.edit();
                sharedpreferance.putBoolean("daily_remainder",b);
                sharedpreferance.apply();

                if (b){
                    notificationReceiver.setDailyNotification(getApplicationContext(), NotificationReceiver.TYPE_REPEATING);
//                    notificationReceiver.setDailyNotification(getApplicationContext(),NotificationReceiver.TYPE_TODAY);
                }
                else {
                    notificationReceiver.cancelAlarm(getApplicationContext(), NotificationReceiver.TYPE_REPEATING);
                }
            }
        });
        todayRemainder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    notificationReceiver.setDailyNotification(getApplicationContext(), NotificationReceiver.TYPE_TODAY);
                }
                else {
                    notificationReceiver.cancelAlarm(getApplicationContext(), NotificationReceiver.TYPE_TODAY);
                }
            }
        });

        boolean prefDaily =sharedPref.getBoolean("daily_remainder",true);
        boolean prefToday =sharedPref.getBoolean("today_remainder",true);
        dailyRemainder.setChecked(prefDaily);
        dailyRemainder.setChecked(prefToday);


        Toolbar toolbar = findViewById(R.id.my_toolbar_setting);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Setting");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}
