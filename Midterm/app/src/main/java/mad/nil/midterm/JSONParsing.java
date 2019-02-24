package mad.nil.midterm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class JSONParsing {
    public ArrayList<Application> parseApplicationJSON(String jsonString) {
        ArrayList<Application> headlines = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONObject feed = object.getJSONObject("feed");
            JSONArray applicationArray = feed.getJSONArray("results");
            for(int i=0; i<applicationArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) applicationArray.get(i);
                Application application = parseApplication(jsonObject);
                headlines.add(application);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headlines;
    }

    private Application parseApplication(JSONObject jsonObject) throws  JSONException {
        Application application = new Application();
        String name = jsonObject.getString("name");
        if(name == null || name.equals("null")) {
            name = "";
        }
        String artistName = jsonObject.getString("artistName");
        if(artistName == null || artistName.equals("null")) {
            artistName = "";
        }
        String urlToImage = jsonObject.getString("artworkUrl100");
        if(urlToImage == null || urlToImage.equals("null")) {
            urlToImage = "";
        }
        String releaseDate = jsonObject.getString("releaseDate");
        if(releaseDate == null || releaseDate.equals("null")) {
            releaseDate = "";
        }
        String copyright = jsonObject.getString("copyright");
        if(copyright == null || copyright.equals("null")) {
            copyright = "";
        }

        ArrayList<String> genreList = application.getGenreList();
        JSONArray genreArray = jsonObject.getJSONArray("genres");
        for(int i=0; i<genreArray.length(); i++) {
            JSONObject genre = (JSONObject) genreArray.get(i);
            genreList.add(genre.getString("name"));
        }
        Collections.sort(genreList);
        application.setName(name);
        application.setArtistName(artistName);
        application.setImageUrl(urlToImage);
        application.setReleaseDate(releaseDate);
        application.setGenreList(genreList);
        application.setCopyright(copyright);

        return application;
    }
}
