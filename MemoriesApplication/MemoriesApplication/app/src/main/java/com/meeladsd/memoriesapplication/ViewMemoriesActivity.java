package com.meeladsd.memoriesapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewMemoriesActivity extends ActionBarActivity {

    private ListView _lstMem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memories);

        _lstMem = (ListView)findViewById(R.id.memories_list);

        new ViewMemoriesList(19, _lstMem, this, getApplicationContext()).execute();


    }
}
