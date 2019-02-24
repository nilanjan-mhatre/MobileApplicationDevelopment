package com.example.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.finalexam.utils.Gift;
import com.example.finalexam.utils.GiftsAdapter;
import com.example.finalexam.utils.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/**
 * Created by Nilanjan Mhatre
 * EmailObj: nmhatre@uncc.edu
 * Student Id: 801045013
 */
public class PersonGiftsActivity extends AppCompatActivity {
    final String TAG = "demo";
    ListView listView;
    GiftsAdapter giftsAdapter;
    ArrayList<Gift> gifts = new ArrayList<>();

    String personId;

    private FirebaseAuth auth;
    private FirebaseUser fUser;

    DatabaseReference personDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_gifts);
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        personId = getIntent().getExtras().getString("personId");

        setTitle(getIntent().getExtras().getString("name"));

        setupData();

        listView = findViewById(R.id.listview);
        giftsAdapter = new GiftsAdapter(this, R.layout.gift_item, gifts);
        listView.setAdapter(giftsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupData() {
        personDatabase = FirebaseDatabase.getInstance().getReference()
                .child(fUser.getUid())
                .child("persons")
                .child(personId.toString())
                .child("gifts");
        personDatabase.keepSynced(true);
        personDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap) dataSnapshot.getValue();

                if(map != null) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    Gift gift = gson.fromJson(jsonElement, Gift.class);
                    gifts.add(gift);
                    giftsAdapter.notifyDataSetChanged();
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.person_gifts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_gift_menu_item:
                Log.d(TAG, "onOptionsItemSelected: ");
                Intent intent = new Intent(this, AddGiftActivity.class);
                intent.putExtra("personId", personId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
