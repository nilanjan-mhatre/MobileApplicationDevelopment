package com.example.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalexam.utils.Person;
import com.example.finalexam.utils.PersonsAdapter;
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
public class ChristmasListActivity extends AppCompatActivity {
    final String TAG = "demo";
    ListView listView;
    PersonsAdapter personsAdapter;
    ArrayList<Person> persons = new ArrayList<>();

    Integer id = 1;

    private FirebaseAuth auth;
    private FirebaseUser fUser;

    DatabaseReference personDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_list);
        setTitle(R.string.main_name);
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();

        setupdata();
        listView = findViewById(R.id.listview);
        personsAdapter = new PersonsAdapter(this, R.layout.person_item, persons);
        listView.setAdapter(personsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ChristmasListActivity.this, PersonGiftsActivity.class);
                intent.putExtra("personId", persons.get(position).getId());
                intent.putExtra("name", persons.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void setupdata() {
        personDatabase = FirebaseDatabase.getInstance().getReference()
                .child(fUser.getUid())
                .child("persons");
        personDatabase.keepSynced(true);

        personDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap) dataSnapshot.getValue();

                if(map != null) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    Person person = gson.fromJson(jsonElement, Person.class);
                    persons.add(person);
                    id = Integer.parseInt(person.getId()) + 1;
                    Collections.sort(persons);
                }
                personsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap) dataSnapshot.getValue();

                if(map != null) {
                    Gson gson = new Gson();
                    JsonElement jsonElement = gson.toJsonTree(map);
                    Person person = gson.fromJson(jsonElement, Person.class);
                    Iterator<Person> iterator = persons.iterator();
                    int tempId = -1;
                    while(iterator.hasNext()) {
                        Person temp = iterator.next();
                        if(temp.getId().equals(person.getId())) {
                            tempId = persons.indexOf(temp);
                            break;
                        }
                    }
                    if(tempId > 0) {
                        persons.set(tempId, person);
                        personsAdapter.notifyDataSetChanged();
                    }
                }
                personsAdapter.notifyDataSetChanged();
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
        inflater.inflate(R.menu.christmas_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_person_menu_item:
                Log.d(TAG, "onOptionsItemSelected: add person");
                Intent intent = new Intent(this, AddPersonActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            case R.id.logout_menu_item:
                auth.signOut();
                finish();
                Intent intentLogin = new Intent(ChristmasListActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                Log.d(TAG, "onOptionsItemSelected: logout");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
