package com.joohi.gpstracker;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();
        String Text1 = "Latitude = "+ location.getLatitude();
        String Text2 = "Longitude = " + location.getLongitude();
        String Text3 = "Current Speed = " + location.getSpeed();

        TextView txtspeed = (TextView)activity.findViewById(R.id.text_speed);
        txtspeed.setText(Text3);
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
