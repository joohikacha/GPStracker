package com.joohi.gpstracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by joohi_k on 13-06-2015.
 */
public class broadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        String coords = "Latitude" + b.getDouble("Latitude") + "Longitude" + b.getDouble("Longitude");
        Toast.makeText(context,coords, Toast.LENGTH_LONG).show();
    }
}
