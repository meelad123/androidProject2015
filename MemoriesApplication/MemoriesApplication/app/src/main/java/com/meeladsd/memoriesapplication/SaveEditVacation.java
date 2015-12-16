package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.UTF_8;

/**
 * Created by Sevag on 2015-12-09.
 */
public class SaveEditVacation extends AsyncTask<String, String, Integer> {
    private int _vacId;
    private String VacTitle,VacDes,VacPlace,VacStart,VacEnd;
    private String newRes[] ;
    private boolean VacFoumd = true;
    private String _txtTitle,_txtDesc,_txtPlace,_txtStartDate,_txtEndDate, _txtMemCounter;
    private String[] myList;
    private HttpResponse response=null;
    int state;
    Activity _myContext;
    boolean connected;
    VacationListHandler ListHandler = new VacationListHandler(_myContext, 20, null);

    SaveEditVacation(String Title,String Description,String place,String Start ,String End, int vacId, Activity c)
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

        if (CheckNetworkConnection() == false)
        {
            VacationEditInfo Store = new VacationEditInfo();
            Store.VacTitle = _txtTitle;
            Store.VacDes =_txtDesc;
            Store.VacPlace=_txtPlace;
            Store.VacStart = _txtStartDate;
            Store.VacEnd= _txtEndDate;
            Store.VacID= String.valueOf(_vacId);

            Converter conv = new Converter();
            JSONArray result = new JSONArray();
            try {
                result.put(0, conv.ClassToJSON(Store));
            }
            catch (Exception ex)
            {
                Log.e("SaveEditVac", ex.getMessage());
            }

            ListHandler.writeToFile(result, "StorredComs.txt");
        }


            VacTitle = _txtTitle;
            VacDes =_txtDesc;
            VacPlace=_txtPlace;
            VacStart = _txtStartDate;
            VacEnd= _txtEndDate;




    }

    @Override
    protected Integer doInBackground(String... params) {
        if (connected == true) {

            JSONObject result = new JSONObject();


            SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);

            String t = myS.getString("access_token", "");

            DefaultHttpClient client = new DefaultHttpClient();



                    HttpPut httpPut = new HttpPut("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations/"+_vacId);
                    httpPut.addHeader("authorization", "bearer " + t);
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
                    nameValuePair.add(new BasicNameValuePair("VacationId",Integer.toString(_vacId)));

                    nameValuePair.add(new BasicNameValuePair("Title", VacTitle));
                    nameValuePair.add(new BasicNameValuePair("Description", VacDes));
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
        else
        {

        }


        return null;
    }

    public Boolean CheckNetworkConnection() {
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)_myContext.getSystemService(_myContext.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
        }
        return  connected;

    }
    @Override
    protected void onPostExecute(Integer rep) {
        super.onPostExecute(rep);
        if (connected ==true) {

            if (rep >= 200 && rep < 300) {


                Toast.makeText(_myContext, "your profile has been successfully updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(_myContext, MainActivity.class);
                intent.putExtra("VacationID", _vacId);

                _myContext.startActivity(intent);
                _myContext.finish();

            }
            else {
                Toast.makeText(_myContext, "your profile did not  updated", Toast.LENGTH_LONG).show();


            }
        }

        JSONArray Check = ListHandler.ReadFromFile("StorredComs.txt");
        if (Check != null && Check.length()>0)
        {
            try{
                JSONObject LastCommand = Check.optJSONObject(0);

                VacTitle = LastCommand.getString("VacTitle");
                VacDes =LastCommand.getString("VacTitle");
                VacPlace=LastCommand.getString("VacTitle");
                VacStart = LastCommand.getString("VacTitle");
                VacEnd= LastCommand.getString("VacTitle");
                _vacId = Integer.parseInt(LastCommand.getString("VacID"));
                _myContext.deleteFile("StorredComs.txt");
                new SaveEditVacation(VacTitle,VacDes,VacPlace,VacStart,VacEnd,_vacId,_myContext).execute();
            }
            catch (Exception ex)
            {
                Log.e("SaveEditVac", ex.getMessage());

            }
        }

    }
}

class VacationEditInfo
{
    public String VacTitle,VacDes,VacPlace,VacStart,VacEnd,VacID;
}

