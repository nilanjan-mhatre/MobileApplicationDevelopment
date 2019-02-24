package mad.nil.newsactivity;

import java.util.List;


/**
 * Inclass Assignment 7
 * File name: NewsFunctions.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */



public interface NewsFunctions {

    void loadHeadlines(List<News> headlines);
//
//    void loadCurrentNews();
//
    void dismissDialog();

    void showDialog(String message);
}
