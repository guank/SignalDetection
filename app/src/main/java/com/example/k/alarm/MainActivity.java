package com.example.k.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                switch(view.getId()){
                    case R.id.button:
                        perform = true;

                        checkConnect();

                        break;

                    case R.id.ext:
                        perform = false;
                        finish();

                        break;

                }


                //checkConnect();

                //checkConnect();
                /*ConnectivityManager cmanager = (ConnectivityManager)getSystemService(getActivity().this.CONNECTIVITY_SERVICE);
                NetworkInfo info = cmanager.getActiveNetworkInfo();
                if(info!=null && info.isConnected()){
                    if(info.getType() == ConnectivityManager.TYPE_WIFI) {
                        Toast.makeText(MainActivity.this, "Wifi", Toast.LENGTH_LONG).show();
                    }
                    else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                        Toast.makeText(MainActivity.this, "Mobile", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_LONG).show();
                }*/

                Calendar calendar = Calendar.getInstance();
                Calendar c = Calendar.getInstance();

                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);

                //seconds = seconds + 1;

                calendar.set(Calendar.HOUR_OF_DAY, hours);
                calendar.set(Calendar.MINUTE, minutes);
                calendar.set(Calendar.SECOND, seconds);

                Intent intent = new Intent(getApplicationContext(),Notification_receiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            }
        });
    }

    public void clickEx(View v){
        perform = false;
        finishAffinity();
        System.exit(0);
    }


    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Exit Application?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int id){
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean perform;

    public static boolean getPerform()
    {
        return perform;
    }

    public void checkConnect() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                //int x = 0;
                while(MainActivity.getPerform()){
                    ConnectivityManager cmanager = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo info = cmanager.getActiveNetworkInfo();
                    findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            switch (view.getId()) {
                                case R.id.ext:
                                    Toast.makeText(MainActivity.this, "Notification Cancelled",
                                            Toast.LENGTH_LONG).show();
                                    perform = false;
                                    finish();

                                    break;
                            }
                        }
                    });
                    if(info!=null && info.isConnected()){
                        //if(info.getType() == ConnectivityManager.TYPE_WIFI) {
                            //Toast.makeText(MainActivity.this, "Wifi", Toast.LENGTH_LONG).show();
                        //}
                        if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                            Toast.makeText(MainActivity.this, "Mobile Detected", Toast.LENGTH_LONG).show();
                            perform = false;
                        }
                    }
                    //else {
                       // Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_LONG).show();
                    //}

                }
            }
        }, 0);
    }
}
