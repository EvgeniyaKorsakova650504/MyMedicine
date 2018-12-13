package com.tritpo.mymedicine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperMedication  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MedicationSQL";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "medicationTable";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE_BEGIN = "dateBegin";
    public static final String COLUMN_DATE_END = "dateEnd";
    public static final String COLUMN_NAME= "name";
    public static final String COLUMN_SCHEDULE= "schedule";
    public static final String COLUMN_COUNT = "countDays";


    public DBHelperMedication(Context context) {
        super(context, DATABASE_NAME , null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.i("LOG_TAG", "--- onCreate ---");

        db.execSQL("CREATE TABLE "+ TABLE_NAME +"("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT," + COLUMN_DATE_BEGIN + " TEXT,"  +COLUMN_DATE_END + " TEXT,"+
                COLUMN_TIME + " TEXT," + COLUMN_SCHEDULE + " TEXT," + COLUMN_COUNT + " NUMERIC" + ");");
        Log.i("LOG_TAG", "--- onCreate ---");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}
