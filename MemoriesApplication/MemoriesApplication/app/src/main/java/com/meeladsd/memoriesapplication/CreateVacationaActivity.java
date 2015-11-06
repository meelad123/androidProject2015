package com.meeladsd.memoriesapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


    public static ArrayList<Bitmap> bitmapArray;
    private GridView gridView;
    public static final int IMAGE_GALLERY = 1;
    ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacationa);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();



        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
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

        /*logic for the gridview and displaying the images that the user selected
        * Meelad*/
        Bitmap firstIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_insert_photo_white_48dp);
        bitmapArray = new ArrayList<Bitmap>();
        bitmapArray.add(firstIcon);

        gridView = (GridView) findViewById(R.id.grd_images);
        imageAdapter = new ImageAdapter(this, bitmapArray);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(position == 0)
                {
                    Intent imageGalleryInten = new Intent(Intent.ACTION_PICK);
                    File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                    String picDirPath = picDir.getPath();

                    Uri data = Uri.parse(picDirPath);

                    imageGalleryInten.setDataAndType(data, "image/*");
                    startActivityForResult(imageGalleryInten, IMAGE_GALLERY);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_GALLERY){
                Uri imgUri = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imgUri);
                    Bitmap imgFetched = BitmapFactory.decodeStream(inputStream);
                    bitmapArray.add(imgFetched);
                    imageAdapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Unable to open the image", Toast.LENGTH_LONG).show();
                }

            }

        }
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

}
