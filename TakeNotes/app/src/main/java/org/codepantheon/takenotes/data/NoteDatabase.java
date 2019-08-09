package org.codepantheon.takenotes.data;

import org.codepantheon.takenotes.model.NoteInfo;

import java.util.List;

public interface NoteDatabase {
    List<NoteInfo> getAllNotes();
}
