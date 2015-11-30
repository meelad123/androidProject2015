package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import java.util.List;


/**
 * Created by Sevag on 2015-11-24.
 */
public class Friends extends AsyncTask<String, String, JSONArray> {

    Context con;
    ListView myList;
    Activity ac;


    public Friends(Activity _con,ListView _List,Activity _ac) {
        myList=_List;
        con = _con;
        ac=_ac;

    }


    @Override
    protected JSONArray doInBackground(String... params) {
        JSONArray result = new JSONArray();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        SharedPreferences myS = con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        String name = myS.getString("username", "");

        try {


            HttpGet getFriends = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name + "/friends");
            getFriends.addHeader("authorization", "bearer " + t);
            HttpResponse response = httpclient.execute(getFriends);
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
        super.onPostExecute(jsonArray);
        String myFriends[] = new String[jsonArray.length()];


        try {

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject Myobj = jsonArray.getJSONObject(i);
              myFriends[i] = Myobj.getString("UserName");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> stringList = new ArrayList<>(Arrays.asList(myFriends));
        mylistAdob adapter = new mylistAdob(stringList, con,ac);

        myList.setAdapter(adapter);

    }

}
