package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.client.methods.HttpPatch;
import cz.msebera.android.httpclient.entity.BasicHttpEntity;

import static org.apache.http.protocol.HTTP.UTF_8;

/**
 * Created by Sevag on 2015-12-09.
 */
public class SaveEditVacation extends AsyncTask<String, String, Integer> {
    private int _vacId;
    private String VacTitle,VacDes,VacPlace,VacStart,VacEnd;
    private String newRes[] ;
    private boolean VacFoumd = true;
    private TextView _txtTitle,_txtDesc,_txtPlace,_txtStartDate,_txtEndDate, _txtMemCounter;
    private String[] myList;
    private HttpResponse response=null;
    int state;
    Activity _myContext;

    SaveEditVacation(TextView Title,TextView Description,TextView place,TextView Start ,TextView End, int vacId, Activity c)
    {   _txtTitle =Title;
        _txtDesc = Description;
        _txtPlace = place;
        _txtStartDate=Start;
        _txtEndDate = End;
        _myContext = c;
       _vacId = vacId;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        VacTitle = _txtTitle.getText().toString();
        VacDes =_txtDesc.getText().toString();
        VacPlace=_txtPlace.getText().toString();
        VacStart = _txtStartDate.getText().toString();
        VacEnd= _txtEndDate.getText().toString();


    }

    @Override
    protected Integer doInBackground(String... params) {

        JSONObject result = new JSONObject();


        SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);

        String t = myS.getString("access_token", "");
        String name = myS.getString("username", "");

        DefaultHttpClient client = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/Users/" + name + "/vacations");
        httpGet.addHeader("authorization", "bearer " + t);
        try {
            HttpResponse resp = client.execute(httpGet);
            HttpEntity entity = resp.getEntity();
            JSONArray jsonObjectVacations = JsonHelper.parsArray(entity.getContent());
            String newRes[] = new String[jsonObjectVacations.length()];
            for (int i=0;i< jsonObjectVacations.length();i++) {
                result =(jsonObjectVacations.getJSONObject(i));
                newRes[i] = result.getString("VacationId");
                if(Integer.toString(_vacId) == newRes[i])
                {

                    VacFoumd=false;
                }
            }
            if(!VacFoumd) {
                HttpPut httpPut = new HttpPut("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations/"+_vacId);
                httpPut.addHeader("authorization", "bearer " + t);
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                nameValuePair.add(new BasicNameValuePair("Title",VacTitle));
                nameValuePair.add(new BasicNameValuePair("Description",VacDes));
                nameValuePair.add(new BasicNameValuePair("Place", VacPlace));
                nameValuePair.add(new BasicNameValuePair("Start", VacStart));
                nameValuePair.add(new BasicNameValuePair("End", VacEnd));

                try {

                    httpPut.setEntity(new UrlEncodedFormEntity(nameValuePair, UTF_8));
                    response = client.execute(httpPut);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(_myContext, e.getMessage(), Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }


               return state = response.getStatusLine().getStatusCode();


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
    @Override
    protected void onPostExecute(Integer rep) {
        super.onPostExecute(rep);

        if (rep > 200 || rep <300){


            Toast.makeText(_myContext,"your profile has been successfully updated",Toast.LENGTH_LONG).show();
            //_myContext.startActivity(new Intent(_myContext,MyProfileactivity2.class));

        }

    }
}

