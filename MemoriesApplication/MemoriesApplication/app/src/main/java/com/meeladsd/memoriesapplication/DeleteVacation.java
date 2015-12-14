package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by Sevag on 2015-12-10.
 */
public class DeleteVacation extends AsyncTask<String, String, Integer> {
     ProgressDialog progress;
    private int VacId;
    int state;
    private Activity con;
    public DeleteVacation(int _vacId, Activity _c) {
        VacId=_vacId;
        con=_c;
        progress = new ProgressDialog(con);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.setTitle("Deleteing a  vacation");
        progress.setMessage("Please wait...");
        progress.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        SharedPreferences myS = con.getSharedPreferences("token", Context.MODE_PRIVATE);
        String t = myS.getString("access_token", "");
        String name = myS.getString("username", "");

        DefaultHttpClient client = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete("http://jthcloudproject.elasticbeanstalk.com/api/v1/vacations/"+VacId);
        httpDelete.addHeader("authorization", "bearer " + t);

        try {

            HttpResponse response = client.execute(httpDelete);
            state = response.getStatusLine().getStatusCode();
        } catch (IOException e) {

            Toast.makeText(con, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return state;
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (progress.isShowing()) {
            progress.dismiss();

            if(state ==200){
            Toast.makeText(con, "Vacations successfully deleted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(con,MainActivity.class);
            con.startActivity(intent);}
            else {
                Toast.makeText(con,  "Error deleting vacations", Toast.LENGTH_LONG).show();


            }
        }



    }
}

