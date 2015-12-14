package com.meeladsd.memoriesapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by meeladsd on 10/18/2015.
 */
public class LukesJSON
{

    public static JSONArray parseJSONArrayResponse(InputStream stream)
    {
        String s = ReadInputStream(stream);
        Log.d("Luke", "Hello, is it me your looking for?" +s);
        return ParseStringToJsonArray(s);
    }

    public static String ReadInputStream(InputStream stream)
    {
        BufferedReader reader;
        StringBuilder builder = new StringBuilder();

        try
        {
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            for (String line = null; (line = reader.readLine()) != null; )
            {
                builder.append(line).append("\n");
                Log.e("Luke", "We have " + line);
            }
            reader.close();
            return builder.toString();
        }
        catch (Exception e)
        {
            Log.e("Luke", "reading" + e.getMessage());
            return "";
        }
    }


    public static JSONArray ParseStringToJsonArray(String input)
    {
        JSONArray result = new JSONArray();
        try
        {
            Log.d("Luke", "parsing well 1");
            StringBuilder lol = new StringBuilder();
            lol.append("{\"Vacations\":");
            lol.append(input);
            lol.append("}");
            JSONObject tokener = new JSONObject(lol.toString());
            Log.d("Luke", "parsing well 2");
            result = tokener.getJSONArray("Vacations");
            Log.d("Luke", "parsing well 3");
        }
        catch (Exception e)
        {
            Log.e("Luke", "parsing" + e.getMessage());
        }
        return result;

    }
}
