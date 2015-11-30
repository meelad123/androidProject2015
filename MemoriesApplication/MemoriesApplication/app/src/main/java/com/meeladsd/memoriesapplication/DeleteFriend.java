package com.meeladsd.memoriesapplication;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
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
 * Created by Sevag on 2015-11-26.
 */
public class DeleteFriend extends AsyncTask<String,String,Integer>
{
    Context _myContext;
    String FriendsName;
    int state;

    Activity ac;

    ProgressDialog progress;

    DeleteFriend(Activity activity, String _name)
    {   FriendsName=_name;
        _myContext = activity;
        progress = new ProgressDialog(activity);

    }


    @Override
    protected Integer doInBackground(String... params) {
        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        String name = myS.getString("username", "");

        DefaultHttpClient client = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/"+name +"/friends/"+ FriendsName );
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
            Toast.makeText(_myContext, "User successfully deleted", Toast.LENGTH_LONG).show();
        }



}
}
