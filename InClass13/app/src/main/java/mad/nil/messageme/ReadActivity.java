package mad.nil.messageme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class ReadActivity extends AppCompatActivity {

    EmailObj email;

    TextView fromText;
    TextView messageText;
    Button backButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        setupUI();
    }

    private void setupUI() {
        Toolbar toolbar = findViewById(R.id.inbox_toolbar);
        toolbar.setTitle(R.string.read_message);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().show();

        auth = FirebaseAuth.getInstance();

        fromText = findViewById(R.id.from_text);
        messageText = findViewById(R.id.message_text);
        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupData();
    }

    private void setupData() {
        if(auth.getCurrentUser() == null) {
            finish();
            return;
        }
        email = (EmailObj) getIntent().getSerializableExtra("email");
        fromText.setText(email.getFromUsername());
        messageText.setText(email.getMessage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sign_out) {
            auth.signOut();
            finish();
        } else if(item.getItemId() == R.id.delete) {
            deleteMessage();
            finish();
        } else if(item.getItemId() == R.id.reply) {
            reply();
        }
        return super.onOptionsItemSelected(item);
    }

    private void reply() {
        Intent intent = new Intent(this, ComposeActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    private void deleteMessage() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("messages").child(email.getToUserId()).child(email.getKey().toString());
        database.keepSynced(true);
        database.removeValue();
    }
}
