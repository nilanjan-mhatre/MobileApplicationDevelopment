package mad.nil.midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Nilanjan Mhatre
 * email: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_second);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);
        onCreateSetup();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartSetup();
    }

    private void onCreateSetup() {

    }

    private void onStartSetup() {

    }
}
