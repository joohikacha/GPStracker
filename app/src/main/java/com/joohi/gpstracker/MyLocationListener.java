package com.joohi.gpstracker;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by joohi_k on 08-06-2015.
 */
public class MyLocationListener extends MainActivity implements LocationListener  {
    private Context context;
    Activity activity;
    public MyLocationListener(Context context,Activity activity){
        this.context = context;
        this.activity = activity;
    }
    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        String Text1 = "Latitude = "+ location.getLatitude();
        String Text2 = "Longitude = " + location.getLongitude();
        String Text3 = "Current Speed = " + location.getSpeed();
        //TextView txtlat = (TextView)activity.findViewById(R.id.text_lat);
        //TextView txtlon = (TextView)activity.findViewById(R.id.text_lon);
        TextView txtspeed = (TextView)activity.findViewById(R.id.text_speed);
        //txtlat.setText(Text1);
        //txtlon.setText(Text2);
        txtspeed.setText(Text3);
        //Toast.makeText(context.getApplicationContext(), Text1 + Text2, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
