package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

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
 * Created by Sevag on 2015-11-24.
 */
public class Friends extends AsyncTask<String, String, JSONArray> {

    Context con;
    ListView myList;
    Activity ac;
    private ProgressDialog progress;
    SearchView searchView;
    boolean connected;


    public Friends(Activity _con, ListView _List, SearchView _sr, Activity _ac) {
        myList = _List;
        con = _con;
        ac = _ac;
        searchView = _sr;
        progress = new ProgressDialog(con);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setTitle("Loading your Friend List");
        progress.setMessage("Please wait...");
        progress.show();
    }


    @Override
    protected JSONArray doInBackground(String... params) {
        if (CheckNetworkConnection() == true) {
            JSONArray result = new JSONArray();
            DefaultHttpClient httpclient = new DefaultHttpClient();
            SharedPreferences myS = con.getSharedPreferences("token", Context.MODE_PRIVATE);
            String t = myS.getString("access_token", "");
            String name = myS.getString("username", "");

            try {


                HttpGet getFriends = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name + "/friends");
                getFriends.addHeader("authorization", "bearer " + t);
                HttpResponse response = httpclient.execute(getFriends);
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
        } else {
            return null;

        }

    }

    public Boolean CheckNetworkConnection() {
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) ac.getSystemService(ac.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;

    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        if (progress.isShowing()) {
            progress.dismiss();
        }

        if (connected == false) {
            try {
                VacationListHandler ListHandler = new VacationListHandler(ac, 20, null);
                jsonArray = ListHandler.ReadFromFile("FriendList.txt");
            } catch (Exception ex) {
                Log.e("Friends", ex.getMessage());
            }
        }
        if (jsonArray != null && jsonArray.length() > 0) {
            super.onPostExecute(jsonArray);
            String myFriends[] = new String[jsonArray.length()];


            try {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject Myobj = jsonArray.getJSONObject(i);
                    myFriends[i] = Myobj.getString("UserName");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<String> stringList = new ArrayList<>(Arrays.asList(myFriends));
            final mylistAdob adapter = new mylistAdob(stringList, con, ac);

            myList.setAdapter(adapter);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        } else {

        }

    }
}

