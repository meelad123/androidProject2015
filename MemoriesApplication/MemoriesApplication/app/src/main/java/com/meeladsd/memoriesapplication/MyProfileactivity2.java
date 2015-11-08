package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyProfileactivity2 extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profileactivity2);
        TextView textView_name ,textView_Freinds, txtViewVacations ;
        textView_name =(TextView)findViewById(R.id.textView_name);
        textView_Freinds = (TextView)findViewById(R.id.txtFriends);
        txtViewVacations = (TextView)findViewById(R.id.vacationsView);


        new MyProfile1(MyProfileactivity2.this,textView_name,textView_Freinds,txtViewVacations).execute();


    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();

    }
}
