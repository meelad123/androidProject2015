package com.meeladsd.memoriesapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RelativeLayout;

public class ViewVacationActivity extends ActionBarActivity {

    private RelativeLayout _memText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vacation);

        new ViewVacation(22, ViewVacationActivity.this).execute();
    }
}
