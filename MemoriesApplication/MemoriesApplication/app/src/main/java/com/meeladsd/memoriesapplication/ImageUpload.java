package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by meeladsd on 11/9/2015.
 */
public class ImageUpload extends AsyncTask<String, Void, String> {
    private int _id;
    private ArrayList<Bitmap> _bArray;
    Context _con;
    private int _statuscode;

    DataOutputStream dos;
    ImageUpload(int id, ArrayList<Bitmap> bArra, Context con) {

        _bArray = bArra;
        _id = id;
        _con = con;

    }

    @Override
    protected String doInBackground(String... params) {
        try {

            Bitmap bitmap = _bArray.get(0);

            SharedPreferences myS =  _con.getSharedPreferences("token", Context.MODE_PRIVATE);
            String t = myS.getString("access_token", "");

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            URL url = new URL("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/"+ _id+"/picture?height="+height+"&width="+width);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();

            c.setDoInput(true);
            c.setDoOutput(true);

            c.setRequestMethod("POST");
            c.setRequestProperty("Authorization", "bearer " + t);
            c.setRequestProperty("Content-type", "multipart/form-data");
            
            dos = new DataOutputStream(c.getOutputStream());

            c.connect();

            OutputStream output = c.getOutputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();

            output.write(imageBytes);
            output.close();

            int responseCode = c.getResponseCode();
            Scanner result = new Scanner(c.getInputStream());
            String response = result.nextLine();
            result.close();

            return response;
        } catch (IOException e) {
            Log.e("tstImage", "Error", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        Log.e("response",s);
    }
}


//URL url = new URL("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/"+ _id+"/picture?height="+height+"&width="+width);