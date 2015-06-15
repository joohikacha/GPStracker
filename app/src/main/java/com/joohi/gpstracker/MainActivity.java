package com.joohi.gpstracker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;


public class MainActivity extends Activity implements OnMapReadyCallback{

    DatabaseAdapter adapter;
    public static final String PREFS_NAME = "MyPrefs";
    public coordinates [] sghighway;
    public coordinates [] home;
    public coordinates [] demo_road1;
    public coordinates [] demo_road2;
    public coordinates [] road3;
    public coordinates [] road1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Working with Maps
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        googleMapOptions.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);

        mapFragment.newInstance(googleMapOptions);
        //First of all define all segments
        initializeValues();

        //Shared preference for storing in DB
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean firstStart = settings.getBoolean("firstStart", true);

        //Make database entry for geofence coordinates only once.
        if(firstStart) {
            adapter = new DatabaseAdapter(this);
            //Function for adding entry into DB
            addSegments();
            Toast.makeText(this, "Database operation complete", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstStart", false);
            editor.commit();
        }

        //This runs a service in background, which just returns a toast message of Coordinates
        //startService(new Intent(this,LocationService.class));


        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //For getting the current location
        MyLocationListener mlocListener = new MyLocationListener(this,this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        //Checking whether current location is in any restricted area or not
        Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null){
            Toast.makeText(this,"Null hai yaar!!",Toast.LENGTH_LONG).show();
        }
        coordinates [][] all_coords = new coordinates[][]{road1,road3};
        checkForPoint(all_coords);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeValues(){

        //Initialize road 1
        road1 = new coordinates[6];
        double road1_lat [] = new double[]{23.04047263774957, 23.040440550554298, 23.040420804584166, 23.04029986045397, 23.04033194768277, 23.040371439646172};
        double road1_lon []= new double[]{72.53228813409805, 72.53098458051682, 72.52974539995193, 72.52980977296829, 72.53135204315186, 72.53229886293411};
        for(int i=0;i<road1_lat.length;i++){
            road1[i] = new coordinates(road1_lat[i],road1_lon[i]);
        }

        //Initialize road 3
        road3 = new coordinates[7];
        double road3_lat [] = new double[]{23.038767069314645, 23.038939848576028, 23.03908054009661, 23.03892257065987, 23.038846054147335, 23.038690552713806, 23.038599226391398};
        double road3_lon [] = new double[]{72.53017991781235, 72.5312152504921, 72.53233909606934, 72.53235787153244, 72.53165781497955, 72.5307297706604, 72.53021746873856};
        for (int i=0;i<road3_lat.length;i++){
            road3[i] = new coordinates(road3_lat[i],road3_lon[i]);
        }
        //Initialize demo road 1
        demo_road1 = new coordinates[16];
        double demo1_lat [] = new double[]{23.040455360029984, 23.0404282093233, 23.040408463351362, 23.040415868091184, 23.040383780882372, 23.040065376626927, 23.039835828906188, 23.039482867669278, 23.038739918267705, 23.038759664484203, 23.039440907181003, 23.039828424134484,23.040322074690103, 23.04032947943467, 23.040351693665915, 23.040368971398813};
        double demo1_lon [] = new double[] {72.53226399421692,72.53107577562332,72.53034889698029,72.5298473238945,72.52977222204208,72.52988755702972,72.52995997667313,72.53001362085342,72.53011018037796,72.53019869327545,72.53010481595993, 72.53003776073456,72.52988487482071,72.53035962581635,72.53137350082397,72.53227472305298};
        for(int i=0;i<demo1_lat.length;i++){

            demo_road1[i] = new coordinates(demo1_lat[i],demo1_lon[i]);
        }

        //Initializing demo road 2
        demo_road2 = new coordinates[20];
        double demo2_lat [] = new double[]{23.0387571962073, 23.03886826862319, 23.03892503893372, 23.038991682311238, 23.03903611121126, 23.039065730469797, 23.039581598178344, 23.039991329017816, 23.040455360029984, 23.040470169504054, 23.039912344856564, 23.03939647841449, 23.038974404401706, 23.03893244375509, 23.038883078271734, 23.03886086379831, 23.038811498288734, 23.03869302099193, 23.038621440907963, 23.038596758111556};
        double demo2_lon [] = new double[]{72.53018528223038,72.53080487251282,72.531199157238,72.53165245056152,72.5320628285408,72.53232568502426,72.53230959177017,72.5322961807251,72.5322613120079,72.53234446048737,72.53237932920456,72.53239005804062,72.53241151571274,72.5323498249054,72.5318643450737,72.53166049718857,72.53124207258224,72.53071904182434,72.53030866384506,72.53021210432053};
        for(int i=0;i<demo2_lat.length;i++){
            demo_road2[i] = new coordinates(demo2_lat[i],demo2_lon[i]);
        }

        //Initializing SG Highway segment
        sghighway  = new coordinates[7];
        double segment_lat []= new double[]{23.049920750139297, 23.048400408985046, 23.047274950580903, 23.045952031308417, 23.046149482771135, 23.04841028138557, 23.05004908984069 };
        double segment_long []= new double[]{72.51755475997925,72.51695394515991,72.5163745880127,72.51577377319336,72.5153124332428,72.5163745880127,72.51710414886475};
        for(int i=0;i<segment_lat.length;i++) {
            sghighway[i] = new coordinates(segment_lat[i], segment_long[i]);
        }

        //Initializing home segment
        home = new coordinates[4];
        double home_lat [] = new double[]{23.041854848136985,23.041968386217043,23.040892238830633,23.040803382240206,};
        double home_long [] = new double[]{72.53221035003662, 72.53297746181488, 72.53313302993774, 72.53226399421692};
        for(int i=0;i<home_lat.length;i++){
            home[i] = new coordinates(home_lat[i],home_long[i]);
        }
    }

    //For adding road segments to the database
    public void addSegments(){

        // DB record 1
        for(int i=0;i<sghighway.length;i++){
            long id = adapter.insertData(1,"sg highway",sghighway[i].latitude,sghighway[i].longitude,40);
        }

        //DB record 2
        for(int i=0;i<home.length;i++){
            long id = adapter.insertData(2,"home",home[i].latitude,home[i].longitude,20);
        }

        //DB record 3
        for(int i=0;i<demo_road1.length;i++){
            long id = adapter.insertData(3,"demo_road1",demo_road1[i].latitude,demo_road1[i].longitude,20);
        }

        //DB record 4
        for(int i=0;i<demo_road2.length;i++){
            long id = adapter.insertData(4, "demo_road2", demo_road2[i].latitude, demo_road2[i].longitude, 40);
        }

        //DB record 5
        for(int i=0;i<road1.length;i++){
            long id = adapter.insertData(5,"road1",road1[i].latitude,road1[i].longitude,20);
        }

        //DB record 6
        for(int i=0;i<road3.length;i++){
            long id = adapter.insertData(6,"road3",road3[i].latitude,road3[i].longitude,20);
        }
    }

    //For checking whether the current location falls in the given segment
    public void checkForPoint(final coordinates[][] segment){

        final Thread thread = new Thread(){
            @Override
            public void run() {
                LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                while (true) {
                    Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    pointInPolygon point = new pointInPolygon();
                    for (int i = 0; i < segment.length; i++) {
                        boolean pointStatus = point.FindPoint(location.getLatitude(), location.getLongitude(), segment[i]);
                        if (pointStatus) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    TextView txt_msg = (TextView) findViewById(R.id.text_msg);
                                    String msg = "You are in speed restricted area";
                                    txt_msg.setText(msg);
                                    //Toast.makeText(getApplicationContext(), "You are in restricted area", Toast.LENGTH_LONG).show();
                                }
                            });
                            while (true){
                                Location location1 = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                boolean still_inside = point.FindPoint(location1.getLatitude(),location1.getLongitude(),segment[i]);
                                if(!still_inside){
                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            TextView txt_msg = (TextView) findViewById(R.id.text_msg);
                                            String msg = "";
                                            txt_msg.setText(msg);
                                            //Toast.makeText(getApplicationContext(), "You are in restricted area", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                        /*else {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "You are not in restricted area..Move Freely!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }*/
                    }
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        //Nothing to print
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
