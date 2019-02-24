package com.example.notesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by mshehab on 4/27/18.
 */

public class DBDataManager {
    private Context mContext;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NotesDAO notesDAO;

    public DBDataManager(Context mContext) {
        this.mContext = mContext;

        dbOpenHelper = new DBOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        notesDAO = new NotesDAO(db);
    }

    public void close(){
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    public long saveNote(Note note){
        return this.notesDAO.save(note);
    }

    public boolean updateNote(Note note){
        return this.notesDAO.update(note);
    }

    public boolean deleteNote(Note note){
        return this.notesDAO.delete(note);
    }

    public Note getNote(long id){
        return this.notesDAO.get(id);
    }

    public List<Note> getAllNotes(){
        return this.notesDAO.getAll();
    }

}
