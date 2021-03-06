package mad.nil.photogallery;

import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilan on 2/13/2018.
 */



public class GetImageURLsAsync extends AsyncTask<String, String, List<String>> {

    ImageFunctions imageFunctions;

    public GetImageURLsAsync(ImageFunctions imageFunctions) {
        this.imageFunctions = imageFunctions;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        List<String> photoUrlList = new ArrayList<>();
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = null;

        try {
            StringBuilder urlBuilder = new StringBuilder(params[0]);
            urlBuilder.append("?keyword=" + params[1]);
            URL url = new URL(urlBuilder.toString());
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
                    photoUrlList.add(photoUrl);
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection !=null) {
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
        return photoUrlList;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        imageFunctions.loadImageURLList(strings);
        imageFunctions.dismissDialog();
    }
}
