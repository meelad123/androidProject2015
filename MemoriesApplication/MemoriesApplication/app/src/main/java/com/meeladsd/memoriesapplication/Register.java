package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Sevag on 2015-10-21.
 * Edited by Meelad on 2015-10-22.
 */
public class Register extends AsyncTask<String, String, JSONObject> {

    private String userName, password, email;
    private ProgressDialog progress;
    private int _statuscode;
    Context con;
    Activity _rAct;



    public Register(String _userName, String _Password, String _Email, Context c, Activity rAct) {
        userName = _userName;
        password = _Password;
        email = _Email;
        con = c;
        _rAct = rAct;
        progress = new ProgressDialog(con);


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
            _statuscode = response.getStatusLine().getStatusCode();
            return JsonHelper.parseJSONObjectResponse(response.getEntity().getContent());

        } catch (ClientProtocolException e) {
            e.printStackTrace();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null ;
    }


    protected void onPostExecute(JSONObject result) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        if(_statuscode >= 200 && _statuscode <300) {
            Toast.makeText(con, "User created successfullysuccessfully", Toast.LENGTH_LONG).show();
            Intent reg = new Intent(con.getApplicationContext(), LogInActivity.class);
            _rAct.startActivity(reg);

        }
        else {
            try {
                Toast.makeText(con, result.getJSONObject("ModelState").getJSONArray("").getString(0), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}