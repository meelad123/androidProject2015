package com.meeladsd.memoriesapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sevag on 2015-12-04.
 */
public class addFriend extends AsyncTask<String, String, JSONObject> {
    Context con;
    private ProgressDialog progress;
    private  String FriendsName;
    private int _statuscode;



    public addFriend(Context _con,String _Friendsname) {
        FriendsName = _Friendsname;
        con = _con;

        progress = new ProgressDialog(con);

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setTitle("Adding a friend");
        progress.setMessage("Please wait...");
        progress.show();

    }



    @Override
    protected JSONObject doInBackground(String... params) {
        JSONArray result = new JSONArray();
        DefaultHttpClient httpclient = new DefaultHttpClient();
        SharedPreferences myS = con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        String name = myS.getString("username", "");


        try {
            HttpPost addFriend = new HttpPost("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name + "/friends");
            addFriend.addHeader("authorization", "bearer " + t);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("Username", FriendsName));
            addFriend.setEntity(new UrlEncodedFormEntity(nameValuePair));
            HttpResponse response = httpclient.execute(addFriend);
            HttpEntity entity  = response.getEntity();
            _statuscode = response.getStatusLine().getStatusCode();

            return  JsonHelper.parseJSONObjectResponse(response.getEntity().getContent());



        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonArray) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        if(_statuscode >= 200 && _statuscode <300) {
            Toast.makeText(con, "Friend added", Toast.LENGTH_LONG).show();

          Intent intent = new Intent(con.getApplicationContext(),FriendsActivity.class);
            con.startActivity(intent);
        }
        else {
            Toast.makeText(con, "NO user found with the followed name", Toast.LENGTH_LONG).show();

        }


        }



    }

