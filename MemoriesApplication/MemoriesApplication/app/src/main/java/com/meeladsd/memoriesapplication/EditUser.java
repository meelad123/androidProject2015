package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sevag on 2015-11-11.
 */
public class EditUser extends AsyncTask<String, String, Integer> {

   private TextView  FirstName,LastName,Email;
    private String[] myList;
   private HttpResponse response=null;
    int state;
    Activity _myContext;
    EditUser(TextView _FName,TextView _Lname,TextView _email, String [] myArray, Activity c)
    {LastName=_Lname;
        Email=_email;
        myList=myArray;
        FirstName=_FName;
        _myContext = c;
    }


    @Override
    protected Integer doInBackground(String... params) {


        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences myS2 = _myContext.getSharedPreferences("Name", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        String name = myS2.getString("Fname", "");

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name);
        httpPut.addHeader("authorization", "bearer " + t);
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("UserId", myList[0].toString()));
        nameValuePair.add(new BasicNameValuePair("FirstName", myList[1].toString()));
        nameValuePair.add(new BasicNameValuePair("LastName", myList[2].toString()));
        nameValuePair.add(new BasicNameValuePair("Email", myList[3].toString()));
        nameValuePair.add(new BasicNameValuePair("username", myList[4].toString()));

        try {

            httpPut.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));
            response = client.execute(httpPut);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(_myContext,e.getMessage(),Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }


             state=  response.getStatusLine().getStatusCode();

        return  state;
    }
    @Override
    protected void onPostExecute(Integer rep) {
        super.onPostExecute(rep);

        if (rep > 200 || rep <300){


            Toast.makeText(_myContext,"your profile has been successfully updated",Toast.LENGTH_LONG).show();
            _myContext.startActivity(new Intent(_myContext,MyProfileactivity2.class));

        }

    }
}




