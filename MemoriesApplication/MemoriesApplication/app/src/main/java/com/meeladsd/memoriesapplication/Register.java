package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sevag on 2015-10-21.
 * Edited by Meelad on 2015-10-22.
 */
public class Register extends AsyncTask<String, String, JSONObject> {

    private String userName, password, email;
    private String Statues1;
    Context con;
    private JSONObject statuscode;


    public Register(String _userName, String _Password, String _Email, Context c) {
        userName = _userName;
        password = _Password;
        email = _Email;
        con = c;

    }


    protected JSONObject doInBackground(String... params) {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://jthcloudproject.elasticbeanstalk.com/api/v1/account");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("Username", userName));
        nameValuePair.add(new BasicNameValuePair("password", password));
        nameValuePair.add(new BasicNameValuePair("email", email));


        try {

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

        try {

            HttpResponse response = httpclient.execute(httppost);
            statuscode = JsonHelper.parseJSONObjectResponse(response.getEntity().getContent());


        } catch (ClientProtocolException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return statuscode;

    }


    protected void onPostExecute(JSONObject result) {


            Toast.makeText(con, result.toString(), Toast.LENGTH_SHORT).show();


    }
}