package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Hamster on 3/11/2015.
 */
public class LukesNetworkTest extends AsyncTask<String, Void, Void>
{
    private String _userName, _url, JsonUnconverted;
    private Activity _myContext;
    private HttpResponse resp = null;

    LukesNetworkTest(String userName, String url, Activity c)
    {
        _userName = userName;
        _url = url;
        _myContext = c;
    }

    @Override
    protected Void doInBackground(String... params)
    {

        Log.d("Luke", "still going");
        String eh = _url + _userName + "/vacations";
        HttpGet httpGet = new HttpGet(eh);
        HttpClient clive = new DefaultHttpClient();
        String token = null;

        try
        {
            Log.d("Luke", "accessing token");
            SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);

            token = myS.getString("access_token", "");
            Log.d("Luke",token);
        }
        catch (Exception e)
        {
            Log.e("Luke",e.getMessage());
        }

        try
        {
            httpGet.addHeader("Accept", "application/json");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("Authorization", "Bearer " + token);
            resp = clive.execute(httpGet);

            JsonUnconverted = LukesJSON.ReadInputStream(resp.getEntity().getContent());
            JSONArray meh = LukesJSON.ParseStringToJsonArray(JsonUnconverted);
            return null;

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void v)
    {
        TextView test = (TextView)_myContext.findViewById(R.id.outPutData);
        test.setText("Yay");
        Log.d("Luke","the file" + JsonUnconverted);
        File folder = _myContext.getFilesDir();
        File theFile = new File(folder, "JsonList.txt");
        try{
            FileOutputStream outputStream = new FileOutputStream(theFile);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            writer.flush();
            writer.write(JsonUnconverted, 0, JsonUnconverted.length());
            outputStream.close();
        }catch (Exception e){
            Log.e("Luke", e.getMessage());
        }
        Log.d("Luke", "the file all done");


    }
}
