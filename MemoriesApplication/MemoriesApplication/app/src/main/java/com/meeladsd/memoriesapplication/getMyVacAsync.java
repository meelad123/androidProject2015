package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;

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
 * Created by Sevag on 2015-12-15.
 */
public class getMyVacAsync extends AsyncTask<String, String, JSONArray> {
    Context _myContext;
    int _vacId;
    ListView _List;
    private JSONArray Vacations = new JSONArray();

    public getMyVacAsync(ListView _ls, Context applicationContext, int Id) {
        _List = _ls;
        _vacId = Id;
        _myContext = applicationContext;

    }

    @Override
    protected JSONArray doInBackground(String... params) {

        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        String name = myS.getString("username", "");
        String t = myS.getString("access_token", "");

        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpGet getVacarion = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/users/" + name + "/vacations");
        getVacarion.addHeader("authorization", "bearer " + t);

        try {
            HttpResponse resp = httpclient.execute(getVacarion);
            HttpEntity entity = resp.getEntity();
            Vacations = JsonHelper.parsArray(entity.getContent());


            return Vacations;


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        ArrayList<String> myVacs = new ArrayList<String>();
        ArrayList<String> myVacsIds = new ArrayList<String>();


        try {

            for (int i = 0; i< jsonArray.length();i++) {
                JSONObject o = jsonArray.getJSONObject(i);
                myVacs.add(i, o.getString("Title"));
                myVacsIds.add(i, o.getString("VacationId"));
            }


            extraAdobpter adobpter = new extraAdobpter(_myContext,myVacs,myVacsIds);

            _List.setAdapter(adobpter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}