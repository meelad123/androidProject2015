package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


public class MyProfileactivity2 extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profileactivity2);
        TextView textView_name, textView_Freinds, txtViewVacations;
        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_Freinds = (TextView) findViewById(R.id.userFirstName);
        txtViewVacations = (TextView) findViewById(R.id.vacations_item_counter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                Intent parentIntent1 = new Intent(this,MainActivity.class);
                startActivity(parentIntent1);
                return true;

            case  R.id.icon_edit:
                Intent intent_1 = new Intent(this,EditProfileActivity.class);
                startActivity(intent_1);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
