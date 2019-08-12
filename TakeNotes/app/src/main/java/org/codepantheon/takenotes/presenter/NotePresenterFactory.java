package org.codepantheon.takenotes.presenter;

import android.content.Context;

import org.codepantheon.takenotes.data.NoteDatabase;

public final class NotePresenterFactory {
    public static NotePresenter create(Context context){
        NoteDatabase noteDatabase = NoteDatabase.getDatabase(context);
        return new NotePresenter(noteDatabase);
    }
}
