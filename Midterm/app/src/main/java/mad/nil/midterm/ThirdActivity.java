package mad.nil.midterm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class ThirdActivity extends AppCompatActivity {

    RequestOptions glideOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        onCreateSetup();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartSetup();
    }

    private void onCreateSetup() {

        glideOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.loading_cat)
                .error(R.drawable.not_found_404)
                .priority(Priority.LOW);
        setTitle(getString(R.string.app_details));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void onStartSetup() {

        Intent intent = getIntent();
        Application application = (Application) intent.getSerializableExtra(Code.APPLICATION.name());

        TextView name = findViewById(R.id.name_text);
        TextView artist = findViewById(R.id.artist_text);
        TextView date = findViewById(R.id.date_text);
        TextView copyright = findViewById(R.id.copyright_text);
        TextView genre = findViewById(R.id.genre_text);
        ImageView imageView = findViewById(R.id.app_image);
        displayImageFromUrl(imageView, application.getImageUrl());

        name.setText(application.getName());
        artist.setText(application.getArtistName());
        date.setText(application.getReleaseDate());
        copyright.setText(application.getCopyright());
        genre.setText(application.toStringGenre());
    }

    private void displayImageFromUrl(ImageView imageView, String url) {
        Glide.with(this).load(url)
                .thumbnail(Glide.with(this).load(R.drawable.loading_cat))
                .apply(glideOptions)
                .into(imageView);
    }
}
