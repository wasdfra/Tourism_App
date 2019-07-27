package com.tourism.app.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.tourism.app.R;
import com.tourism.app.sensors.GPS;

import org.json.JSONException;
import org.json.JSONObject;

public class Track_Service extends Service {
    private static Integer ServiceID = null;

    private static final int NotifyID = 1;
    private static final int NotifyGPSID = 2;

    private static Notification notification;
    static NotificationManager notificationManager;

    public GPS gps = null;

    public static Handler timer = new Handler();

    public int gpsFreq = 1000; //In ms

    public Track_Service(){}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ServiceID != null)
            throw new UnsupportedOperationException("Already started");
        ServiceID = startId;
        gps = new GPS(0, (LocationManager) getSystemService(LOCATION_SERVICE));

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //TODO: Get data from here -> Bundle bundleExtra = intent.getBundleExtra("SID");

        if (this.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED){
            activateSensors();
        } else {
            Toast.makeText(getApplicationContext(), R.string.gps_error, Toast.LENGTH_LONG).show();
            this.stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        ServiceID = null;
        super.onDestroy();
    }

    public void activateSensors(){
        try {

        Intent notifyIntent = new Intent(this, getApplication().getClass());

        PendingIntent intent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        gps.startGPS(0,0, this.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION"), gpsFreq);
        timer.postDelayed(checkData, gpsFreq);

        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private Runnable checkData = new Runnable() {

        @Override
        public void run() {

            try {
                Log.e("GPS Data:", gps.getLastData().toString(2));
            }catch (JSONException e){
                e.printStackTrace();
            }

            timer.postDelayed(checkData, gpsFreq);
        }

    };

}
