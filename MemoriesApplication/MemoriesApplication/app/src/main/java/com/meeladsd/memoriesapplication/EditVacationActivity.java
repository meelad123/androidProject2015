package com.meeladsd.memoriesapplication;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditVacationActivity extends ActionBarActivity {
    private TextView _txtTitle,_txtDesc,_txtPlace,_txtStartDate,_txtEndDate, _txtMemCounter;
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vacation);
        saveBtn = (Button)findViewById(R.id.saveVacBtn);
        _txtTitle =(TextView) findViewById(R.id.edit_title_desc);
        _txtDesc =(TextView) findViewById(R.id.edit_desc2_desc);
        _txtPlace =(TextView) findViewById(R.id.edit_place_desc);
        _txtStartDate =(TextView) findViewById(R.id.edit_start_date_desc);
        _txtEndDate =(TextView) findViewById(R.id.edit_end_date_desc);
        new ViewVacation(22,this).execute();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveEditVacation(_txtTitle,_txtDesc,_txtPlace,_txtStartDate,_txtEndDate,8,EditVacationActivity.this).execute();
            }
        });







    }

}
