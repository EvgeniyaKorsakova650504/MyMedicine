package com.tritpo.mymedicine;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMedication extends AppCompatActivity {

    EditText nameEt, dateBeginEt, dateEndEt, timeEt, countEt;
    DBHelperMedication DB;
    RadioGroup scheduleRg;
    Button addTime, OKbtn, btnDateBegin, btnDateEnd;
    String schedule, dateBegin, dateEnd;
    String time = "";
    ArrayList<String> tempTime;
    Calendar cal;
    public static int ALARM_TYPE_ELAPSED = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        nameEt = findViewById(R.id.editTextAddPillName);
        countEt = findViewById(R.id.editTextBreaks);
        scheduleRg = findViewById(R.id.radioGroup);
        addTime = findViewById(R.id.addPillBtn);
        OKbtn = findViewById(R.id.buttonPillOK);
        btnDateBegin = findViewById(R.id.btnDateBegin);
        btnDateEnd = findViewById(R.id.btnDateEnd);
        DB = new DBHelperMedication(this);
        tempTime = new ArrayList<>();
        cal = Calendar.getInstance();

        scheduleRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        schedule = "every day";
                        break;
                    case R.id.radioButtonEveryDay:
                        schedule = "every day";
                        break;
                    case R.id.radioButtonWithBreaks:
                        schedule = "with breaks";
                        break;

                    default:
                        break;
                }
            }
        });
    }

    public void onClickDateBegin(View view) {
        showDialog(2);
    }


    DatePickerDialog.OnDateSetListener myCallBackDateBegin = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dateBegin = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year);
        }
    };

    public void onClickDateEnd(View view) {
        showDialog(3);
    }


    DatePickerDialog.OnDateSetListener myCallBackDateEnd = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            dateEnd = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year);
        }
    };


    public void onClickAddTime(View view) {
        showDialog(1);
    }


    TimePickerDialog.OnTimeSetListener myCallBackTime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String mHour = String.valueOf(hourOfDay);
            String mMinute = String.valueOf(minute);
            String value = mHour + ":" + mMinute;
            tempTime.add(value);

        }
    };

    protected Dialog onCreateDialog(int id) {
        if (id == 1) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBackTime, 0, 0, true);
            return tpd;
        }
        if (id == 2) {
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBackDateBegin, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            return dpd;
        }
        if (id == 3) {
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBackDateEnd, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            return dpd;
        }

        return super.onCreateDialog(id);
    }

    public void onClickOKPill(View v) {
        ContentValues cv = new ContentValues();
        String name = nameEt.getText().toString();
        int count;
        if (schedule == "every day") count = 0;
        else count = Integer.parseInt(countEt.getText().toString());

        for (int i = 0; i < tempTime.size() - 1; i++) {
            String first = tempTime.get(i);
            String two = tempTime.get(i + 1);
            if (first.equals(two)) {
                tempTime.remove(i);
            }
        }
        time = tempTime.toString();


        SQLiteDatabase db = DB.getWritableDatabase();

        cv.put(DB.COLUMN_NAME, name);
        cv.put(DB.COLUMN_DATE_BEGIN, dateBegin);
        cv.put(DB.COLUMN_DATE_END, dateEnd);
        cv.put(DB.COLUMN_TIME, time);
        cv.put(DB.COLUMN_SCHEDULE, schedule);
        cv.put(DB.COLUMN_COUNT, count);
        db.insert(DB.TABLE_NAME, null, cv);
        DB.close();
        setAlarm();
        finish();
    }
    private void setAlarm() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(this, Receiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        for (int i = 0; i < tempTime.size(); i++) {

            String temp=tempTime.get(i);
            int hour = Integer.parseInt(temp.substring(0, temp.indexOf(':')));
            int minute = Integer.parseInt(temp.substring(temp.indexOf(':') + 1, temp.length()));

            Calendar calendar = Calendar.getInstance();
            int nowH=calendar.get(Calendar.HOUR_OF_DAY);
            int nowM=calendar.get(Calendar.MINUTE);



            int setHour = hour - nowH;
            int setMin = minute - nowM;


            long alarmTime = ((setHour * 60 + setMin-1 ) * 60  ) * 1000;

            if (alarmTime > 0)
                manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+
                        alarmTime,AlarmManager.INTERVAL_DAY, pendingIntent);
            else {
                alarmTime=24*60*60*1000 -alarmTime;
                manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+
                        alarmTime,AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }


    }


}

