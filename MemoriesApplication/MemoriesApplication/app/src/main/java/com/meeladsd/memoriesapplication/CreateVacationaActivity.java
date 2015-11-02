package com.meeladsd.memoriesapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateVacationaActivity extends ActionBarActivity {
    private Button create_vacaion_btn;
    private EditText title;
    private EditText description;
    private String start;
    private String end;
    private EditText place;
    TextView startVac;
    TextView EndVac;

    Calendar myCalender = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalender.set(Calendar.YEAR, year);
            myCalender.set(Calendar.MONTH, monthOfYear);
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Start_updateLabel();
        }

    };


    Calendar myCalender_2 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date_2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalender.set(Calendar.YEAR, year);
            myCalender.set(Calendar.MONTH, monthOfYear);
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            End_updateLabel();
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacationa);

        startVac = (TextView) findViewById(R.id.Vac_start);
        EndVac = (TextView) findViewById(R.id.Vac_End);
        create_vacaion_btn = (Button) findViewById(R.id.create_vacation_btn);
        startVac.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalender
                        .get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
                        myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        EndVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date_2, myCalender
                        .get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
                        myCalender.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

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
        place = (EditText)findViewById(R.id.place_txt);

        new CreateVacation(title.getText().toString(),
                description.getText().toString(),
                place.getText().toString(),
                start.toString(),
                end.toString(),
                getApplicationContext()).execute();

    }

    private void Start_updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        startVac.setText(sdf.format(myCalender.getTime()));
        start=startVac.getText().toString();
    }

    private void End_updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        EndVac.setText(sdf.format(myCalender.getTime()));
        end=EndVac.getText().toString();
    }


}
