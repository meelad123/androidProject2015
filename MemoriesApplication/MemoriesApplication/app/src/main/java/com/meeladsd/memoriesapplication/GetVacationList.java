package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Hamster on 7/12/2015.
 */
public class GetVacationList extends AsyncTask<String, Void, Void> {
    private String  JsonUnconverted;
    private Activity _myContext;
    private HttpResponse resp = null;
    private JSONArray Vacations = new JSONArray();
    private JSONArray Friends = new JSONArray();
    private JSONArray PersonalVacations = new JSONArray();
    private JSONArray FinalList = new JSONArray();
    private VacationListHandler ListHandler;
    private boolean connected = false;


    GetVacationList(Activity c)
    {
        _myContext = c;
        ListHandler = new VacationListHandler(_myContext, 20);
    }

    @Override
    protected Void doInBackground(String... params)
    {
        CheckNetworkConnection();
        if (connected == true)
        {
            GetFriends();
            SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
            String token = myS.getString("access_token", "");
            String Username = myS.getString("username", "");
            GetVacations(Username,token);
            if (Vacations.length() > 0 && Vacations != null) {
                PersonalVacations = Vacations;
                for (int i = 0; i < Vacations.length(); i++) {
                    try {
                        JSONObject trans = Vacations.getJSONObject(i);
                        FinalList.put(trans);
                    }
                    catch (Exception ex)
                    {
                        Log.e("Luke", ex.getMessage());
                    }
                }
            }
            if (Friends.length() > 0 && Friends != null) {
                for (int i = 0; i < Friends.length(); i++)
                {
                    Log.e("Luke", "Friends length: " + Friends.length());
                    String FriendName = null;
                    try
                    {
                        JSONObject Romp = Friends.getJSONObject(i);
                        FriendName = Romp.getString("UserName");
                    }
                    catch (Exception ex)
                    {
                        Log.e("Luke", "meh");
                        Log.e("Luke", ex.getMessage());
                    }
                    if (FriendName != null) {
                        GetVacations(FriendName, token);
                        try {
                            if(Vacations.length() > 0 && Vacations != null) {
                                for (int a = 0; a < Vacations.length(); a++) {
                                    JSONObject trans = Vacations.getJSONObject(a);
                                    FinalList.put(trans);
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            Log.e("Luke", "meh2");
                            Log.e("Luke", ex.getMessage());
                        }
                    }
                }
            }
        }
        else
        {
            ListHandler.checkForFile();
        }

        return null;
    }

    private JSONArray GetVacations(String Username, String Token)
    {
        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);

        Log.d("Luke", "still going");
        String eh = _myContext.getString(R.string.url_get_vacations) + Username + "/vacations";
        HttpGet httpGet = new HttpGet(eh);
        HttpClient clive = new DefaultHttpClient();

        try
        {
            httpGet.addHeader("Accept", "application/json");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("Authorization", "Bearer " + Token);
            resp = clive.execute(httpGet);

            JsonUnconverted = LukesJSON.ReadInputStream(resp.getEntity().getContent());
            Log.d("Luke", "Retrieved from net");
            Log.d("Luke", JsonUnconverted);
            Vacations = LukesJSON.ParseStringToJsonArray(JsonUnconverted);
            return Vacations;


        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void GetFriends()
    {
        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        String Username = myS.getString("username", "");
        String eh = _myContext.getString(R.string.url_get_vacations) + Username + "/friends";
        HttpGet httpGet = new HttpGet(eh);
        HttpClient clive = new DefaultHttpClient();
        String token = null;

        try
        {
            Log.d("Luke", "accessing token");

            token = myS.getString("access_token", "");
            Log.d("Luke",token);
        }
        catch (Exception e)
        {
            Log.e("Luke",e.getMessage());
        }

        try
        {
            httpGet.addHeader("Accept", "application/json");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("Authorization", "Bearer " + token);
            resp = clive.execute(httpGet);

            JsonUnconverted = LukesJSON.ReadInputStream(resp.getEntity().getContent());
            Log.d("Luke", "Retrieved from net");
            Log.d("Luke", JsonUnconverted);
            Friends = LukesJSON.ParseStringToJsonArray(JsonUnconverted);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void CheckNetworkConnection() {
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)_myContext.getSystemService(_myContext.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
    }



    @Override
    protected void onPostExecute(Void v)
    {
        if (FinalList != null) {
            ListHandler.PopulateList(FinalList);
            ListHandler.Writetofile(PersonalVacations);
        }
        else
        {

        }

    }
}
