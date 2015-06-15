package com.joohi.gpstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;

/**
 * Created by joohi_k on 11-06-2015.
 */
public class DatabaseAdapter {
    DatabaseHelper helper;
    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    public long insertData(int id , String name , double latitude, double longitude, int speed ){

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseHelper.SEGMENT_ID, id);
        contentValues.put(DatabaseHelper.SEGMENT_NAME,name);
        contentValues.put(DatabaseHelper.SEGMENT_LATITUDE,latitude);
        contentValues.put(DatabaseHelper.SEGMENT_LONGITUDE,longitude);
        contentValues.put(DatabaseHelper.SPEED_LIMIT, speed);

        long rowid = db.insert(DatabaseHelper.SEGMENT_TABLE, null,contentValues);

        return rowid;

    }
}