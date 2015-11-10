package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
 * Created by meeladsd on 11/9/2015.
 */
public class CreateMemory extends AsyncTask<String, Void, JSONObject> {

    private int _id;
    private ArrayList<Bitmap> _bArray;
    private String _time;
    private Context _con;
    private int _statuscode;

    CreateMemory(int id, ArrayList<Bitmap> bArra, String time, Context c) {

        _bArray = bArra;
        _id = id;
        _time = time;
        _con = c;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations/"+_id+"/memories");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("time", _time));




        try {
            SharedPreferences myS =  _con.getSharedPreferences("token", Context.MODE_PRIVATE);

            String t = myS.getString("access_token", "");
            httppost.addHeader("authorization", "bearer " + t);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httppost);
            _statuscode = response.getStatusLine().getStatusCode();

            return JsonHelper.parseJSONObjectResponse(response.getEntity().getContent());
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        int idM = -1;
        try {
            idM = jsonObject.getInt("MemoryId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new ImageUpload(idM,
                _bArray,
                _con).execute();
    }
}