package com.meeladsd.memoriesapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends ActionBarActivity {


    private ListView mDrawerList;
    private ListView vacationList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Button btnViewVac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("TEST_DEVICE_ID")
                .build();
        adView.loadAd(adRequest);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();



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
                }
                if (position == 1) {

                    Intent intent = new Intent(view.getContext(), FriendsActivity.class);
                    startActivity(intent);
                }
                if (position == 4) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                    adb.setTitle("Alert")
                            .setMessage("are you sure you want to delete this user")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    DeleteUser test = new DeleteUser(MainActivity.this);
                                    test.execute();

                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            }).show();

                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Log.e("Luke", "before we begin");
        final ListAdapter adapter = new ListAdapter(this,R.layout.itemlistrow);
        ListView vacationlist = (ListView)this.findViewById(R.id.ListofVacations);
        vacationlist.setAdapter(adapter);
        Log.d("Luke", "OMG it works");
        vacationlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(v.getContext(), ViewVacationActivity.class);
                intent.putExtra("VacationID", adapter.getItem(position).VacationID);
                startActivity(intent);
            }
        });

        btnViewVac = (Button) findViewById(R.id.btn_view_vac);

        btnViewVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshData(adapter);
            }
        });

    }

    private void RefreshData(ListAdapter adapter)
    {
        new GetVacationList(this, adapter).execute();
    }



    private void addDrawerItems() {
        String[] myitems = {"My vacations", "My friends", "Log out", "My Profile", "Delete current user"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myitems);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {
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
        if (id == R.id.action_add) {
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
