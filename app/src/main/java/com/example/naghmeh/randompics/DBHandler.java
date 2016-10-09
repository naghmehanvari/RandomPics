package com.example.naghmeh.randompics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naghmeh on 10/8/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "savedImages";
    // Contacts table name
    private static final String TABLE_IMAGES = "images";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_URL = "url";
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_IMAGES + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_URL + " TEXT)";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        // Creating tables again
        onCreate(db);
    }

    public void addImage(Image image)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_URL, image.getUrl());

        // Inserting Row
        db.insert(TABLE_IMAGES, null, values);
        db.close(); // Closing database connection
    }


    public List<String> getAllImages()
    {
        List<String> imageList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_IMAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                imageList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return imageList;
    }
}


