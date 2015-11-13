package com.meeladsd.memoriesapplication;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sevag on 2015-11-11.
 */
public class DeleteUser extends AsyncTask<String,String,Integer>
{
    Activity _myContext;
    int state;
    ProgressDialog progress;

    DeleteUser(Activity activity)
    {
        _myContext = activity;
        progress = new ProgressDialog(activity);

    }



    @Override
    protected Integer doInBackground(String... params) {
        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences myS2 = _myContext.getSharedPreferences("Name", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        String name = myS2.getString("Fname", "");

        DefaultHttpClient client = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name);
        httpDelete.addHeader("authorization", "bearer " + t);

        try {

            HttpResponse response = client.execute(httpDelete);
             state = response.getStatusLine().getStatusCode();
        } catch (IOException e) {

            Toast.makeText(_myContext, e.getMessage(), Toast.LENGTH_LONG).show();


            e.printStackTrace();
        }


        return state;
    }


    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (progress.isShowing()) {
            progress.dismiss();
        }
        if (integer > 200 || integer < 300)
        {  Toast.makeText(_myContext, "User successfully deleted", Toast.LENGTH_LONG).show();

        }



    }
}
