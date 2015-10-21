package com.meeladsd.memoriesapplication;

import android.content.Context;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meeladsd on 10/18/2015.
 */
public class login extends AsyncTask<String, Void, JSONObject>{

    private String _userName, _password, _url;
    Context _myContext;
    private HttpResponse resp = null;
    
    login(String userName, String password, String url, Context c)
    {
        _userName = userName;
        _password = password;
        _url = url;
        _myContext = c;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        HttpPost httppost = new HttpPost(_url);
        HttpClient client = new DefaultHttpClient();
        List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();

        valuePairs.add(new BasicNameValuePair("grant_type", "password"));
        valuePairs.add(new BasicNameValuePair("username", _userName));
        valuePairs.add(new BasicNameValuePair("password", _password));

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

        if(jsonObject != null)
            Toast.makeText(_myContext, "logged in", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(_myContext, "not in", Toast.LENGTH_LONG).show();
    }
}
