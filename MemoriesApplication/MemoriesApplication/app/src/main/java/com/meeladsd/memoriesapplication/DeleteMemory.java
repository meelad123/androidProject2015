package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by alem1324 on 12/16/2015.
 */
public class DeleteMemory extends AsyncTask<String, String, Integer> {
    private String VacId;
    int state;
    Activity con;
    public DeleteMemory(String memId, Activity ac) {
        VacId=memId;
        con=ac;
    }

    @Override
    protected Integer doInBackground(String... params) {
        SharedPreferences myS = con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        String name = myS.getString("username", "");

        DefaultHttpClient client = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/"+VacId);
        httpDelete.addHeader("authorization", "bearer " + t);

        try {

            HttpResponse response = client.execute(httpDelete);
            state = response.getStatusLine().getStatusCode();
        } catch (IOException e) {

            Toast.makeText(con, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return state;
    }
}
