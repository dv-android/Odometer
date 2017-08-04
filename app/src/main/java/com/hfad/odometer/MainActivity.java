package com.hfad.odometer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends Activity {

    private OdometerService odometer;
    private boolean bound = false;


    private ServiceConnection connection = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.v("Main Activity"," OnServiceConnected started");
            OdometerService.OdometerBinder odometerBinder = (OdometerService.OdometerBinder) binder;
            odometer = odometerBinder.getOdometer();
            bound = true;

            double distance = 0.0;
            distance =  odometer.getMiles();

            Log.v("Main Activity"," OnServiceConnected stopped");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("MainActivity"," Main Activity created");

    }

    protected void onStart(){
        super.onStart();
        Log.v("MainActivity","onStart stared");
        Intent intent = new Intent(this,OdometerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
        Log.v("MainActivity","onStart finished");
    }
}
