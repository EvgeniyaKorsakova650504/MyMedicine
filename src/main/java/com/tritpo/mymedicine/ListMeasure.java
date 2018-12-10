package com.tritpo.mymedicine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListMeasure extends AppCompatActivity {
    DBHelperMeasure DB;
    SQLiteDatabase db;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_measure);
        DBHelperMeasure DB=new DBHelperMeasure(this);
        db = DB.getWritableDatabase();
        list=findViewById(R.id.listMeasures);
        show();
    }

    public void onClickAddMeasure (View v){
        Intent intent = new Intent(this, AddMeasure.class);
        startActivity(intent);
    }

    public void show(){

        Cursor c = db.query(DB.TABLE_NAME, null, null, null, null, null, null);



        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex(DB.COLUMN_ID);
            int nameColIndex = c.getColumnIndex(DB.COLUMN_NAME);
            int dateColIndex = c.getColumnIndex(DB.COLUMN_DATE);
            int timeColIndex = c.getColumnIndex(DB.COLUMN_TIME);
            int descriptionColIndex = c.getColumnIndex(DB.COLUMN_DESCRIPTION);
            do {
                Log.i("LOG_TAG",c.getString(nameColIndex));



            } while (c.moveToNext());
        }

        c.close();
    }
}
