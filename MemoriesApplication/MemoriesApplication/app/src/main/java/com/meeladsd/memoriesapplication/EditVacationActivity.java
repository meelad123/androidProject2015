package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditVacationActivity extends ActionBarActivity {
    private TextView _txtTitle,_txtDesc,_txtPlace,_txtStartDate,_txtEndDate, _txtMemCounter;
    private Button saveBtn;
    int VacID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vacation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        saveBtn = (Button)findViewById(R.id.saveVacBtn);
        _txtTitle =(TextView) findViewById(R.id.edit_title_desc);
        _txtDesc =(TextView) findViewById(R.id.edit_desc2_desc);
        _txtPlace =(TextView) findViewById(R.id.edit_place_desc);
        _txtStartDate =(TextView) findViewById(R.id.edit_start_date_desc);
        _txtEndDate =(TextView) findViewById(R.id.edit_end_date_desc);
        Intent i = getIntent();
        VacID = i.getIntExtra("VacationID", 0);
        new ViewVacation(VacID,this).execute();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String VacTitle = _txtTitle.getText().toString();
                String VacDes =_txtDesc.getText().toString();
                String VacPlace=_txtPlace.getText().toString();
                String VacStart = _txtStartDate.getText().toString();
                String VacEnd= _txtEndDate.getText().toString();
                new SaveEditVacation(VacTitle,VacDes,VacPlace,VacStart,VacEnd,VacID,EditVacationActivity.this).execute();
            }
        });}

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }







    }

