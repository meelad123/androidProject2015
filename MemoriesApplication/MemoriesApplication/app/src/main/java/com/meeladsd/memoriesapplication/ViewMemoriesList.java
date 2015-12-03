package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by meelad on 12/1/2015.
 */
public class ViewMemoriesList extends AsyncTask<String, String, JSONArray> {

    private Activity _activ;
    private Context _con;
    private ArrayList<String> _memTitles;
    private ArrayList<String> _memDesc;
    private ArrayList<String> _memIds;
    private ListView _lstMem;
    private int _vacId;
    public ViewMemoriesList(int vacId, ListView lstMem, Activity con, Context appCon){
        _con = appCon;
        _activ = con;
        _memTitles = new ArrayList<String>();
        _memDesc = new ArrayList<String>();
        _memIds = new ArrayList<String>();
        _vacId = vacId;
        _lstMem = lstMem;

    }
    @Override
    protected JSONArray doInBackground(String... params) {

        JSONArray result = new JSONArray();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        SharedPreferences myS = _con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");

        try {
            HttpGet fetchMemories = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations/"+_vacId+"/memories");
            fetchMemories.addHeader("Authorization", "Bearer " + t);
            HttpResponse response = httpclient.execute(fetchMemories);

            HttpEntity entity  = response.getEntity();
            JSONArray jsonObjectFriends = JsonHelper.parsArray(entity.getContent());

            for (int i=0;i< jsonObjectFriends.length();i++) {
                result.put(jsonObjectFriends.get(i));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        try {
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                _memTitles.add(i, o.getString("Title"));
                _memDesc.add(i, o.getString("Description"));
                _memIds.add(i, o.getString("MemoryId"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MemoriesAdapter mAd = new MemoriesAdapter(_con, _memTitles, _memDesc, _memIds);

        _lstMem.setAdapter(mAd);
    }
}
