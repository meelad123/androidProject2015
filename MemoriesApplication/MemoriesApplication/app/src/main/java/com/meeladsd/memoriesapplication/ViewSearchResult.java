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
import java.util.Arrays;

/**
 * Created by alem1324 on 12/16/2015.
 */
public class ViewSearchResult extends AsyncTask<String, String, JSONArray> {

    private String _q;
    private ListView _lstView;
    private Activity _ac;
    private Context _con;
    public ViewSearchResult(String q, ListView lst, Activity ac, Context c) {
        _q = q;
        _lstView = lst;
        _ac = ac;
        _con = c;
    }

    @Override
    protected JSONArray doInBackground(String... params) {

        JSONArray result = new JSONArray();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        SharedPreferences myS = _con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");

        try {
            HttpGet getMem = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/search?type=title&query=" + _q);
            getMem.addHeader("authorization", "bearer " + t);
            HttpResponse response = httpclient.execute(getMem);
            HttpEntity entity = response.getEntity();
            JSONArray jsonObjectFriends = JsonHelper.parsArray(entity.getContent());
            for (int i = 0; i < jsonObjectFriends.length(); i++) {
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
        ArrayList<String> stringList = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject Myobj = jsonArray.getJSONObject(i);
                stringList.add(i, Myobj.getString("Title"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mylistAdob adapter = new mylistAdob(stringList, _con, _ac);
        _lstView.setAdapter(adapter);
    }
}
