package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Dictionary;

/**
 * Created by Hamster on 7/12/2015.
 */
public class VacationListHandler {

    private static Activity _myContext;
    private static JSONArray ResultsList;
    private static int saveRate = 20;
    private ListAdapter Connection = null;

    public VacationListHandler(Activity c, int s, ListAdapter adapter)
    {
        _myContext = c;
        saveRate = s;
        Connection = adapter;
    }

    public void checkForFile()
    {
        File folder = _myContext.getFilesDir();

        File theFile = new File(folder, "JsonList.txt");
        boolean answer = theFile.exists();
        if (answer == true)
        {
            ReadFromFile();
            PopulateList(ResultsList, null);
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

    public void PopulateList(JSONArray Inputarray, Dictionary<String,String> Friends)
    {
        try {
            for (int i = 0; i < Inputarray.length() -1; i++)
            {
                VacationItem NextItem = new VacationItem();
                Log.d("Luke", "Oli" + i);
                JSONObject Steve = Inputarray.getJSONObject(i);
                if (Steve.getString("Title").equals("null"))
                {
                    NextItem.Title = "Unnamed " + i;
                } else
                {
                    NextItem.Title = Steve.getString("Title");
                }
                if (Steve.getString("Place").equals("null"))
                {
                    NextItem.Place = "Undefined";
                } else
                {
                    NextItem.Place = Steve.getString("Place");
                }
                NextItem.VacationID = Integer.parseInt(Steve.getString("VacationId"));

                if (Friends != null && Friends.isEmpty() != true)
                {
                    try{
                        NextItem.UserName = Friends.get(Steve.getString("UserId"));
                    }
                    catch (Exception ex)
                    {
                        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
                        String Username = myS.getString("username", "");
                        NextItem.UserName = Username;
                    }

                }


                Connection.add(NextItem);

            }




        } catch (Exception e) {
            Log.e("Luke", e.getMessage());
        }

        Log.d("Luke", "Do I die again");
    }

}
