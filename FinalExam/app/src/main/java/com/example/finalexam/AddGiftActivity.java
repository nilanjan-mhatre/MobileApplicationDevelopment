package com.example.finalexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
public class AddGiftActivity extends AppCompatActivity {
    final String TAG = "demo";
    ListView listView;
    GiftsAdapter giftsAdapter;
    ArrayList<Gift> gifts = new ArrayList<>();
    String personId;
    Integer budgetRemaining;

    private FirebaseAuth auth;
    private FirebaseUser fUser;

    DatabaseReference giftDatabase;;
    private DatabaseReference personDatabase;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gift);
        setTitle(R.string.add_gift);
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();

        personId = getIntent().getExtras().getString("personId");

        setupData();
        listView = findViewById(R.id.listview);
        giftsAdapter = new GiftsAdapter(this, R.layout.gift_item, gifts);
        listView.setAdapter(giftsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Gift gift = gifts.get(position);
                person.getGifts().add(gift);
                person.incrementItemsBought(gift.getPrice());
                person.incrementGiftCount();
                personDatabase.setValue(person);

                finish();
            }
        });
    }

    private void setupData() {

        personDatabase = FirebaseDatabase.getInstance().getReference()
                .child(fUser.getUid())
                .child("persons")
                .child(personId.toString());
        personDatabase.keepSynced(true);
        personDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap map = (HashMap) dataSnapshot.getValue();

                if(map != null) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    person = gson.fromJson(jsonElement, Person.class);
                    budgetRemaining = person.getTotalBudget() - person.getTotalBought();
                    if(budgetRemaining == 0) {
                        Toast.makeText(AddGiftActivity.this, "No Budget Available !!", Toast.LENGTH_SHORT).show();
                    } else {
                        setupGifts();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setupGifts() {
        giftDatabase = FirebaseDatabase.getInstance().getReference()
                        .child("gifts");
        giftDatabase.keepSynced(true);

        giftDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap) dataSnapshot.getValue();

                if(map != null) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    Gift gift = gson.fromJson(jsonElement, Gift.class);
                    if(gift.getPrice() <= budgetRemaining) {
                        gift.setId(dataSnapshot.getKey());
                        gifts.add(gift);
                        Collections.sort(gifts);
                        giftsAdapter.notifyDataSetChanged();
                    }
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
}
