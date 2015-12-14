package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ViewVacationActivity extends ActionBarActivity {

    private RelativeLayout _memText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vacation);
        Button deleteBtnVac = (Button)findViewById(R.id.btnDeleteVac);
        Intent i = getIntent();
        final int VacID = i.getIntExtra("VacationID", 0);

        deleteBtnVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                new DeleteVacation(, ViewVacationActivity.this).execute();
=======
                new DeleteVacation(VacID, ViewVacationActivity.this).execute();
>>>>>>> origin/master
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
<<<<<<< HEAD
        new ViewVacation(2, ViewVacationActivity.this).execute();
=======
        new ViewVacation(VacID, ViewVacationActivity.this).execute();
>>>>>>> origin/master
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent1 = new Intent(this,MainActivity.class);
                startActivity(parentIntent1);
                return true;
            case  R.id.icon_edit:
                Intent intent_1 = new Intent(this,EditVacationActivity.class);
                startActivity(intent_1);
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
