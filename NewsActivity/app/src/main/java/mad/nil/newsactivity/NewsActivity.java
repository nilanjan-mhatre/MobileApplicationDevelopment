package mad.nil.newsactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Inclass Assignment 7
 * File name: NewsActivity.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
public class NewsActivity extends AppCompatActivity {

    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle(R.string.detail_title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setup() {
        news = (News) getIntent().getSerializableExtra("news");

        TextView titleTextView = findViewById(R.id.news_title);
        TextView publishedTextView = findViewById(R.id.news_date);
        ImageView newsImageView = findViewById(R.id.news_image);
        TextView descriptionTextView = findViewById(R.id.news_description);

        titleTextView.setText(news.getTitle());
        publishedTextView.setText(news.getPublishedAt());
        descriptionTextView.setText(news.getDescription());

        Picasso.with(this).load(news.getImageUrl()).placeholder(R.drawable.loading).into(newsImageView);
    }
}
