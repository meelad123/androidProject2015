package com.meeladsd.memoriesapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ViewMemoryByIdActivity extends ActionBarActivity {

    private ImageView _imgCam, _imgAudio, _imgVideo;
    public static ArrayList<Bitmap> _bitmapArray;
    public static ArrayList<Bitmap> _videoArray;
    public static ArrayList<Bitmap> _soundpArray;
    public static final int IMAGE_GALLERY = 1;
    public static final int IMAGE_FROM_CAMERA = 2;
    public static final int VIDEO_GALLRY = 3;
    private GridView _gridView;
    private ImageAdapter _imageAdapter;
    private TextView _txtAudio, _txtVideo;
    public String _outputFile;
    private byte[] _vidBytes;
    private Button _btnDel;

    private String _memId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memory_by_id);
        Intent i = getIntent();
        _memId = i.getStringExtra("memId");

        _btnDel = (Button) findViewById(R.id.btnDeleteMem);

        _btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteMemory(_memId, ViewMemoryByIdActivity.this).execute();
            }
        });
        _outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        _bitmapArray = new ArrayList<Bitmap>();
        _imageAdapter = new ImageAdapter(this, _bitmapArray);
        _gridView = (GridView) findViewById(R.id.grd_imagess);
        _gridView.setAdapter(_imageAdapter);

        new ViewMemoryById(_memId, ViewMemoryByIdActivity.this).execute();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        _imgCam = (ImageView)this.findViewById(R.id.img_camera);
        _imgAudio = (ImageView)this.findViewById(R.id.img_audio);
        _txtAudio = (TextView) findViewById(R.id.txt_audio);
        _txtVideo = (TextView) findViewById(R.id.txt_video);
        _imgVideo = (ImageView) findViewById(R.id.img_video);

        _imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_txtVideo.getText() != "Video: 1") {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, VIDEO_GALLRY);
                }

            }
        });

        _imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((_txtAudio.getText() != "Audio: 1")) {
                    AudioActivity cdd = new AudioActivity(ViewMemoryByIdActivity.this);
                    cdd.show();
                }
            }
        });
        if (_bitmapArray.size() == 0){
            _imgCam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String[] items = new String[]{"From Camera", "From Gallery"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.select_dialog_item, items);
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Choose an action!");
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            if (item == 0) {
                                Intent reg = new Intent(getApplicationContext(), CameraPreviewActivity.class);

                                startActivityForResult(reg, IMAGE_FROM_CAMERA);
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
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_memories, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.icon_upload:
                if(_bitmapArray.size() == 1 && _txtVideo.getText() == "Video: 1" && _txtAudio.getText() == "Audio: 1") {
                    byte[] sAr = getAudioBytes();
                    new ImageUpload(Integer.parseInt(_memId), _bitmapArray, this).execute();
                    new AudioUpload(Integer.parseInt(_memId), sAr, this).execute();
                    new VideoUpload(Integer.parseInt(_memId), _vidBytes, this).execute();
                }
                else if(_bitmapArray.size() == 1) {
                    new ImageUpload(Integer.parseInt(_memId), _bitmapArray, this).execute();
                }
                else if(_txtAudio.getText() == "Audio: 1"){
                    byte[] sAr = getAudioBytes();
                    new AudioUpload(Integer.parseInt(_memId), sAr, this).execute();
                }
                else if(_txtVideo.getText() == "Video: 1"){
                    new VideoUpload(Integer.parseInt(_memId), _vidBytes, this).execute();
                }else if(_bitmapArray.size() == 1 && _txtVideo.getText() == "Video: 1"){
                    new ImageUpload(Integer.parseInt(_memId), _bitmapArray, this).execute();
                    new VideoUpload(Integer.parseInt(_memId), _vidBytes, this).execute();
                }else if(_bitmapArray.size() == 1 && _txtAudio.getText() == "Audio: 1"){
                    byte[] sAr = getAudioBytes();
                    new AudioUpload(Integer.parseInt(_memId), sAr, this).execute();
                    new ImageUpload(Integer.parseInt(_memId), _bitmapArray, this).execute();
                }else if(_txtVideo.getText() == "Video: 1" && _txtAudio.getText() == "Audio: 1"){
                    byte[] sAr = getAudioBytes();
                    new AudioUpload(Integer.parseInt(_memId), sAr, this).execute();
                    new VideoUpload(Integer.parseInt(_memId), _vidBytes, this).execute();
                }
                else {
                    Toast.makeText(this, "No media selected to upload!", Toast.LENGTH_SHORT).show();
                }
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    _bitmapArray.add(imgFetched);
                    _imageAdapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Unable to open the image", Toast.LENGTH_LONG).show();
                }
            }
            if(requestCode == IMAGE_FROM_CAMERA){
                byte[] b = data.getByteArrayExtra("imageData");
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                _bitmapArray.add(bitmap);
                _imageAdapter.notifyDataSetChanged();
            }
            if(requestCode == VIDEO_GALLRY){
                _txtVideo.setText("Video: 1");
                Uri vidUri = data.getData();
                Log.d("uri url", vidUri.getPath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FileInputStream fis;
                File f = new File(getAbsolutePathFromURI(vidUri));
                try {
                    fis = new FileInputStream(f);
                    byte[] buf = new byte[1024];
                    int n;
                    while (-1 != (n = fis.read(buf)))
                        baos.write(buf, 0, n);
                    Log.d("err", "hej");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                _vidBytes = baos.toByteArray();

            }
        }
    }
    public String getAbsolutePathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(index);
    }

    public byte[] getAudioBytes(){
        File file = new File(_outputFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
            Log.d("err", "hej");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  baos.toByteArray();
    }
}
