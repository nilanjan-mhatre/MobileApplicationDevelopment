package mad.nil.messageme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseApp.initializeApp(this.getApplicationContext());
        auth = FirebaseAuth.getInstance();
        fuser = auth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(fuser == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, EmailActivity.class);
                    intent.putExtra("username", fuser.getDisplayName());
                    startActivity(intent);
                }
                finish();
            }
        };

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.schedule(runnable, 2, TimeUnit.SECONDS);
    }
}
