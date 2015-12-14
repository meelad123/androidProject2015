package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Hamster on 2/11/2015.
 */
public class LukesTesting extends Activity

{


    public static JSONArray InternetList;
    private Button btntest;
    private Button NextTest;
    private Button screwthis;
    private TextView outputtest;
    private ListView VacationsList;

    public String lol = "http://jthcloudproject.elasticbeanstalk.com/api/v1/users/";

    private HttpResponse resp = null;

    public void getvacations()
    {
        Log.d("Luke", "still going");
        new LukesNetworkTest("meelad2", lol, this).execute();


    }

    public void DoWeHaveoldFiles()
    {
        File folder = this.getFilesDir();
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = folder.listFiles();
        for (File file : files) {
            if(file.getName().endsWith(".txt")){
                inFiles.add(file);
            }

        }
        for (File file: inFiles) {
            Log.d("Luke", "The Files exist?: " + file.toString());
            Log.d("Luke", "How many Files: " +inFiles.size());
            Log.d("Luke", "Can Write: " +file.canWrite());
            file.delete();
        }
    }

    public void PopulateList()
    {
        File folder = this.getFilesDir();

        File theFile = new File(folder, "JsonList.txt");
        File[] TheList = folder.listFiles();
        for (int i = 0; i <TheList.length; ++i)
        {
            Log.d("Luke", "File List: " + TheList[i].toString());
        }

        JSONArray Results = null;
        try {
            Log.e("Luke", "hello again 1");
            String s = new String();
            FileInputStream fIn = new FileInputStream(theFile);
            s = LukesJSON.ReadInputStream(fIn);

            Log.e("Luke", "hello again 2" + s);
            Results = LukesJSON.ParseStringToJsonArray(s);
            Log.e("Luke", "hello again 3");
            Log.d("Luke", "the results are" + Results.toString());
        } catch (Exception e) {
            Log.e("Luke", e.getMessage());

        }
        outputtest.setText("Got the list");
        if (Results == null) {

        } else {
            try {
                String[] sing = new String[Results.length()];
                for (int i = 0; i < Results.length() -1; i++)
                {
                    Log.d("Luke", "Oli" + i);
                    JSONObject Steve = Results.getJSONObject(i);
                    if (Steve.getString("Title") == "null")
                    {
                        sing[i] = "Unnamed " + i;
                    } else
                    {
                        sing[i] = Steve.getString("Title");
                    }
                }
                VacationArrayAdapter adapter = new VacationArrayAdapter(this, sing);
                VacationsList.setAdapter(adapter);
                Log.d("Luke", "OMG it works");

            } catch (Exception e) {
                Log.e("Luke", e.getMessage());
            }


        }

        Log.d("Luke", "Do I die again");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.lukestestingface);

        Log.d("Luke", "still going");

       // outputtest = (TextView)findViewById(R.id.outPutData);
       // VacationsList = (ListView)findViewById(R.id.ListOfVacations);

       // btntest = (Button)findViewById(R.id.beginTest);

        Log.d("Luke", "still going");

        Log.d("Luke", "is this where i die");
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Luke", "Or Here");
                Log.d("Luke", "still going");
                getvacations();
            }
        });

       // NextTest = (Button)findViewById(R.id.NextTest);

        Log.d("Luke", "still going");

        Log.d("Luke", "is this where i die");
        NextTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopulateList();
            }
        });

       // screwthis = (Button)findViewById(R.id.Screwthis);

        Log.d("Luke", "still going");

        Log.d("Luke", "is this where i die");
        screwthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoWeHaveoldFiles();
            }
        });

    }

    public String readSavedData (File F )
    {
        StringBuffer datax = new StringBuffer("");
        try
        {
            FileInputStream fIn = new FileInputStream(F) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            Log.e("Luke", "We are reading lines "+ readString);
            while ( readString != null )
            {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
                Log.e("Luke", "We are reading lines "+ readString);
            }

            isr.close ( ) ;
        }
        catch ( IOException ioe )
        {
            ioe.printStackTrace ( ) ;
        }
        return datax.toString() ;
    }

}

