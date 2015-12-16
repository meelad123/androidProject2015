package com.meeladsd.memoriesapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;

import android.widget.AdapterView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ViewMemoriesActivity extends ActionBarActivity {

    private ListView _lstMem;

    private int _vacId;
    private AdapterView.OnItemClickListener itemClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            String myId = (String)v.getTag();
            Intent mem = new Intent(getApplicationContext(), ViewMemoryByIdActivity.class);
            mem.putExtra("memId", myId);
            startActivity(mem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memories);

        _vacId = getIntent().getIntExtra("VAC_ID", -1);

        _lstMem = (ListView)findViewById(R.id.memories_list);
        _lstMem.setOnItemClickListener(itemClickedHandler);

        new ViewMemoriesList(_vacId, _lstMem, this, getApplicationContext()).execute();
    }

}
