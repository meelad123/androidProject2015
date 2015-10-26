package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sevag on 2015-10-26.
 */

public class CreateVacation extends AsyncTask<String, String, String> {

    private String Description, title,place,start,end;
    Context con;



    public CreateVacation(String _title, String _description, String _place, String _start, String _end, Context c) {
        Description=_description;
        title=_title;
        place=_place;
        start=_start;
        end=_end;
        con = c;



    }

    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://jthcloudproject.elasticbeanstalk.com/api/v1/users/vacations");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("title",title ));
        nameValuePair.add(new BasicNameValuePair("description", Description));
        nameValuePair.add(new BasicNameValuePair("place", place));
        nameValuePair.add(new BasicNameValuePair("start", start));
        nameValuePair.add(new BasicNameValuePair("end", end));



        try {

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        try {

            HttpResponse response = httpclient.execute(httppost);



        } catch (ClientProtocolException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }




        return null;
    }
}