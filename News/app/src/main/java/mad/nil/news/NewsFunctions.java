package mad.nil.news;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by nilan on 2/13/2018.
 */


public interface NewsFunctions {

    void loadKeywords(List<String> keywordsList);

    void loadImageURLList(List<String> imageURLList);

    void getImageFromURL(String url);

    void displayImage(Bitmap bitmap);

    void dismissDialog();

    void showDialog(String message);
}
