package com.example.notesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mshehab on 4/27/18.
 */

public class NotesDAO {
    private SQLiteDatabase db;

    public NotesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());
        return db.insert(NotesTable.TABLENAME, null, values);
    }

    public boolean update(Note note){
        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_ID, note.getId());
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());

        return db.update(NotesTable.TABLENAME, values,
                NotesTable.COLUMN_ID + "=?",
                new String[]{String.valueOf(note.getId())}) > 0;
    }

    public boolean delete(Note note){
        return db.delete(NotesTable.TABLENAME,
                NotesTable.COLUMN_ID + "=?",
                new String[]{String.valueOf(note.getId())}) > 0;
    }

    public Note get(long id){
        Note note = null;

        Cursor cursor = db.query(NotesTable.TABLENAME,
                new String[]{NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT, NotesTable.COLUMN_TEXT},
                NotesTable.COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            note = buildNoteFromCursor(cursor);
            if(!cursor.isClosed()){
                cursor.close();
            }
        }

        return note;
    }

    public List<Note> getAll(){
        ArrayList<Note> notes = new ArrayList<>();

        Cursor cursor = db.query(NotesTable.TABLENAME,
                new String[]{NotesTable.COLUMN_ID, NotesTable.COLUMN_SUBJECT, NotesTable.COLUMN_TEXT},
                null, null,
                null, null, null);


        if(cursor != null && cursor.moveToFirst()){

            do{
                Note note = buildNoteFromCursor(cursor);
                notes.add(note);
            } while (cursor.moveToNext());


            if(!cursor.isClosed()){
                cursor.close();
            }
        }
        return notes;
    }

    public Note buildNoteFromCursor(Cursor cursor){
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setSubject(cursor.getString(1));
        note.setText(cursor.getString(2));
        return note;
    }

}
