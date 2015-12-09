package com.meeladsd.memoriesapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;

public class ViewMemoriesActivity extends ActionBarActivity {

    private ListView _lstMem;
    private ImageView _imgCam;
    public static final int IMAGE_GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memories);

        _lstMem = (ListView)findViewById(R.id.memories_list);
        new ViewMemoriesList(22, _lstMem, this, getApplicationContext()).execute();

        _imgCam = (ImageView)this.findViewById(R.id.img_camera);

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

                            startActivity(reg);
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
