package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
<<<<<<< HEAD
<<<<<<< HEAD
                switch (position) {
                    case 0:
                        Intent intent_Vac = new Intent(view.getContext(), CreateVacationaActivity.class);
                        startActivity(intent_Vac);
                        break;

                    case 2:
                        SharedPreferences userDetails = view.getContext().getSharedPreferences(getString(R.string.str_token), MODE_PRIVATE);
                        SharedPreferences.Editor editor = userDetails.edit();

                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(view.getContext(), LogInActivity.class);
                        startActivity(intent);
                        break;
=======
=======
>>>>>>> parent of fadf28a... post create vacation works
                if (position == 2) {
                    SharedPreferences userDetails = view.getContext().getSharedPreferences(getString(R.string.str_token), MODE_PRIVATE);
                    SharedPreferences.Editor editor = userDetails.edit();

                    editor.clear();
<<<<<<< HEAD
                    editor.apply();

                    Intent intent = new Intent(view.getContext(), LogInActivity.class);
                    startActivity(intent);
                    finish();
>>>>>>> origin/master
                }
=======
                    editor.commit();
>>>>>>> parent of fadf28a... post create vacation works

                    Intent intent = new Intent(view.getContext(), LogInActivity.class);
                    startActivity(intent);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void addDrawerItems() {
        String[] myitems = { "My vacations", "My friends", "Log out"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myitems);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("My Items");
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(id == R.id.action_add)
        {
            Intent intent = new Intent(this, CreateVacationaActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}
