package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;

public class EditProfileActivity extends ActionBarActivity {
      private   TextView FnameTxtview;
    private   TextView Lnametxtview;
    private   TextView EmaiTxtview;
    private   TextView userNameTxtview;
    private Button editBtn ,deleteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
      FnameTxtview=(TextView)findViewById(R.id.userFirstName);
        Lnametxtview=(TextView)findViewById(R.id.userLasttName);
        EmaiTxtview =(TextView)findViewById(R.id.userEmail);
        userNameTxtview = (TextView)findViewById(R.id.username);
        SharedPreferences detalisOfUser = this.getSharedPreferences("profile", Context.MODE_PRIVATE);
        String Fname = detalisOfUser.getString("Fname", "");
        String Lname = detalisOfUser.getString("Lname","");
        String email = detalisOfUser.getString("email","");
        String userName = detalisOfUser.getString("username","");
        final String userId = detalisOfUser.getString("UserId","");
        FnameTxtview.setText(Fname);
        Lnametxtview.setText(Lname);
        EmaiTxtview.setText(email);
        userNameTxtview.setText(userName);

        deleteBtn=(Button)findViewById(R.id.deleteUserBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            new DeleteUser(EditProfileActivity.this).execute();

                Intent login1 = new Intent(v.getContext(), LogInActivity.class);

                startActivity(login1);




            }
        });
        editBtn = (Button)findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String [] myList ={userId,FnameTxtview.getText().toString(),Lnametxtview.getText().toString(),EmaiTxtview.getText().toString(),userNameTxtview.getText().toString()};
                new EditUser(FnameTxtview, Lnametxtview, EmaiTxtview, userNameTxtview,myList, EditProfileActivity.this).execute();

            }
        });



        }

    }
