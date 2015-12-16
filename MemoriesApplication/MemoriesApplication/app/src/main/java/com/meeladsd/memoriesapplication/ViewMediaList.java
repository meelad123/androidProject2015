package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alem1324 on 12/16/2015.
 */
public class ViewMediaList extends AsyncTask<String, String, JSONArray> {

    private Activity _activ;
    private Context _con;
    private JSONArray _medPic, _medVid, _medAud;
    private ListView _lstMemPic;
    private String _memId;
    private ArrayList<String> _pics, _vids, _aud;

    public ViewMediaList(String memId, ListView lstMed, Activity ac, Context c) {
        _memId = memId;
        _lstMemPic = lstMed;

        _activ = ac;
        _con = c;
        _medPic = new JSONArray();
        _medVid = new JSONArray();
        _medAud = new JSONArray();

        _pics = new ArrayList<String>();
        _vids = new ArrayList<String>();
        _aud = new ArrayList<String>();

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONArray doInBackground(String... params) {
        try {
            SharedPreferences myS = _con.getSharedPreferences("token", Context.MODE_PRIVATE);
            String t = myS.getString("access_token", "");

            DefaultHttpClient httpclient = new DefaultHttpClient();

            HttpGet getMem = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId);
            getMem.addHeader("authorization", "bearer " + t);
            HttpGet getMediaPic = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId + "/picture");
            getMediaPic.addHeader("Authorization", "bearer " + t);
            HttpGet getMediaVid = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId + "/video");
            getMediaVid.addHeader("Authorization", "bearer " + t);
            HttpGet getMediaSound = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId + "/sound");
            getMediaSound.addHeader("Authorization", "bearer " + t);

            HttpResponse resp = httpclient.execute(getMem);
            HttpResponse respMedPic = httpclient.execute(getMediaPic);
            HttpResponse respMedVid = httpclient.execute(getMediaVid);
            HttpResponse respMedSound = httpclient.execute(getMediaSound);

            _medPic = JsonHelper.parsArray(respMedPic.getEntity().getContent());
            _medVid = JsonHelper.parsArray(respMedVid.getEntity().getContent());
            _medAud = JsonHelper.parsArray(respMedSound.getEntity().getContent());

        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {


        for(int i = 0; i < _medPic.length(); i++){
            try {
                JSONObject o = _medPic.getJSONObject(i);
                if(o.getString("Url") != ""){
                    _pics.add(o.getString("Url"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < _medVid.length(); i++){
            try {
                JSONObject o = _medVid.getJSONObject(i);
                if(o.getString("Url") != ""){
                    _vids.add(o.getString("Url"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < _medAud.length(); i++){
            try {
                JSONObject o = _medAud.getJSONObject(i);
                if(o.getString("Url") != ""){
                    _aud.add(o.getString("Url"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ViewMediaAdapter mAd = new ViewMediaAdapter(_con, _pics, _vids, _aud);

        _lstMemPic.setAdapter(mAd);
    }
}
