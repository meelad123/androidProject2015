package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sevag on 2015-10-26.
 */

public class CreateVacation extends AsyncTask<String, String, JSONObject> {


    private String Description, title,place,start,end;
    private int statuscode;
    private String Statues1;
    private ArrayList<Bitmap> _bitmapArray;

    Context con;



    public CreateVacation(String _title, String _description, String _place, String _start, String _end, Context c, ArrayList<Bitmap> bArray) {
        Description=_description;
        title=_title;
        place=_place;
        start=_start;
        end=_end;
        con = c;
        _bitmapArray = bArray;

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("title",title ));
        nameValuePair.add(new BasicNameValuePair("description", Description));
        nameValuePair.add(new BasicNameValuePair("place", place));
        nameValuePair.add(new BasicNameValuePair("start", start));
        nameValuePair.add(new BasicNameValuePair("end", end));



        try {
            SharedPreferences myS =  con.getSharedPreferences("token", Context.MODE_PRIVATE);

            String t = myS.getString("access_token", "");
            httppost.addHeader("authorization", "bearer " + t);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));

            HttpResponse response = httpclient.execute(httppost);

            Statues1 = response.getEntity().toString();
            statuscode = response.getStatusLine().getStatusCode();
            return JsonHelper.parseJSONObjectResponse(response.getEntity().getContent());

        }catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    protected void onPostExecute(JSONObject result) {

        if (statuscode > 200 || statuscode < 300)
            try {
                newMemory(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        else {
            Toast.makeText(con, "Vacation not created", Toast.LENGTH_SHORT).show();
        }

    }

    private void newMemory(JSONObject result) throws JSONException {
        int id = result.getInt("VacationId");
        new CreateMemory(id,
                _bitmapArray,
                start,
                con).execute();

    }


}
