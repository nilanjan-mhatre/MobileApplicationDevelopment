package mad.nil.midterm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class GetContentAsyncTask extends AsyncTask<String, String, String> {

    InterfaceFunctions interfaceFunctions;
    Code code;

    public GetContentAsyncTask(InterfaceFunctions interfaceFunctions, Code code) {
        this.interfaceFunctions = interfaceFunctions;
        this.code = code;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while(true) {
                    String photoUrl = reader.readLine();
                    if(photoUrl == null || photoUrl.length() == 0) {
                        break;
                    }
                    result.append(photoUrl);
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(code == Code.DEFAULT) {
            interfaceFunctions.dismissDialog();
        } else if(code == Code.GET_APPS) {
            ArrayList<Application> applicationList = new JSONParsing().parseApplicationJSON(result);
            interfaceFunctions.loadApplications(applicationList);
            interfaceFunctions.dismissDialog();
        }
    }
}
