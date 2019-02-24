package com.example.notesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mshehab on 4/27/18.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    static final public String DBNAME = "notes";
    static final public int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DBOpenHelper.DBNAME, null, DBOpenHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        NotesTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        NotesTable.onUpdate(sqLiteDatabase);
    }
}
