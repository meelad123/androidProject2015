package com.meeladsd.memoriesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

public class FriendsActivity extends ActionBarActivity {
    private Context context = this;
    private    String userInputValue;
    private FloatingActionButton addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        ListView listView = (ListView)findViewById(R.id.listView_friends);
        SearchView sr = (SearchView)findViewById(R.id.searchViewforFriends);




        new  Friends(FriendsActivity.this,listView,sr,FriendsActivity.this).execute();




        addBtn = (FloatingActionButton ) findViewById(R.id.addFriends);
        addBtn.setRippleColor(Color.RED);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder inputAlert = new AlertDialog.Builder(context);
                inputAlert.setTitle("Add Friend");
                inputAlert.setMessage("Enter persons username ");
                final EditText userInput = new EditText(context);
                inputAlert.setView(userInput);

                inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         userInputValue = userInput.getText().toString();

                        new addFriend(context,userInputValue ).execute();


                    }
                });
                inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = inputAlert.create();
                alertDialog.show();
            }
        });



    }
}

