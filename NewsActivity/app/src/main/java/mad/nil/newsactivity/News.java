package mad.nil.newsactivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Inclass Assignment 6
 * File name: News.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */


public class News implements Serializable {
    public static final String BLANK = "";
    String title;
    String publishedAt;
    String url;
    String imageUrl;
    String description;

    public News() {
        this.title = BLANK;
        this.publishedAt = BLANK;
        this.imageUrl = BLANK;
        this.description = BLANK;
    }

    public News(String title, String publishedAt, String imageUrl, String description) {
        this.title = title;
        this.publishedAt = publishedAt;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if(title == null) {
            this.title = "";
        }
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
        if(publishedAt == null) {
            this.publishedAt = "";
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        if(description == null) {
            this.description = "";
        }
    }
}
