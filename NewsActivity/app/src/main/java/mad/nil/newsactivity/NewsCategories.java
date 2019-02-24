package mad.nil.newsactivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Inclass Assignment 7
 * File name: NewsCategories.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
public class NewsCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_categories);
        setTitle(R.string.category_title);

        setup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setup() {
        final String[] categories = getResources().getStringArray(R.array.news_categories);

//        new GetHeadlinesAsync(NewsCategories.this).execute(NEWS_URL, NEWS_COUNTRY, keyword, API_KEY);

        ListView listView = findViewById(R.id.category_list_view);
//        final ArrayAdapter<Student> arrayAdapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentList);
        final ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openCategory(categories[position]);
            }
        });
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void openCategory(String category) {
        if(isConnected()) {
            Intent intent = new Intent(this, HeadlinesRecyclerActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }
}
