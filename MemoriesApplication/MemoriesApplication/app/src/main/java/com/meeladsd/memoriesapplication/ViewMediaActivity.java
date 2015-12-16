package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ViewMediaActivity extends ActionBarActivity {

    private String _memId;
    private ListView _lstMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_media);
        _memId = getIntent().getStringExtra("MEM_ID");
        _lstMedia = (ListView)findViewById(R.id.media_list_pics);

        _lstMedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String medurl = (String)view.getTag();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mybuckpics.s3.amazonaws.com/"+ medurl));
                startActivity(browserIntent);
            }
        });

        new ViewMediaList(_memId, _lstMedia, this, getApplicationContext()).execute();
    }
}
