package com.tritpo.mymedicine;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMeasure extends AppCompatActivity {

    EditText nameEt, dateEt, timeEt, decriptionEt;
    DBHelperMeasure DB;
    Button OkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure);
        nameEt = findViewById(R.id.editTextAddMeasureName);
        dateEt = findViewById(R.id.editTextDate);
        timeEt = findViewById(R.id.editTextTime);
        decriptionEt = findViewById(R.id.editText);
        DB=new DBHelperMeasure(this);
        OkBtn=findViewById(R.id.buttonOK2);


    }


    public void onClickOK (View v) {
        Log.i("LOG_TAG", "--- Insert in mytable: ---");
        ContentValues cv = new ContentValues();
        String name = nameEt.getText().toString();
        String date = dateEt.getText().toString();
        String time = timeEt.getText().toString();
        String description = decriptionEt.getText().toString();
        Log.i("LOG_TAG", "--- create db ---");
        SQLiteDatabase db;
        Log.i("LOG_TAG", "--- create db ---");
            db = DB.getWritableDatabase();


        Log.i("LOG_TAG", "--- Insert ---");

        cv.put(DB.COLUMN_NAME,name);
        cv.put(DB.COLUMN_DATE,date);
        cv.put(DB.COLUMN_TIME,time);
        cv.put(DB.COLUMN_DESCRIPTION,description);

        db.insert(DB.TABLE_NAME, null, cv);
        DB.close();

        Intent intent = new Intent(this, ListMeasure.class);
        startActivity(intent);
    }

}
