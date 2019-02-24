package mad.nil.newsactivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeadlinesRecyclerActivity extends AppCompatActivity implements NewsFunctions {

    public static final String NEWS_URL = "https://newsapi.org/v2/top-headlines";
    public static final String NEWS_COUNTRY = "us";
    public static final String API_KEY = "5dbfb20add3346c3ad007e38d5427d8e";
    public static final String BLANK = "";
    public static final int POOL_SIZE = 4;

    Dialog dialog;
    TextView dialogTextView;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    ScheduledExecutorService scheduler;
    Handler handler;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headlines_recycler);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setLayoutParams(params);

        dialogTextView = new TextView(this);
        progressBar = new ProgressBar(this);
        linearLayout.addView(progressBar);
        linearLayout.addView(dialogTextView);

        dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(linearLayout);
        dialog.create();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                HeadlinesRecyclerActivity.this.handleMessage(msg);
                return true;
            }
        });
        progressDialog = new ProgressDialog(this);
        scheduler = Executors.newScheduledThreadPool(POOL_SIZE);
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showDialog(getResources().getString(R.string.loading_news_message));

        String category = getIntent().getExtras().getString("category");
        setTitle(category);

        if (isConnected()) {
//            new GetHeadlinesAsync(this).execute(NEWS_URL, NEWS_COUNTRY, category, API_KEY);
            scheduler.schedule(new ContentLoader(handler, category), 2, TimeUnit.SECONDS);
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            dismissDialog();
        }
    }

    public void handleMessage(Message message) {
        Bundle bundle = message.getData();
        new GetHeadlinesAsync(this).execute(NEWS_URL, NEWS_COUNTRY, bundle.getString("category"), API_KEY);
    }

    @Override
    public void loadHeadlines(final List<News> headlines) {
        if (headlines.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
            layoutManager = new LinearLayoutManager(this);
            layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);

            final NewsAdapterRecycler adapter = new NewsAdapterRecycler((ArrayList<News>) headlines);
            recyclerView.setAdapter(adapter);

            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showNews(headlines.get(position));
                }
            });*/
        } else {
            Toast.makeText(this, R.string.no_news, Toast.LENGTH_LONG).show();
        }
    }

    private void showNews(News news) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("news", news);
        startActivity(intent);
    }

    public void dismissDialog() {
        progressDialog.dismiss();
    }

    public void showDialog(String message) {
        if (message == null || message.equals(BLANK)) {
            message = getString(R.string.default_loading_message);
        }
//        dialogTextView.setText(message);
//        dialog.show();
        progressDialog.setTitle(message);
        progressDialog.show();
    }
}