package com.meeladsd.memoriesapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ViewVacationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vacation);

        new ViewVacation(7, ViewVacationActivity.this).execute();
    }
}
