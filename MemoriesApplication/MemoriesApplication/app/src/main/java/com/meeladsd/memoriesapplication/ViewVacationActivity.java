package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewVacationActivity extends ActionBarActivity {

    private RelativeLayout _memText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vacation);

        _memText = (RelativeLayout)findViewById(R.id.layout_memories);

        _memText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mem = new Intent(v.getContext(), ViewMemoriesActivity.class);

                startActivity(mem);

            }
        });
        new ViewVacation(7, ViewVacationActivity.this).execute();
    }
}
