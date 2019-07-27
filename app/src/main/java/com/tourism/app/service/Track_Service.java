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
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.tourism.app.R;
import com.tourism.app.sensors.GPS;

public class Track_Service extends Service {
    private static Integer ServiceID = null;

    private static final int NotifyID = 1;
    private static final int NotifyGPSID = 2;

    private static Notification notification;
    static NotificationManager notificationManager;

    public GPS gps = null;

    public int gpsFreq = 1; //In Hz

    public Track_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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

        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

}
