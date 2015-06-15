package com.joohi.gpstracker;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by joohi_k on 11-06-2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /*--- database related strings ---*/
    private static final String DATABASE_NAME = "GPSTracker";
    private static final int DATABASE_VERSION = 1;
    public static final String SEGMENT_TABLE = "segment_master_table";

    public static final String SEGMENT_ID = "segment_id";
    public static final String SEGMENT_NAME = "segment_name";
    public static final String SEGMENT_LATITUDE = "segment_latitude";
    public static final String SEGMENT_LONGITUDE = "segment_longitude";
    public static final String SPEED_LIMIT = "speed_limit";

    public static final String CREATE_SEGMENT_TABLE = "CREATE TABLE "
            + SEGMENT_TABLE + "(" + SEGMENT_ID + " INTEGER, "
            + SPEED_LIMIT + " INTEGER, "
            + SEGMENT_NAME + " TEXT, "
            + SEGMENT_LATITUDE + " INTEGER, "
            + SEGMENT_LONGITUDE + " INTEGER"
            + ")";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS" + SEGMENT_TABLE;
    /* --------------------------------- */

    private static DatabaseAdapter instance;
    private static Context context;

    public static synchronized DatabaseAdapter getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseAdapter(context);
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            Toast.makeText(context.getApplicationContext(),CREATE_SEGMENT_TABLE,Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL(CREATE_SEGMENT_TABLE);
            Toast.makeText(context.getApplicationContext(),"table created",Toast.LENGTH_SHORT).show();
        } catch (SQLException e){
            //nothing to do
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {

        }
    }
}
