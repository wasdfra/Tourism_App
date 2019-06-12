package com.tourism.app.sensors;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.tourism.app.data.handler.GPSDataHandler;
import com.tourism.app.data.model.GPSData;
import com.tourism.app.service.Track_Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by estgl3264 on 30/08/2018.
 */

public class GPS {

    private LocationManager locationManager;
    private LocationListener GPSListener;

    //BD Var
    private static com.tourism.app.data.model.GPSData GPSData = new GPSData();
    private static com.tourism.app.data.handler.GPSDataHandler GPSDataHandler = new GPSDataHandler();

    //Temp Var Array
    private List<Integer> unsavedGPS = new ArrayList<>();

    private double Lat;
    private double Long;
    private double Alt;

    public boolean hasData = false;

    private int UID = -1;


    private Handler timer = new Handler();

    public GPS(int uID,  LocationManager lm) {
        UID = uID;
        hasData = false;
        locationManager = lm;
    }

    private Track_Service service = new Track_Service();

    private int addData (double Lat, double Long, double Alt, long currentDateTime){

//        if(hasData){
            this.Lat = Lat;
            this.Long = Long;
            this.Alt = Alt;

            GPSData.setData(this.Lat, this.Long, this.Alt, currentDateTime);

            if(UID < 0 )
                return -1;

            GPSData.setUserID(UID);


            return GPSDataHandler.insert(GPSData);
//        }else
//            return -1;

    }

    public JSONObject getLastData() throws JSONException {
        JSONObject tmp = new JSONObject();

        tmp.put("Lat", Lat);
        tmp.put("Long", Long );
        tmp.put("Alt", Alt);

        return tmp;
    }

    void createGPSListner(){
        GPSListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Long = location.getLongitude();
                Lat= location.getLatitude();
                Alt = location.getAltitude();
                hasData = true;
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                service.startActivity(i);

            }
        };
    }

    public void Stop(){
        timer.removeCallbacks(gravFich);
        locationManager.removeUpdates(GPSListener);
        timer.removeCallbacksAndMessages(null);
    }


    public void startGPS(int minTime, int minDistance, int res, float freq)throws SecurityException {
        this.createGPSListner();

        if(res == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates("gps", minTime, minDistance, GPSListener);
            timer.postDelayed(gravFich, (int)((1000/freq)+0.5));  //Convert Hz to ms and round the result
        }

        hasData = false;
    }

    Runnable gravFich =  new Runnable() {
        @Override
        public void run() {
            timer.postDelayed(gravFich, 1000);
            long currentDateTime = System.currentTimeMillis();

            if(hasData)
                addData(Lat,Long,Alt,currentDateTime);
        }
    };

    public JSONArray getUnsavedData(double max) throws JSONException {
        JSONArray gpsJData = new JSONArray();

        GPSData tmpGPS;

        unsavedGPS = GPSDataHandler.getNotSavedId();

        for (int temp = 1; temp <= unsavedGPS.size() && temp < max; temp++) {
            int tempID = unsavedGPS.get(unsavedGPS.size()-temp);

            JSONObject tmpG = new JSONObject();

            tmpGPS = GPSDataHandler.getDataByID(tempID);
            tmpG.put("GPSDataID", tempID);
            tmpG.put("UserID", tmpGPS.getUserID());
            tmpG.put("Latitude", tmpGPS.getLatitude());
            tmpG.put("Longitude", tmpGPS.getLongitude());
            tmpG.put("Altitude", tmpGPS.getAltitude());
            tmpG.put("Data", tmpGPS.getDateTime());
            gpsJData.put(tmpG);
        }

        return gpsJData;
    }

    public void setDataState(JSONArray data, boolean state) throws JSONException {
        JSONObject tempJson = new JSONObject();
        for (int i = 0; i < data.length(); i++) {
            tempJson = data.getJSONObject(i);
            if (state = true)
                GPSDataHandler.changeSavedStatus(tempJson.getInt("GPSDataID"), state);
        }
    }

}
