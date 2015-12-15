package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alem1324 on 12/14/2015.
 */


public class VideoUpload extends AsyncTask<String, Void, String> {
    private String _attachmentName = "bitmap";
    private String _attachmentFileName = "bitmap.MP4";
    private String _crlf = "\r\n";
    private String _twoHyphens = "--";
    private String _boundary =  "*****";
    private ProgressDialog progress;
    private Activity _con;
    private int _id;
    private byte[] _video;
    int _statusCode;
    public VideoUpload(int id, byte[] bArra, Activity con) {
        _video = bArra;
        _con = con;
        _id = id;
        progress = new ProgressDialog(con);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setTitle("Uploading the video");
        progress.setMessage("Please wait...");
        progress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        SharedPreferences myS =  _con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        HttpURLConnection httpUrlConnection = null;

        try {
            URL url = new URL("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/"+ _id+"/video");

            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Authorization", "Bearer "+t);
            httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + _boundary);

            DataOutputStream request = new DataOutputStream(
                    httpUrlConnection.getOutputStream());

            request.writeBytes(_twoHyphens + _boundary + _crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    _attachmentName + "\";filename=\"" +
                    _attachmentFileName + "\"" + _crlf);
            request.writeBytes(_crlf);


            request.write(_video);

            request.writeBytes(_crlf);
            request.writeBytes(_twoHyphens + _boundary +
                    _twoHyphens + _crlf);

            request.flush();
            request.close();

            _statusCode = httpUrlConnection.getResponseCode();
            InputStream inputStream = httpUrlConnection.getInputStream();
            inputStream.close();
            httpUrlConnection.disconnect();
        } catch (IOException e) {
            Log.e("tstImage", "Error", e);
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        if (progress.isShowing()) {
            progress.dismiss();
        }
        if (_statusCode > 200 || _statusCode < 300) {
            Toast.makeText(_con, "Uploaded...", Toast.LENGTH_LONG).show();
        }
    }

}
