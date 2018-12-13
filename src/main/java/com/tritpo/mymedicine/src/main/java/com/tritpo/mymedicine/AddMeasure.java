package com.tritpo.mymedicine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddMeasure extends AppCompatActivity {

    EditText nameEt,  decriptionEt;
    Button dateEt, timeEt;
    DBHelperMeasure DB;
    Button OkBtn;
    String date,time;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure);
        nameEt = findViewById(R.id.editTextAddMeasureName);
        dateEt = findViewById(R.id.btnDate);
        timeEt = findViewById(R.id.editTextTime);
        decriptionEt = findViewById(R.id.editText);
        DB=new DBHelperMeasure(this);
        OkBtn=findViewById(R.id.buttonOK2);
        cal = Calendar.getInstance();



    }

    public void onClickDate(View view) {
        showDialog(2);
    }


    DatePickerDialog.OnDateSetListener myCallBackDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year);
        }
    };


    public void onClickTime(View view) {
        showDialog(1);
    }


    TimePickerDialog.OnTimeSetListener myCallBackTime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String mHour = String.valueOf(hourOfDay);
            String mMinute = String.valueOf(minute);
            String value = mHour + ":" + mMinute;
            time=value;

        }
    };

    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBackTime, 0, 0, true);
            return tpd;
        }
        if (id == 2) {
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBackDate, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            return dpd;
        }

        return super.onCreateDialog(id);
    }




    public void onClickOK (View v) {
        ContentValues cv = new ContentValues();
        String name = nameEt.getText().toString();
        String description = decriptionEt.getText().toString();
        SQLiteDatabase db;
        db = DB.getWritableDatabase();


        cv.put(DB.COLUMN_NAME,name);
        cv.put(DB.COLUMN_DATE,date);
        cv.put(DB.COLUMN_TIME,time);
        cv.put(DB.COLUMN_DESCRIPTION,description);

        db.insert(DB.TABLE_NAME, null, cv);
        DB.close();
        finish();
    }

}
