package mad.nil.messageme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class EmailActivity extends AppCompatActivity {

    private ArrayList<EmailObj> emailList;
    ArrayList<String> usernameList;
    ArrayList<String> userIdList;

    private ListView listView;
    private EmailAdapter emailAdapter;

    private FirebaseAuth auth;
    private FirebaseUser fUser;

    DatabaseReference emailDatabase;
    DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        setupData();
        setupUI();
    }

    private void setupUI() {
        emailList = new ArrayList<>();
        listView = findViewById(R.id.email_view);
        emailAdapter = new EmailAdapter(this, R.layout.email_layout, this.emailList);
        listView.setAdapter(emailAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EmailObj email = emailList.get(position);
                email.setRead(true);
                emailDatabase.child(email.getKey().toString()).child("read").setValue(true);
                emailAdapter.notifyDataSetChanged();

                Intent intent = new Intent(EmailActivity.this, ReadActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.inbox_toolbar);
        toolbar.setTitle(R.string.inbox);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
        getSupportActionBar().show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() == null) {
            gotoLogin();
        }
    }

    private void setupData() {
        usernameList = new ArrayList<>();
        userIdList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        emailDatabase = FirebaseDatabase.getInstance().getReference().child("messages").child(fUser.getUid());
        emailDatabase.keepSynced(true);

        emailDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap) dataSnapshot.getValue();

                if(map != null) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    EmailObj emailObj = gson.fromJson(jsonElement, EmailObj.class);
                    emailList.add(emailObj);
                    Collections.sort(emailList);
                }
                emailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                emailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HashMap map = (HashMap) dataSnapshot.getValue();
                EmailObj emailObj = null;
                if(map != null) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    emailObj = gson.fromJson(jsonElement, EmailObj.class);
                }

                Iterator<EmailObj> itr = emailList.iterator();
                while(itr.hasNext()) {
                    EmailObj email = itr.next();
                    if(email.compareTo(emailObj) == 0) {
                        itr.remove();
                        break;
                    }
                }
                emailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        usersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        usersDatabase.keepSynced(true);
        usersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(!dataSnapshot.getKey().equals(auth.getUid())) {
                    usernameList.add(dataSnapshot.getValue().toString());
                    userIdList.add(dataSnapshot.getKey().toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sign_out) {
            auth.signOut();
            gotoLogin();
        } else if(item.getItemId() == R.id.compose_button) {
            Intent intent = new Intent(this, ComposeActivity.class);
            intent.putExtra("usernameList", usernameList);
            intent.putExtra("userIdList", userIdList);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
