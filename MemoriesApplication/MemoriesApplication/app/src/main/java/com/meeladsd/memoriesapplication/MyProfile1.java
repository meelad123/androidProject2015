package com.meeladsd.memoriesapplication;

/**
 * Created by Sevag on 2015-11-09.
 */




import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AsyncPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.meeladsd.memoriesapplication.R.layout.activity_my_profileactivity2;

/**
 * Created by Sevag on 2015-11-05.
 */
public class MyProfile1 extends AsyncTask<String, String, JSONArray> {


    Activity con;
    TextView TextViewUsername, friendstxtView, vacationsView;
    ProgressDialog progressDialog;

    public MyProfile1(Activity _c, TextView _textView, TextView _friends, TextView _vacations) {
        con = _c;
        TextViewUsername = _textView;
        friendstxtView = _friends;
        vacationsView = _vacations;
        progressDialog = new ProgressDialog(con);

    }

    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Just a Sec");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }


    @Override
    protected JSONArray doInBackground(String... params) {


        try {
            JSONArray result = new JSONArray();
            SharedPreferences myS = con.getSharedPreferences("token", Context.MODE_PRIVATE);
            SharedPreferences myS2 = con.getSharedPreferences("Name", Context.MODE_PRIVATE);
            String t = myS.getString("access_token", "");
            String name = myS2.getString("Fname", "");
            DefaultHttpClient httpclient = new DefaultHttpClient();



            // HTTP Reques for user's Vacations
            HttpGet getVacarions = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name + "/vacations");
            getVacarions.addHeader("authorization", "bearer " + t);
            HttpResponse responseForVacaions = httpclient.execute(getVacarions);

            HttpEntity entityVacations = responseForVacaions.getEntity();
            JSONArray jsonObjectVac = JsonHelper.parsArray(entityVacations.getContent());
            result.put(jsonObjectVac);
            //users vacations request End

            //HTTP Request for User
            HttpGet Getrequest = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name);
            Getrequest.addHeader("authorization", "bearer " + t);
            HttpResponse response = httpclient.execute(Getrequest);

            HttpEntity entity = response.getEntity();
            JSONObject jsonObjectUser = JsonHelper.parseJSONObjectResponse(entity.getContent());
            saveTheUser(jsonObjectUser);
            result.put(jsonObjectUser);

            //User request end

            //HTTP request for users Friends

            HttpGet getFreinds = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name + "/friends");
            getFreinds.addHeader("authorization", "bearer " + t);
            HttpResponse responseForFriends = httpclient.execute(getFreinds);
            HttpEntity entyFriends = responseForFriends.getEntity();
            JSONArray jsonObjectFriends = JsonHelper.parsArray(entyFriends.getContent());

            result.put(jsonObjectFriends);
            // users Friends Request end

            return result;


        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private void saveTheUser(JSONObject object) throws JSONException {
        SharedPreferences userDetalis = con.getSharedPreferences("profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetalis.edit();
        editor.putString("Fname", object.getString("FirstName"));
        editor.putString("UserId",object.getString("UserId"));
        editor.putString("Lname", object.getString("LastName"));
        editor.putString("email", object.getString("Email"));
        editor.putString("username", object.getString("UserName"));
        editor.commit();

    }

    @Override
    protected void onPostExecute(JSONArray s) {


        try {
            String TotalVacations = String.valueOf(s.getJSONArray(0).length());
            String numOffriends = String.valueOf(s.getJSONArray(2).length());



            JSONObject Myobj = s.getJSONObject(1);
            String Fname = Myobj.getString("FirstName");
            String Lname = Myobj.getString("LastName");

            if (Fname.equals("null") || Lname.equals("null")) {
                TextViewUsername.setText("First name and last name not defind");
            } else {
                friendstxtView.setText(numOffriends);
                vacationsView.setText(TotalVacations);
                TextViewUsername.setText(Fname + " " + Lname);
            }
        } catch (JSONException e) {
            e.printStackTrace();


        } finally {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }


    }
}











