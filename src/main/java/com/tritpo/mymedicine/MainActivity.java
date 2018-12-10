package com.tritpo.mymedicine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onClickPills (View v){
        Intent intent = new Intent(this, ListOfPills.class);
        startActivity(intent);
    }

    public void onClickMeasures (View v){
        Intent intent = new Intent(this, ListMeasure.class);
        startActivity(intent);
    }




}
