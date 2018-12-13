package com.tritpo.mymedicine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListMeasure extends AppCompatActivity {
    DBHelperMeasure DB;
    SQLiteDatabase db;
    ListView list;
    Cursor cursor;
    ArrayAdapter adapter;
    AlertDialog.Builder ad;
    Button find, dateBtn;
    TextView text;
    Calendar cal;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_measure);
        DBHelperMeasure DB=new DBHelperMeasure(this);
        db = DB.getWritableDatabase();
        list=findViewById(R.id.listMeasures);
        find=findViewById(R.id.findBtn);
        dateBtn=findViewById(R.id.editTextDate);
        text=findViewById(R.id.textMeasure);
        cal=Calendar.getInstance();
        show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent=new Intent(ListMeasure.this,Measure.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                delete(position);
                return true;

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();

    }

    protected void delete(final int pos){
        ad = new AlertDialog.Builder(ListMeasure.this);
        //ad.setTitle(title);  // заголовок
        ad.setMessage("Delete?"); // сообщение
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);
                cursor.moveToPosition(pos);
                int idColIndex=cursor.getColumnIndex(DB.COLUMN_ID);
                db.delete(DB.TABLE_NAME, "_id = " + Integer.parseInt(cursor.getString(idColIndex)), null);
                cursor.close();
                adapter.clear();
                show();
            }
        });
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        ad.show();

    }

    public void onClickDateFind(View view) {
        showDialog(1);
    }


    DatePickerDialog.OnDateSetListener myCallBackDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year);
        }
    };


    protected Dialog onCreateDialog(int id) {

        if (id == 1) {
            DatePickerDialog dpd = new DatePickerDialog(this, myCallBackDate, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            return dpd;
        }

        return super.onCreateDialog(id);
    }

    public void onClickFind (View v){
        cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<String> items=new ArrayList<>();

        if (cursor.moveToFirst()) {

            int dateColIndex = cursor.getColumnIndex(DB.COLUMN_DATE);
            int nameColIndex = cursor.getColumnIndex(DB.COLUMN_NAME);

            do {

                if (date.equals(cursor.getString(dateColIndex)))
                {
                    text.setText("Find measures");
                    items.add(cursor.getString(nameColIndex));

                }

            } while (cursor.moveToNext());

        }
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        list.setAdapter(adapter);

        cursor.close();

    }

    public void onClickAddMeasure (View v){
        Intent intent = new Intent(this, AddMeasure.class);
        startActivity(intent);
    }

    public void show(){

        ArrayList<String> items;
        cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);
        items= new ArrayList<>();

        if (cursor.moveToFirst()) {

            int nameColIndex = cursor.getColumnIndex(DB.COLUMN_NAME);

            do {

                items.add(cursor.getString(nameColIndex));

            } while (cursor.moveToNext());

        }
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        list.setAdapter(adapter);

        cursor.close();
    }
}
