package com.tourism.app.fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tourism.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Show_Map_Fragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static Show_Map_Fragment newInstance() {
        return new Show_Map_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_map_fragment, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.backround));

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        try {
            mapFragment.getMapAsync(this);
        }catch (NullPointerException npe){
            Toast.makeText(this.getContext(),npe.getMessage(),Toast.LENGTH_SHORT).show();
            Log.e("NPE",npe.getStackTrace().toString());
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.addPolyline(new PolylineOptions().addAll());
        // Add a marker in Sydney and move the camera
        LatLng loc= new LatLng(41.096630942221005,-7.808231537426082);
        mMap.addMarker(new MarkerOptions().position(loc).title("Olá de ESTGL"));
        mMap.addPolyline(newPolyline());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));

    }


    //TODO: Change the coordinates/JSONArray to server data
    private JSONArray getPolylineData()throws JSONException{
        return new JSONArray("[[41.093424999999996, -7.816678333333334], [41.09322166666667, -7.816635000000001], [41.09321333333334, -7.816236666666667], [41.09317333333333, -7.8158650000000005], [41.09303499999999, -7.8154916666666665], [41.09291666666666, -7.815600000000001], [41.09294833333333, -7.815873333333333], [41.09286, -7.816256666666668], [41.092688333333335, -7.81663], [41.09231, -7.817093333333334], [41.092025, -7.817415], [41.091606666666664, -7.817741666666666], [41.09129500000001, -7.817898333333333], [41.09097499999999, -7.81803]]");
    }


    private PolylineOptions newPolyline()  {
        PolylineOptions polylineOptions = new PolylineOptions().width(15)
                .color(Color.BLUE);

        try {

            JSONArray latlong = getPolylineData();

            for (int i=0; i < latlong.length();i++){
                JSONArray temp = latlong.getJSONArray(i);
                polylineOptions.add(new LatLng(temp.getDouble(0), temp.getDouble(1)));
            }

        } catch (JSONException jsonEx){
            jsonEx.printStackTrace();
        }

        return polylineOptions;
    }
}
