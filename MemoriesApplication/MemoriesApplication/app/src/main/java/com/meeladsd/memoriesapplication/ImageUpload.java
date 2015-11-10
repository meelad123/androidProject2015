package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meeladsd on 11/9/2015.
 */
public class ImageUpload extends AsyncTask<String, Void, JSONObject> {

    private int _id;
    private ArrayList<Bitmap> _bArray;
    Context _con;
    private int _statuscode;

    ImageUpload(int id, ArrayList<Bitmap> bArra, Context con) {

        _bArray = bArra;
        _id = id;
        _con = con;

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        Bitmap bitmap = _bArray.get(1);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();



        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        InputStream is = new ByteArrayInputStream(stream.toByteArray());

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        //nameValuePair.add(new BasicNameValuePair( "picture", is));
        HttpPost httppost = new HttpPost("http://jthcloudproject.elasticbeanstalk.com/api/v1/memories/"+ _id+"/picture?height="+height+"&width="+width);

        try {
            SharedPreferences myS =  _con.getSharedPreferences("token", Context.MODE_PRIVATE);

            String t = myS.getString("access_token", "");
            httppost.addHeader("authorization", "bearer " + t);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8));

            HttpResponse response = httpclient.execute(httppost);
            _statuscode = response.getStatusLine().getStatusCode();
            return JsonHelper.parseJSONObjectResponse(response.getEntity().getContent());
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (_statuscode > 200 || _statuscode < 300) {
            Toast.makeText(_con, "Vacation created", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(_con, "Vacation not created", Toast.LENGTH_SHORT).show();
        }

    }
}
