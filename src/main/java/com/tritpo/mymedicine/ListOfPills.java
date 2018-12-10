package com.tritpo.mymedicine;

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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListOfPills extends AppCompatActivity {

    final DBHelperMedication DB=new DBHelperMedication(this);
    SQLiteDatabase db;
    ListView list;
    ArrayAdapter adapter;
    Cursor cursor;
    AlertDialog.Builder ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_of_pills);
        db = DB.getWritableDatabase();
        list=findViewById(R.id.listPills);

        show();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent=new Intent(ListOfPills.this,Medication.class);
                intent.putExtra("position",position);
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

    protected void delete(final int pos){
        ad = new AlertDialog.Builder(ListOfPills.this);
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

    public void onClickAdd (View v){
        Intent intent = new Intent(this, AddMedication.class);
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
