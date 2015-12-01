package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FriendsActivity extends ActionBarActivity {
        TextView followrs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        ListView listView = (ListView)findViewById(R.id.listView_friends);

        new  Friends(FriendsActivity.this,listView,FriendsActivity.this).execute();








    }
}

