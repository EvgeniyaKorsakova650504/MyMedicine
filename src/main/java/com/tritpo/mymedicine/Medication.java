package com.tritpo.mymedicine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Medication extends AppCompatActivity {

    DBHelperMedication DB;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_item);
        DBHelperMedication DB=new DBHelperMedication(this);
        db = DB.getReadableDatabase();
        Intent intent=getIntent();
        showItem(intent.getIntExtra("position",1));
    }

    public void showItem(int position){
        setContentView(R.layout.activity_medication_item);

        TextView name=findViewById(R.id.MedicationName);
        TextView dateBegin=findViewById(R.id.DateBegin);
        TextView dateEnd=findViewById(R.id.DateEnd);
        TextView listTime=findViewById(R.id.ListOfTime);
        TextView schedule=findViewById(R.id.Schedule);
        ArrayAdapter adapterTime;

        Cursor cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<String> time= new ArrayList<>();

        if (cursor.moveToPosition(position)) {

            int nameColIndex = cursor.getColumnIndex(DB.COLUMN_NAME);
            int dateBeginColIndex = cursor.getColumnIndex(DB.COLUMN_DATE_BEGIN);
            int dateEndColIndex = cursor.getColumnIndex(DB.COLUMN_DATE_END);
            int timeColIndex = cursor.getColumnIndex(DB.COLUMN_TIME);
            int scheduleColIndex = cursor.getColumnIndex(DB.COLUMN_SCHEDULE);
            int countColIndex = cursor.getColumnIndex(DB.COLUMN_COUNT);

            name.setText(cursor.getString(nameColIndex));
            dateBegin.setText(cursor.getString(dateBeginColIndex));
            dateEnd.setText(cursor.getString(dateEndColIndex));
            listTime.setText(cursor.getString(timeColIndex));
            String sched=cursor.getString(scheduleColIndex);
            if (sched.equals("with breaks")) sched+=(" through " + cursor.getString(countColIndex)+ " days");
            schedule.setText(sched);
           /* for (int i=1; i<tempTime.length()-1; i++){
                if (tempTime.charAt(i) != ',') temp+=tempTime.charAt(i);
                else {
                    time.add(temp);
                    temp="";
                }
            }*/
        }


        cursor.close();
    }
}
