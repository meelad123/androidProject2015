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
import android.widget.Button;
import android.widget.ListView;
public class MainActivity extends ActionBarActivity {


    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Button LukesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        LukesButton = (Button) findViewById(R.id.Steve);
        LukesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LukesTesting.class);
                startActivity(intent);
                finish();

            }
        });


        addDrawerItems();
        setupDrawer();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 2) {
                    SharedPreferences userDetails = view.getContext().getSharedPreferences(getString(R.string.str_token), MODE_PRIVATE);
                    SharedPreferences.Editor editor = userDetails.edit();
                    SharedPreferences userName = view.getContext().getSharedPreferences("Name", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = userName.edit();
                    editor1.clear();
                    editor1.apply();
                    editor.clear();
                    editor.apply();


                    Intent intent = new Intent(view.getContext(), LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (position == 3) {

                    Intent intent = new Intent(view.getContext(), MyProfileactivity2.class);
                    startActivity(intent);
                    finish();
                }
                if (position == 4) {
                    DeleteUser test = new DeleteUser(MainActivity.this);
                    test.execute();



                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }
    private void addDrawerItems() {
        String[] myitems = { "My vacations", "My friends", "Log out","My Profile","Delete current user"};
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
