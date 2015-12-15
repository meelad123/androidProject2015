package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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


    public void PopulateList(JSONArray Inputarray)
    {
        try {
            Connection.clear();
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

                Connection.add(NextItem);

            }




        } catch (Exception e) {
            Log.e("Luke", e.getMessage());
        }

        Log.d("Luke", "Do I die again");
    }

    public void writeToFile(JSONArray Inputarray, String Filename) {
        JSONArray transfer = new JSONArray();
        for (int i = 0; i < saveRate; i++ )
        {
            try {
                JSONObject transObj = Inputarray.optJSONObject(i);
                if (transObj != null)
                {
                    transfer.put(i,transObj);
                }
            }
            catch (JSONException e)
            {
                Log.e("luke", e.getMessage());
            }
        }

        String JsonUnconverted = transfer.toString();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(_myContext.openFileOutput(Filename, _myContext.MODE_PRIVATE));
            outputStreamWriter.write(JsonUnconverted);
            outputStreamWriter.close();
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public JSONArray ReadFromFile(String Filename) {

        String ret = "";
        JSONArray Results = null;

        try {
            InputStream inputStream = _myContext.openFileInput(Filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                Results = LukesJSON.ParseStringToJsonArray(ret);
                ResultsList = Results;
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (Exception e) {
        Log.e("login activity", e.toString());
        }

        return Results;
    }

}
