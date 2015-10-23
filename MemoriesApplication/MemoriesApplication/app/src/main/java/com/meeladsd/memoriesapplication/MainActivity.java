package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = (Button)findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences userDetails = v.getContext().getSharedPreferences(getString(R.string.str_token), MODE_PRIVATE);
                SharedPreferences.Editor editor = userDetails.edit();

                editor.clear();
                editor.commit();

                Intent intent = new Intent(v.getContext(), LogInActivity.class);
                startActivity(intent);
            }
        });
    }

}
