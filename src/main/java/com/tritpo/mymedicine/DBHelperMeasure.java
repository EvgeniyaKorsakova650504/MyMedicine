package com.tritpo.mymedicine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


class DBHelperMeasure extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MeasureSQL";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "measureTable";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NAME= "name";
    public static final String COLUMN_DESCRIPTION= "description";


    public DBHelperMeasure(Context context) {
        super(context, DATABASE_NAME , null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE "+ TABLE_NAME +"("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COLUMN_NAME + " TEXT," + COLUMN_DATE + " TEXT,"  +
        COLUMN_TIME + " TEXT," + COLUMN_DESCRIPTION + " TEXT" + ");");


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}

