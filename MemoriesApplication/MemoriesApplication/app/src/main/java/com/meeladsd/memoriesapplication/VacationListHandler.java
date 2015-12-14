package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Hamster on 7/12/2015.
 */
public class VacationListHandler {

    private static Activity _myContext;
    private static JSONArray ResultsList;
    private static int saveRate = 20;

    public VacationListHandler(Activity c, int s)
    {
        _myContext = c;
        saveRate = s;
    }

    public void checkForFile()
    {
        File folder = _myContext.getFilesDir();

        File theFile = new File(folder, "JsonList.txt");
        boolean answer = theFile.exists();
        if (answer == true)
        {
            ReadFromFile();
            PopulateList(ResultsList);
        }
        else
        {
            TextView title = (TextView)_myContext.findViewById(R.id.title);
            title.setText("No Saved vacations, connect to internet");
        }

    }

    public void ReadFromFile()
    {
        Log.d("Luke", "we are reading from file");
        File folder = _myContext.getFilesDir();

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
            Results = LukesJSON.ParseStringToJsonArray(s);
        } catch (Exception e) {
            Log.e("Luke", e.getMessage());

        }
        ResultsList = Results;
    }

    public void Writetofile(JSONArray Inputarray)
    {
        File folder = _myContext.getFilesDir();
        File theFile = new File(folder, "JsonList.txt");
        JSONArray transfer = new JSONArray();
        for (int i = 0; i < saveRate; i++ )
        {
            try {
                transfer.put(i, Inputarray.optJSONObject(i));
            }
            catch (JSONException e)
            {
                Log.e("luke", e.getMessage());
            }
        }

        String JsonUnconverted = transfer.toString();
        try{
            FileOutputStream outputStream = new FileOutputStream(theFile);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            writer.flush();
            writer.write(JsonUnconverted);
            outputStream.close();
        }catch (Exception e){
            Log.e("Luke", e.getMessage());
        }
    }

    public void PopulateList(JSONArray Inputarray)
    {
        try {
            String[] sing = new String[Inputarray.length()];
            for (int i = 0; i < Inputarray.length() -1; i++)
            {
                Log.d("Luke", "Oli" + i);
                JSONObject Steve = Inputarray.getJSONObject(i);
                if (Steve.getString("Title") == "null")
                {
                    sing[i] = "Unnamed " + i;
                } else
                {
                    sing[i] = Steve.getString("Title");
                }
            }
            VacationArrayAdapter adapter = new VacationArrayAdapter(_myContext, sing);
            ListView vacationlist = (ListView)_myContext.findViewById(R.id.ListofVacations);
            vacationlist.setAdapter(adapter);
            Log.d("Luke", "OMG it works");

        } catch (Exception e) {
            Log.e("Luke", e.getMessage());
        }

        Log.d("Luke", "Do I die again");
    }
}
