package com.meeladsd.memoriesapplication;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by meeladsd on 10/18/2015.
 */
public class JsonHelper {

    public static JSONObject parseJSONObjectResponse(InputStream stream) {

        BufferedReader reader;
        StringBuilder builder = new StringBuilder();
        JSONObject result = new JSONObject();

        try {
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(builder.toString());
            result = new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public static JSONArray parsArray(InputStream stream) {

        BufferedReader reader;
        StringBuilder builder = new StringBuilder();
        JSONArray result = new JSONArray();

        try {
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(builder.toString());
            result = new JSONArray(tokener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
