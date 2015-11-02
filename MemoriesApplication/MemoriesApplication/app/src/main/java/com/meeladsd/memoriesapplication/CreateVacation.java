package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sevag on 2015-10-26.
 */

public class CreateVacation extends AsyncTask<String, String, String> {


    private String Description, title, place, start, end;
    Context con;
    private int statuscode;
    private String Statues1;

    public CreateVacation(String _title, String _description, String _place, String _start, String _end, Context c) {
        Description = _description;
        title = _title;
        place = _place;
        start = _start;
        end = _end;
        con = c;


    }

    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://jthcloudproject.elasticbeanstalk.com/api/v1/Vacations");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("Title", title));
        nameValuePair.add(new BasicNameValuePair("Description", Description));
        nameValuePair.add(new BasicNameValuePair("Place", place));
        nameValuePair.add(new BasicNameValuePair("Start", start));
        nameValuePair.add(new BasicNameValuePair("End", end));


        try {
            SharedPreferences myS = con.getSharedPreferences("token", Context.MODE_PRIVATE);

            String t = myS.getString("access_token", "");
            httppost.addHeader("authorization","bearer "+ t);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair,HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        try {

            HttpResponse response = httpclient.execute(httppost);
            Statues1 = response.getEntity().toString();
            statuscode = response.getStatusLine().getStatusCode();


        } catch (ClientProtocolException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    protected void onPostExecute(String result) {

        if (statuscode > 200 || statuscode < 300)
            Toast.makeText(con, "Vacation created succefully", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(con, "Vacation not created", Toast.LENGTH_SHORT).show();
        }


    }
}