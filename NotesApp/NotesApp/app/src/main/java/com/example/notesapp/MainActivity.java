package com.example.notesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    DBDataManager dbDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbDataManager = new DBDataManager(this);

        Log.d("demo", "onCreate: " + dbDataManager.getAllNotes());

        Note note = new Note();
        note.setSubject("Subject 1");
        note.setText("Text 1");
        dbDataManager.saveNote(note);
        dbDataManager.close();
    }
}
