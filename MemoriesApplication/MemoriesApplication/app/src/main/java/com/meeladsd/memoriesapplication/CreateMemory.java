package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

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
    private Activity _con;
    private ProgressDialog progress;
    private int _statuscode;
    private byte[] _vidByte;
    private byte[] _soundByte;

    CreateMemory(int id, byte[] s, byte[] v, ArrayList<Bitmap> bArra, String time, Activity c) {

        _bArray = bArra;
        _id = id;
        _time = time;
        _con = c;
        progress = new ProgressDialog(c);
        _vidByte = v;
        _soundByte = s;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setTitle("Creating a new vacation");
        progress.setMessage("Please wait...");
        progress.show();
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
        if (progress.isShowing()) {
            progress.dismiss();
        }
        if(_statuscode >= 200 && _statuscode <300) {
            int idM = -1;
            try {
                idM = jsonObject.getInt("MemoryId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(_bArray != null)
            {
                new ImageUpload(idM, _bArray, _con).execute();
            }
            if(_soundByte != null){
                new AudioUpload(idM, _soundByte, _con).execute();

            }
            if(_vidByte != null){
                new VideoUpload(idM, _vidByte, _con).execute();

            }
        }
        else {
            Toast.makeText(_con, "Failed to create a new memory", Toast.LENGTH_SHORT).show();
        }

    }
}