package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        TextView textView_name, textView_Freinds, txtViewVacations;
        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_Freinds = (TextView) findViewById(R.id.friends_item_counter);
        txtViewVacations = (TextView) findViewById(R.id.vacations_item_counter);

      getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);



        // Enable the Up button

        new MyProfile1(MyProfileactivity2.this, textView_name, textView_Freinds, txtViewVacations).execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
