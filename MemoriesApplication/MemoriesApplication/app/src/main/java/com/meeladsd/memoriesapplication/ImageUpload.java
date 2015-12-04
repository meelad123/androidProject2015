package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import java.util.List;

/**
 * Created by meeladsd on 11/9/2015.
 */
public class ImageUpload extends AsyncTask<String, Void, String> {

    private List<Integer> _statusCode = new ArrayList<Integer>();
    private int _id;
    private ArrayList<Bitmap> _bArray;
    private Activity _con;

    private String _attachmentName = "bitmap";
    private String _attachmentFileName = "bitmap.JPEG";
    private String _crlf = "\r\n";
    private String _twoHyphens = "--";
    private String _boundary =  "*****";

    private ProgressDialog progress;

    ImageUpload(int id, ArrayList<Bitmap> bArra, Activity con) {

        _bArray = bArra;
        _id = id;
        _con = con;
        progress = new ProgressDialog(con);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setTitle("Uploading the images");
        progress.setMessage("Please wait...");
        progress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        //get sharePrefrences & the access token
        SharedPreferences myS =  _con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        HttpURLConnection httpUrlConnection = null;
        //url for upload

        try {
            _bArray.remove(0);
            for (Bitmap bitmap: _bArray) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                URL url = new URL("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/"+ _id+"/picture?height="+height+"&width="+width);

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

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] imageBytes = baos.toByteArray();

                request.write(imageBytes);

                request.writeBytes(_crlf);
                request.writeBytes(_twoHyphens + _boundary +
                        _twoHyphens + _crlf);

                request.flush();
                request.close();

                _statusCode.add(httpUrlConnection.getResponseCode());
                InputStream inputStream = httpUrlConnection.getInputStream();
                inputStream.close();
                httpUrlConnection.disconnect();
            }
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
        boolean ok = true;
        for (int status:_statusCode) {

            if (status >= 300 && status < 200){
                ok = false;
            }
        }
        if(!ok)
        {
            Toast.makeText(_con, "Failed to upload one or more images", Toast.LENGTH_SHORT).show();
        }
        else
        {
            _con.startActivity(new Intent(_con, MainActivity.class));
        }
    }
}
