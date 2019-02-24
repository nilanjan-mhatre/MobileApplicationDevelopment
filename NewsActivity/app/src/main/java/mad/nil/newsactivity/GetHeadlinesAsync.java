package mad.nil.newsactivity;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Inclass Assignment 6
 * File name: GetHeadlinesAsync.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */



public class GetHeadlinesAsync extends AsyncTask<String, String, String> {

    NewsFunctions newsFunctions;

    public GetHeadlinesAsync(NewsFunctions newsFunctions) {
        this.newsFunctions = newsFunctions;
    }

    @Override
    protected String doInBackground(String... params) {
        List<String> photoUrlList = new ArrayList<>();
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String result = null;

        try {
            StringBuilder urlBuilder = new StringBuilder(params[0]);
            urlBuilder.append("?country=");
            urlBuilder.append(params[1]);
            urlBuilder.append("&category=");
            urlBuilder.append(params[2]);
            urlBuilder.append("&apiKey=");
            urlBuilder.append(params[3]);
            Log.d("demo", urlBuilder.toString());
            URL url = new URL(urlBuilder.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                result = reader.readLine();
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
        return result;
    }

    @Override
    protected void onPostExecute(String strings) {
        super.onPostExecute(strings);
        newsFunctions.loadHeadlines(parseNewsJSON(strings));
        newsFunctions.dismissDialog();
    }

    public ArrayList<News> parseNewsJSON(String jsonString) {
        ArrayList<News> headlines = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray articles = object.getJSONArray("articles");
            for(int i=0; i<articles.length(); i++) {
                JSONObject jsonObject = (JSONObject) articles.get(i);
                News news = parseArticle(jsonObject);
                headlines.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headlines;
    }

    private News parseArticle(JSONObject jsonObject) throws  JSONException {
        News news = new News();
        String title = jsonObject.getString("title");
        if(title == null || title.equals("null")) {
            title = "";
        }
        String description = jsonObject.getString("description");
        if(description == null || description.equals("null")) {
            description = "";
        }
        String urlToImage = jsonObject.getString("urlToImage");
        if(urlToImage == null || urlToImage.equals("null")) {
            urlToImage = "";
        }
        String publishedAt = jsonObject.getString("publishedAt");
        if(publishedAt == null || publishedAt.equals("null")) {
            publishedAt = "";
        }

        news.setTitle(title);
        news.setDescription(description);
        news.setImageUrl(urlToImage);
        news.setPublishedAt(publishedAt);

        return news;
    }
}
