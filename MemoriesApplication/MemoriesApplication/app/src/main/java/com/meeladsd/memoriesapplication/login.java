package com.meeladsd.memoriesapplication;

import android.os.AsyncTask;

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
public class login extends AsyncTask<Void, Void, Boolean>{

    private String _userName, _password, _url;
    private HttpResponse resp = null;
    
    login(String userName, String password, String url)
    {
        _userName = userName;
        _password = password;
        _url = url;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

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

            JSONObject userJson = JsonHelper.parseJSONObjectResponse(resp.getEntity().getContent());

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
