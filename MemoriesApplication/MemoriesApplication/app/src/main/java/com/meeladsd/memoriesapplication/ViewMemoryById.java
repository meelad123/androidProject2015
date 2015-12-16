package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by alem1324 on 12/16/2015.
 */
public class ViewMemoryById extends AsyncTask<String, String, JSONObject> {

    private String _memId;
    private Activity _myContext;
    private ProgressDialog _progressDialog;
    private TextView _txtTitle, _txtDesc, _txtPlace, _txtTime, _txtMemCounter;
    private RelativeLayout _memText;
    private JSONArray _mediaListPic, _mediaListVid, _mediaListSound;

    public ViewMemoryById(String memId, Activity con) {
        _memId = memId;
        _myContext = con;
        _progressDialog = new ProgressDialog(con);
        _mediaListPic = new JSONArray();
        _mediaListVid = new JSONArray();
        _mediaListSound = new JSONArray();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        _progressDialog.setTitle("Fetching data");
        _progressDialog.setMessage("Please wait...");
        _progressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
            String t = myS.getString("access_token", "");

            DefaultHttpClient httpclient = new DefaultHttpClient();

            HttpGet getMem = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId);
            getMem.addHeader("authorization", "bearer " + t);
            HttpGet getMediaPic = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId + "/picture");
            getMediaPic.addHeader("Authorization", "bearer " + t);
            HttpGet getMediaVid = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId + "/video");
            getMediaVid.addHeader("Authorization", "bearer " + t);
            HttpGet getMediaSound = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/" + _memId + "/sound");
            getMediaSound.addHeader("Authorization", "bearer " + t);

            HttpResponse resp = httpclient.execute(getMem);
            HttpResponse respMedPic = httpclient.execute(getMediaPic);
            HttpResponse respMedVid = httpclient.execute(getMediaVid);
            HttpResponse respMedSound = httpclient.execute(getMediaSound);

            _mediaListPic = JsonHelper.parsArray(respMedPic.getEntity().getContent());
            _mediaListVid = JsonHelper.parsArray(respMedVid.getEntity().getContent());
            _mediaListSound = JsonHelper.parsArray(respMedSound.getEntity().getContent());


            return JsonHelper.parseJSONObjectResponse(resp.getEntity().getContent());
        } catch (ClientProtocolException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (_progressDialog.isShowing()) {
            _progressDialog.dismiss();
        }
        _txtTitle = (TextView) _myContext.findViewById(R.id.text_title_desc);
        _txtDesc = (TextView) _myContext.findViewById(R.id.text_desc2_desc);
        _txtPlace = (TextView) _myContext.findViewById(R.id.text_place_desc);
        _txtTime = (TextView) _myContext.findViewById(R.id.text_start_date_desc);
        _txtMemCounter = (TextView) _myContext.findViewById(R.id.text_mem_counter);
        _memText = (RelativeLayout) _myContext.findViewById(R.id.layout_memories);

        int counter = _mediaListPic.length() + _mediaListSound.length() + _mediaListVid.length();
        _txtMemCounter.setText(String.valueOf(counter));

        _memText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mem = new Intent(v.getContext(), ViewMediaActivity.class);
                mem.putExtra("MEM_ID", _memId);
                _myContext.startActivity(mem);
            }
        });

        try {
            if (jsonObject.getString("Title") != null) {
                _txtTitle.setText(jsonObject.getString("Title"));
            }
            if (jsonObject.getString("Description") != null) {
                _txtDesc.setText(jsonObject.getString("Description"));
            }
            if (jsonObject.getString("Place") != null) {
                _txtPlace.setText(jsonObject.getString("Place"));
            }
            if (jsonObject.getString("Time") != null) {
                _txtTime.setText(jsonObject.getString("Time"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
