package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class EditProfileActivity extends ActionBarActivity {
    private   TextView FnameTxtview;
    private   TextView Lnametxtview;
    private   TextView EmaiTxtview;

    private Button editBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_profile);
        FnameTxtview=(TextView)findViewById(R.id.userFirstName);
        Lnametxtview=(TextView)findViewById(R.id.userLasttName);
        EmaiTxtview =(TextView)findViewById(R.id.userEmail);
        SharedPreferences detalisOfUser = this.getSharedPreferences("profile", Context.MODE_PRIVATE);
        String Fname = detalisOfUser.getString("Fname", "");
        String Lname = detalisOfUser.getString("Lname","");
        String email = detalisOfUser.getString("email","");
        final String userName = detalisOfUser.getString("username","");
        final String userId = detalisOfUser.getString("UserId","");
        FnameTxtview.setText(Fname);
        Lnametxtview.setText(Lname);
        EmaiTxtview.setText(email);

        editBtn = (Button)findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String [] myList ={userId,FnameTxtview.getText().toString(),Lnametxtview.getText().toString(),EmaiTxtview.getText().toString(),userName};
                new EditUser(FnameTxtview, Lnametxtview, EmaiTxtview,myList, EditProfileActivity.this).execute();

            }
        });



        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    }
