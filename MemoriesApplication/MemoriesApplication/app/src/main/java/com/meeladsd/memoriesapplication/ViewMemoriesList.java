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
    private ArrayList<String> _memTitles, _memDesc, _memIds, _memUrl;
    private ListView _lstMem;
    private int _vacId;
    public ViewMemoriesList(int vacId, ListView lstMem, Activity con, Context appCon){
        _con = appCon;
        _activ = con;
        _memTitles = new ArrayList<String>();
        _memDesc = new ArrayList<String>();
        _memIds = new ArrayList<String>();
        _memUrl =  new ArrayList<String>();
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
            JSONArray jsonObjectMem = JsonHelper.parsArray(entity.getContent());

            for (int i=0;i< jsonObjectMem.length();i++) {
                result.put(jsonObjectMem.get(i));
            }

            for(int i=0;i<result.length();i++) {
                JSONObject o = result.getJSONObject(i);
                _memTitles.add(i, o.getString("Title"));
                _memDesc.add(i, o.getString("Description"));
                _memIds.add(i, o.getString("MemoryId"));

                HttpGet fetchMediaUrl = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/"+ o.getString("MemoryId") +"/picture");
                fetchMediaUrl.addHeader("Authorization", "Bearer " + t);
                HttpResponse respMemUrl = httpclient.execute(fetchMediaUrl);

                JSONArray objUrl = JsonHelper.parsArray(respMemUrl.getEntity().getContent());
                if(objUrl.length() != 0){
                    _memUrl.add(i, objUrl.getJSONObject(0).getString("Url"));
                }
                else{
                    _memUrl.add(i,"");
                }
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
        MemoriesAdapter mAd = new MemoriesAdapter(_con, _memTitles, _memUrl, _memIds);

        _lstMem.setAdapter(mAd);
    }
}
