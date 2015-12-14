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
 * Created by meeladsd on 11/24/2015.
 */
public class ViewVacation extends AsyncTask<String, String, JSONObject> {

    private int _vacId;
    private Activity _myContext;
    private ProgressDialog _progressDialog;
    private TextView _txtTitle,_txtDesc,_txtPlace,_txtStartDate,_txtEndDate, _txtMemCounter;
    private RelativeLayout _memText;
    private JSONArray _memList;

    public ViewVacation(int vacId, Activity con){
        _vacId = vacId;
        _myContext = con;
        _progressDialog = new ProgressDialog(con);
        _memList = new JSONArray();
    }

    protected void onPreExecute() {
        super.onPreExecute();
        _progressDialog.setTitle("Fetching data");
        _progressDialog.setMessage("Please wait...");
        _progressDialog.show();
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        try{
            SharedPreferences myS = _myContext.getSharedPreferences("token", Context.MODE_PRIVATE);
            String t = myS.getString("access_token", "");

            DefaultHttpClient httpclient = new DefaultHttpClient();

            HttpGet getVacarion = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations/" + _vacId);
            getVacarion.addHeader("authorization", "bearer " + t);

            HttpGet getMem = new HttpGet("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations/"+_vacId+"/memories");
            getMem.addHeader("Authorization", "bearer " + t);

            HttpResponse resp = httpclient.execute(getVacarion);
            HttpResponse respMem = httpclient.execute(getMem);
            int statuscode = respMem.getStatusLine().getStatusCode();
            _memList = JsonHelper.parsArray(respMem.getEntity().getContent());
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

        if (_myContext instanceof ViewVacationActivity) {
            _txtTitle = (TextView) _myContext.findViewById(R.id.text_title_desc);
            _txtDesc = (TextView) _myContext.findViewById(R.id.text_desc2_desc);
            _txtPlace = (TextView) _myContext.findViewById(R.id.text_place_desc);
            _txtStartDate = (TextView) _myContext.findViewById(R.id.text_start_date_desc);
            _txtEndDate = (TextView) _myContext.findViewById(R.id.text_end_date_desc);
            _txtMemCounter = (TextView) _myContext.findViewById(R.id.text_mem_counter);
            _memText = (RelativeLayout) _myContext.findViewById(R.id.layout_memories);

            _txtMemCounter.setText(String.valueOf(_memList.length()));

            _memText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mem = new Intent(v.getContext(), ViewMemoriesActivity.class);
                    mem.putExtra("VAC_ID", _vacId);
                    _myContext.startActivity(mem);


                }
            });

        }     else {

            _txtTitle = (TextView) _myContext.findViewById(R.id.edit_title_desc);
            _txtDesc = (TextView) _myContext.findViewById(R.id.edit_desc2_desc);
            _txtPlace = (TextView) _myContext.findViewById(R.id.edit_place_desc);
            _txtStartDate = (TextView) _myContext.findViewById(R.id.edit_start_date_desc);
            _txtEndDate = (TextView) _myContext.findViewById(R.id.edit_end_date_desc);



        }
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
                if (jsonObject.getString("Start") != null) {
                    _txtStartDate.setText(jsonObject.getString("Start"));
                }
                if (jsonObject.getString("End") != null) {
                    _txtEndDate.setText(jsonObject.getString("End"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }






}

