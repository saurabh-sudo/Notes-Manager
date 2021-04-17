package com.examples.Notesmanager.service;

import com.examples.Notesmanager.entity.Note;
import com.examples.Notesmanager.exception.ModificationNotAllowedException;
import com.examples.Notesmanager.exception.NoteNotFoundException;

import java.util.List;

public interface NoteService {
    //CRUD
    List<Note> getAllNotesData(String noteOwner);

    List<Note> getAllDeletedNotes(String noteOwner);

    void restoreDeletedNotes(String noteOwner);

    Note createNoteData(Note note);

    Note updateNoteData(Note note, boolean archiveFlag);

    boolean DeleteNoteDataHardMode(long noteId, String noteOwner) throws NoteNotFoundException, ModificationNotAllowedException;

    boolean DeleteNoteSoftMode(long noteId, String noteOwner) throws NoteNotFoundException, ModificationNotAllowedException;

    boolean emptySoftDelete(long noteId, String noteOwner) throws NoteNotFoundException, ModificationNotAllowedException;

}
