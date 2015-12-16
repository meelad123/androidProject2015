package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ViewMyVacations extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_vacations);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ListView ls = (ListView)findViewById(R.id.listViewMyVacations);


        new getMyVacAsync(ls,this.getApplicationContext(),2).execute();

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition     = position;
                // ListView Clicked item value
                              String Vacid = (String) view.getTag();

                Intent intent = new Intent(getApplicationContext(),ViewVacationActivity.class);
                intent.putExtra("VacationID", Integer.parseInt(Vacid));
                startActivity(intent);
            }
        });




    }

}
