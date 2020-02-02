package com.example.connected_plant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



public class BackgroundWorker extends AsyncTask<String,Void,String>
{
    Context context;
    AlertDialog ard;


    BackgroundWorker(Context ctxt)
    {
        context = ctxt;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://192.168.43.77/html/employee/login.php";
        if(type.equals("login"))
        {
            try {
                String username = params[1];
                String psswd = params[2];
                URL url = new URL(login_url);
                HttpURLConnection htc = (HttpURLConnection)url.openConnection();
                htc.setRequestMethod("POST");
                htc.setDoOutput(true);
                htc.setDoInput(true);
                OutputStream ops = htc.getOutputStream();
                BufferedWriter bfr = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder
                        .encode(username, "UTF-8")+"&"+URLEncoder
                        .encode("password", "UTF-8")+"="+URLEncoder
                        .encode(psswd, "UTF-8");
                bfr.write(post_data);
                bfr.flush();
                bfr.close();
                ops.close();
                InputStream inputStream = htc.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                htc.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        ard = new AlertDialog.Builder(context).create();
         ard.setTitle("Login status");
    }

    @Override
    protected void onPostExecute(String result)
    {
        ard.setMessage(result);
        ard.show();

if(result.equals("login_success"))
{
    Intent intent = new Intent(context, iot_info_pg.class);
    context.startActivity(intent);
}


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
