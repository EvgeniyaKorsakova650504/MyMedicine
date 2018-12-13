package com.tritpo.mymedicine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Measure extends AppCompatActivity {

    DBHelperMeasure DB;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_item);
        DBHelperMeasure DB=new DBHelperMeasure(this);
        db = DB.getReadableDatabase();
        Intent intent=getIntent();
        showItem(intent.getIntExtra("position",1));
    }

    public void showItem(int position){
            setContentView(R.layout.activity_measure_item);

            TextView name=findViewById(R.id.MeasureName);
            TextView date=findViewById(R.id.DateMeasure);
            TextView time=findViewById(R.id.TimeMeasure);
            TextView desc=findViewById(R.id.Description);

            Cursor cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);

            if (cursor.moveToPosition(position)) {

                int nameColIndex = cursor.getColumnIndex(DB.COLUMN_NAME);
                int dateColIndex = cursor.getColumnIndex(DB.COLUMN_DATE);
                int timeColIndex = cursor.getColumnIndex(DB.COLUMN_TIME);
                int descColIndex = cursor.getColumnIndex(DB.COLUMN_DESCRIPTION);

                name.setText(cursor.getString(nameColIndex));
                date.setText(cursor.getString(dateColIndex));
                time.setText(cursor.getString(timeColIndex));
                desc.setText(cursor.getString(descColIndex));

            }

            cursor.close();
        }
}

