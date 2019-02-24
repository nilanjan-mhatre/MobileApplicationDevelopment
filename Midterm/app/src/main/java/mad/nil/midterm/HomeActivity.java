package mad.nil.midterm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class HomeActivity extends AppCompatActivity implements InterfaceFunctions {

    private static final int POOL_SIZE = 3;
    Dialog dialog;
    ProgressDialog progressDialog;
    Dialog progressbarDialog;
    ProgressBar progressbarPercentage;
    ScheduledExecutorService scheduledExecutorService;
    Handler delayHandler;
    AsyncTask backgroundTask;
    RequestOptions glideOptions;
    ListView listView;

    ArrayList<String> genreList = new ArrayList<>();

    RecyclerView recyclerView;
    NewsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<Application> applicationList;
    private ArrayList<Application> allApplicationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher_round);
        setTitle(R.string.app_name);

        onCreateSetup();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void onCreateSetup() {

        //setup dialog
        dialog = new Dialog(this);
        dialog.setCancelable(false);
        View dialogView = getLayoutInflater().inflate(R.layout.loading_layout, null);
        dialog.setContentView(dialogView);
        ImageView dialogImageView = dialogView.findViewById(R.id.dialog_loading_image);
        TextView dialogTextView = dialogView.findViewById(R.id.dialog_loading_text);

        Glide.with(this).load(R.drawable.loading_cat)
                .thumbnail(Glide.with(this).load(R.drawable.loading_cat))
                .into(dialogImageView);

        //setup dialog
//        progressbarDialog = new Dialog(this);
//        progressbarDialog.setCancelable(false);
//        progressbarPercentage = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
//        View dialogView = getLayoutInflater().inflate(R.layout.loading_layout, null);
//        progressbarDialog.setContentView(dialogView);
//        ImageView dialogImageView = dialogView.findViewById(R.id.dialog_loading_image);
//        TextView dialogTextView = dialogView.findViewById(R.id.dialog_loading_text);

//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(dialogImageView);
//        Glide.with(this).load(R.drawable.loader).into(imageViewTarget);
//        dialogTextView.setText(R.string.loading);

        //Progress Dialog
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setCancelable(false);
//        progressDialog.setProgress(0);
//        progressDialog.setMax(100);

        //setup objects
        scheduledExecutorService = Executors.newScheduledThreadPool(POOL_SIZE);
        delayHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
            HomeActivity.this.handleMessage(msg);
            return true;
            }
        });
        glideOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.loading_cat)
                .error(R.drawable.not_found_404)
                .priority(Priority.LOW);

//        Button button = findViewById(R.id.abc);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                show(v);
//            }
//        });

        if(isConnected()) {
            onStartSetup();
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            Button button = findViewById(R.id.filter_button);
            button.setText(R.string.refresh);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStartSetup();
                }
            });
        }
    }

    private void onStartSetup() {
        if(isConnected()) {
            showDialog(null);
            scheduledExecutorService.schedule(new DelayThread(delayHandler, Code.DEFAULT.getValue(), ""), 3, TimeUnit.SECONDS);
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            Button button = findViewById(R.id.filter_button);
            button.setText(R.string.refresh);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStartSetup();
                }
            });
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    private void displayImageFromUrl(ImageView imageView, String url) {
        Glide.with(this).load(url)
                .thumbnail(Glide.with(this).load(R.drawable.loading_cat))
                .apply(glideOptions)
                .into(imageView);
    }

    private void show(View view) {
//        ImageView imageView = findViewById(R.id.image);
//        String url = "https://media.giphy.com/media/O5AZJzYhCr1NS/source.gif";
//        displayImageFromUrl(imageView, url);
    }

    private void goToSecondActivity() {
        Intent secondActivity = new Intent(this, SecondActivity.class);
        startActivity(secondActivity);
    }

    private void handleMessage(Message msg) {
        Bundle bundle = msg.getData();

        int command = bundle.getInt("code");
        if(Code.getCodeById(command) == Code.DEFAULT) {
            if(backgroundTask != null) {
                backgroundTask.cancel(true);
            }
                backgroundTask = new GetContentAsyncTask(this, Code.GET_APPS).execute("https://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json");
        } else if(Code.getCodeById(command) == Code.FILTER) {
            filterResults(bundle.getString("parameter"));
        }
    }

    @Override
    public void loadApplications(final ArrayList<Application> applications) {
        if (applications.size() > 0) {
            allApplicationList = applications;
            HashSet<String> temp = new HashSet<>();
            for(Application application : allApplicationList) {
                temp.addAll(application.getGenreList());
            }
            applicationList = new ArrayList<>(allApplicationList);
            genreList = new ArrayList<>(temp);
            Collections.sort(genreList);
            genreList.add(0, getResources().getString(R.string.all));
            final ArrayList<String> temp2 = new ArrayList<>(genreList);
            Button button = findViewById(R.id.filter_button);
            button.setText(R.string.filter);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(R.string.choose)
                    .setItems(genreList.toArray(new String[genreList.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            filter(temp2.get(i));
                        }
                    }).setCancelable(false);
            builder.create();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.show();
                }
            });
//            RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
//            layoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(layoutManager);
//
//            final ApplicationAdapterRecycler adapter = new ApplicationAdapterRecycler(applicationList);
//            recyclerView.setAdapter(adapter);

            listView = findViewById(R.id.category_list_view);
            listView.setItemsCanFocus(true);
            adapter = new NewsAdapter(this, R.layout.application_layout, applicationList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showApplication(applicationList.get(position));
                }
            });
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showNews(headlines.get(position));
                }
            });*/
        } else {
            Toast.makeText(this, R.string.no_applications, Toast.LENGTH_LONG).show();

        }
    }

    private void filter(String s) {
        showDialog(null);
        scheduledExecutorService.schedule(new DelayThread(delayHandler, Code.FILTER.getValue(), s), 1, TimeUnit.SECONDS);
    }

    private void filterResults(String s) {
        TextView textView = findViewById(R.id.selected_genre);
        textView.setText(s);

        for(Application application : allApplicationList) {
            if(!application.getGenreList().contains(s) && !s.equals(getResources().getString(R.string.all))) {
                applicationList.remove(application);
            } else if(!applicationList.contains(application)) {
                applicationList.add(application);
            }
        }
        adapter.notifyDataSetChanged();
        dismissDialog();
    }

    private void showApplication(Application application) {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra(Code.APPLICATION.name(), application);
        startActivity(intent);
    }

    public void showDialog(String message) {
        if(StringUtils.isEmpty(message)) {
            message = getString(R.string.loading);
        }
        TextView dialogTextView = dialog.findViewById(R.id.dialog_loading_text);
        dialogTextView.setText(message);

        dialog.show();
    }

    @Override
    public void dismissDialog() {
        dialog.dismiss();
    }
}
