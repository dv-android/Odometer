package com.hfad.odometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class OdometerService extends Service {

    private final IBinder binder = new OdometerBinder();
    public static double distanceInMeters;
    private static Location lastLocation = null;

    public class OdometerBinder extends Binder{
        OdometerService getOdometer(){
            Log.v("Service"," getOdometer called");
            return OdometerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
           Log.v("Service"," OnBind called");
           return binder;
    }

    public void onCreate(){

        Log.v("Service"," Service created = 1");
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               if(lastLocation == null){
                   lastLocation = location;
               }
               distanceInMeters += location.distanceTo(location);
                lastLocation = location;
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        try{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,locationListener);
        }catch(SecurityException e){

        }

    }

    public double getMiles(){
        return this.distanceInMeters/1609.344;
    }
}
