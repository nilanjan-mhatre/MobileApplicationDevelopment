package mad.nil.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetImageAsync extends AsyncTask<String, String, String> {

    NewsFunctions newsFunctions;

    public GetImageAsync(NewsFunctions newsFunctions) {
        this.newsFunctions = newsFunctions;
    }

    @Override
    protected String doInBackground(String... params) {

        return params[0];
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        newsFunctions.loadImage(str);
        newsFunctions.dismissDialog();
    }
}