package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meeladsd on 10/18/2015.
 */
public class login extends AsyncTask<String, Void, JSONObject>{

    private String _userName, _password, _url;
    Activity _myContext;
    private HttpResponse resp = null;
    ProgressDialog progress;
    login(String userName, String password, String url, Activity c)
    {
        _userName = userName;
        _password = password;
        _url = url;
        _myContext = c;
        progress = new ProgressDialog(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setTitle("Logging in");
        progress.setMessage("Please wait...");
        progress.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        HttpPost httppost = new HttpPost(_url);
        HttpClient client = new DefaultHttpClient();
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();


        valuePairs.add(new BasicNameValuePair("grant_type", "password"));
        valuePairs.add(new BasicNameValuePair("username", _userName));
        valuePairs.add(new BasicNameValuePair("password", _password));
        SharedPreferences uName = _myContext.getSharedPreferences("Name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = uName.edit();

        editor.putString("Fname", _userName);
        editor.commit();

        try {
            httppost.addHeader("Content-Type", "application/json");
            httppost.setEntity(new  UrlEncodedFormEntity(valuePairs, HTTP.UTF_8));
            resp = client.execute(httppost);

            return JsonHelper.parseJSONObjectResponse(resp.getEntity().getContent());
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        try {
            if(jsonObject.getString("access_token") != null){
                SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myS.edit();
                try {

                    String s = jsonObject.getString("access_token");
                    editor.putString("access_token", s);
                    editor.putString("username", _userName);
                    editor.commit();
                    Toast.makeText(_myContext, "logged in", Toast.LENGTH_LONG).show();

                    _myContext.startActivity(new Intent(_myContext, MainActivity.class));

                    _myContext.finish();
                } catch (JSONException e) {
                    Toast.makeText(_myContext, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(_myContext, e.getMessage(), Toast.LENGTH_LONG).show();

        }

    }
}
