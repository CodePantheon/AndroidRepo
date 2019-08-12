package org.codepantheon.takenotes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.codepantheon.takenotes.data.NotesContract.*;
import org.codepantheon.takenotes.model.NoteInfo;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase {
    private static NoteDatabase noteDatabase;
    private final SQLiteDatabase database;

    private NoteDatabase(Context context){
        NotesDbHelper notesDbHelper = new NotesDbHelper(context);
        database = notesDbHelper.getWritableDatabase();
    }

    public static NoteDatabase getDatabase(Context context){
        if(noteDatabase != null) {
            return noteDatabase;
        }

        noteDatabase = new NoteDatabase(context);
        return noteDatabase;
    }

    public long addNote(NoteInfo noteInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesEntry.COLUMN_TITLE, noteInfo.getTitle());
        contentValues.put(NotesEntry.COLUMN_CONTENT, noteInfo.getContent());

        return database.insert(NotesEntry.TABLE_NAME, null, contentValues);
    }

    public int editNote(NoteInfo noteInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesEntry.COLUMN_TITLE, noteInfo.getTitle());
        contentValues.put(NotesEntry.COLUMN_CONTENT, noteInfo.getContent());

        final String WHERE_CLAUSE = NotesEntry._ID + "=" + noteInfo.getId();
        return database.update(NotesEntry.TABLE_NAME, contentValues, WHERE_CLAUSE, null);
    }

    public int deleteNote(NoteInfo noteInfo) {
        final String WHERE_CLAUSE = NotesEntry._ID + "=" + noteInfo.getId();
        return database.delete(NotesEntry.TABLE_NAME, WHERE_CLAUSE, null);
    }

    public List<NoteInfo> getAllNotes() {
        Cursor cursor = database.query(
                NotesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NotesEntry.COLUMN_TIMESTAMP
        );

        List<NoteInfo> noteInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            NoteInfo noteInfo = new NoteInfo(cursor.getLong(cursor.getColumnIndex(NotesEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_CONTENT)),
                    cursor.getString(cursor.getColumnIndex(NotesEntry.COLUMN_TIMESTAMP)));
            noteInfoList.add(noteInfo);
        }

        cursor.close();
        return noteInfoList;
    }

    private static class NotesDbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "takeNotes.db";
        private static final int DATABASE_VERSION = 1;

        private NotesDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " + NotesEntry.TABLE_NAME + " (" +
                    NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NotesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    NotesEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                    NotesEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    "); ";
            sqLiteDatabase.execSQL(SQL_CREATE_NOTES_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // For now simply drop the table and create a new one. This means if you change the
            // DATABASE_VERSION the table will be dropped.
            // In a production app, this method might be modified to ALTER the table
            // instead of dropping it, so that existing data is not deleted.
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}