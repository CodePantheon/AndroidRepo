package org.codepantheon.takenotes.presenter;

import org.codepantheon.takenotes.data.NoteDatabase;
import org.codepantheon.takenotes.model.NoteInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotePresenter {
    private final NoteDatabase noteDatabase;

    public NotePresenter(@NotNull NoteDatabase noteDatabase) {
        this.noteDatabase = noteDatabase;
    }

    public List<NoteInfo> getAllNotes() {
        return noteDatabase.getAllNotes();
    }

    public void saveNote(NoteInfo noteInfo){
        if(noteInfo.isNewNote()){
            noteDatabase.addNote(noteInfo);
        }
        else {
            noteDatabase.editNote(noteInfo);
        }
    }
}
