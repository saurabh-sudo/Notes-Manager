package com.examples.Notesmanager.service.impl;

import com.examples.Notesmanager.entity.Note;
import com.examples.Notesmanager.exception.ModificationNotAllowedException;
import com.examples.Notesmanager.exception.NoteNotFoundException;
import com.examples.Notesmanager.repository.NotesRepository;
import com.examples.Notesmanager.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NotesRepository notesRepository;

    @Override
    public List<Note> getAllNotesData(String noteOwner) {

        List<Note> noteList = notesRepository.findByNoteOwner(noteOwner);
        return noteList;
    }

    @Override
    public List<Note> getAllDeletedNotes(String noteOwner) {
        List<Note> noteList = notesRepository.findDeletedByNoteOwner(noteOwner);
        return noteList;
    }

    @Override
    public void restoreDeletedNotes(String noteOwner) {
        notesRepository.restoreAccToNoteOwner(noteOwner);
    }

    @Override
    public Note createNoteData(Note note) {
        Note createdNote = notesRepository.save(note);
        return createdNote;
    }

    @Override
    public Note updateNoteData(Note note, boolean archiveFlag) {
        if(archiveFlag)
        {
            note.setArchived(true);
           return notesRepository.save(note);
        }else
        {
            return notesRepository.save(note);

        }
    }

    @Override
    public boolean DeleteNoteDataHardMode(long noteId, String noteOwner) throws NullPointerException, NoteNotFoundException, ModificationNotAllowedException {
        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found for this id :: " + noteId));
        if (note.getNoteOwner().equals(noteOwner)) {
            notesRepository.deleteById(noteId);
        } else throw new ModificationNotAllowedException("Not able to update");

        return true;
    }

    @Override
    public boolean DeleteNoteSoftMode(long noteId, String noteOwner) throws NoteNotFoundException, ModificationNotAllowedException {
        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found for this id :: " + noteId));
        if (note.getNoteOwner().equals(noteOwner)) {
            note.setSoftDeleted(true);
            notesRepository.save(note);

        } else throw new ModificationNotAllowedException("Not able to update");
        return true;
    }

    @Override
    public boolean emptySoftDelete(long noteId, String noteOwner) throws NoteNotFoundException, ModificationNotAllowedException {
        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found for this id :: " + noteId));
        if (note.getNoteOwner().equals(noteOwner)) {
            note.setSoftDeleted(true);
            notesRepository.deleteByFlag(noteOwner);

        } else throw new ModificationNotAllowedException("Not able to update");
        return true;
    }
}
