package mad.nil.midterm;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */

public class Application implements Serializable {
    private String name;
    private String artistName;
    private String imageUrl;
    private String releaseDate;
    private ArrayList<String> genreList;
    private String copyright;

    public Application() {
        name = "";
        artistName = "";
        imageUrl = "";
        releaseDate = "";
        genreList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<String> getGenreList() {
        return genreList;
    }

    public void setGenreList(ArrayList<String> genreList) {
        this.genreList = genreList;
    }

    public String toStringGenre() {

        StringBuilder builder = new StringBuilder();
        if(genreList != null && genreList.size() > 0) {
            Collections.sort(genreList);
            for(String genre : genreList) {
                builder.append(genre);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
