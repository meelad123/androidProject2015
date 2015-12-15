package com.meeladsd.memoriesapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
/**
 * Created by meeladsd on 11/9/2015.
 */
public class CreateVacationaActivity extends ActionBarActivity {
    private Button _create_vacaion_btn;
    private EditText _title, _description,_place;
    private String _start, _end;
    private TextView _startVac, _EndVac;
    private Calendar _myCalender = Calendar.getInstance();

    public static ArrayList<Bitmap> _bitmapArray;
    private GridView _gridView;
    public static final int IMAGE_GALLERY = 1;
    public static final int IMAGE_CAMERA = 2;
    private ImageAdapter _imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacationa);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();



        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        _startVac = (TextView) findViewById(R.id.Vac_start);
        _EndVac = (TextView) findViewById(R.id.Vac_End);
        _create_vacaion_btn = (Button) findViewById(R.id.create_vacation_btn);
        _startVac.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, _myCalender
                        .get(Calendar.YEAR), _myCalender.get(Calendar.MONTH),
                        _myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        _EndVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date_2, _myCalender
                        .get(Calendar.YEAR), _myCalender.get(Calendar.MONTH),
                        _myCalender.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        _create_vacaion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateVacationFunk(v);

            }
        });

        /*logic for the gridview and displaying the images that the user selected
        * Meelad*/
        Bitmap firstIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_insert_photo_white_48dp);
        _bitmapArray = new ArrayList<Bitmap>();
        _bitmapArray.add(firstIcon);

        _gridView = (GridView) findViewById(R.id.grd_images);
        _imageAdapter = new ImageAdapter(this, _bitmapArray);
        _gridView.setAdapter(_imageAdapter);

        _gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0 && _bitmapArray.size() < 4) {

                    final String [] items           = new String [] {"From Camera", "From Gallery"};
                    ArrayAdapter<String> adapter  = new ArrayAdapter<String> (v.getContext(), android.R.layout.select_dialog_item,items);
                    AlertDialog.Builder builder     = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Choose an action!");
                    builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface dialog, int item ) {
                            if (item == 0) {
                                Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camIntent, IMAGE_CAMERA);
                               dialog.dismiss();
                            } else {
                                Intent imageGalleryInten = new Intent(Intent.ACTION_PICK);
                                File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                                String picDirPath = picDir.getPath();

                                Uri data = Uri.parse(picDirPath);

                                imageGalleryInten.setDataAndType(data, "image/*");
                                startActivityForResult(imageGalleryInten, IMAGE_GALLERY);
                                dialog.dismiss();
                            }
                        }
                    } );
                    final AlertDialog dialog = builder.create();
                    dialog.show();

                } else if(position != 0){
                    _bitmapArray.remove(position);
                    _imageAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
                Uri imgUri = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imgUri);
                    Bitmap imgFetched = BitmapFactory.decodeStream(inputStream);
                    _bitmapArray.add(imgFetched);
                    _imageAdapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Unable to open the image", Toast.LENGTH_LONG).show();
                }


        }
    }

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

    private void CreateVacationFunk(View v) {

        _title = (EditText) findViewById(R.id.title_txt);
        _description = (EditText) findViewById(R.id.description_txt);
        _place = (EditText)findViewById(R.id.place_txt);


        new CreateVacation(_title.getText().toString(),
                _description.getText().toString(),
                _place.getText().toString(),
                _start.toString(),
                _end.toString(),
                CreateVacationaActivity.this,
                _bitmapArray).execute();

    }

    private void Start_updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        _startVac.setText(sdf.format(_myCalender.getTime()));
        _start = _startVac.getText().toString();
    }

    private void End_updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        _EndVac.setText(sdf.format(_myCalender.getTime()));
        _end = _EndVac.getText().toString();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            _myCalender.set(Calendar.YEAR, year);
            _myCalender.set(Calendar.MONTH, monthOfYear);
            _myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Start_updateLabel();
        }

    };


    Calendar myCalender_2 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date_2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            _myCalender.set(Calendar.YEAR, year);
            _myCalender.set(Calendar.MONTH, monthOfYear);
            _myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            End_updateLabel();
        }

    };

}
