package com.meeladsd.memoriesapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateVacationaActivity extends ActionBarActivity {
    private Button create_vacaion_btn;
    private  EditText title,description,start,end,place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacationa);


        create_vacaion_btn = (Button) findViewById(R.id.create_vacation_btn);
        create_vacaion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateVacationFunk(v);

            }
        });


    }

    private void CreateVacationFunk(View v) {

        title = (EditText) findViewById(R.id.title_txt);
        description = (EditText) findViewById(R.id.description_txt);
        place = (EditText) findViewById(R.id.place_txt);
        start = (EditText) findViewById(R.id.start_txt);
        end = (EditText) findViewById(R.id.end_txt);
        new CreateVacation (title.getText().toString(),
                            description.getText().toString(),
                            place.getText().toString(),
                            start.getText().toString(),
                            end.getText().toString(),
                            getApplicationContext()).execute();

    }






}
