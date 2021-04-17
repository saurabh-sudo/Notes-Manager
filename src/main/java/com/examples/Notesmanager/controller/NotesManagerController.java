package com.examples.Notesmanager.controller;

import com.examples.Notesmanager.entity.Note;
import com.examples.Notesmanager.exception.ModificationNotAllowedException;
import com.examples.Notesmanager.exception.NoteNotFoundException;
import com.examples.Notesmanager.repository.NotesRepository;
import com.examples.Notesmanager.service.impl.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
public class NotesManagerController {

    @Autowired
    private NoteServiceImpl noteService;

    @Autowired
    private NotesRepository notesRepository;

    @GetMapping("/getAllNotes/{userName}")
    public ResponseEntity getAllNotes(@PathVariable String userName) throws NoteNotFoundException {
        List<Note> notesList = noteService.getAllNotesData(userName);
        if (notesList == null) {
            throw new NoteNotFoundException("Notes not found for particular User");
        }
        return new ResponseEntity<>(notesList, HttpStatus.OK);
    }

    @GetMapping("/restoreDeletedNotes/{userName}")
    public ResponseEntity restoreDeletedNotes(@PathVariable String userName) throws NoteNotFoundException {
        noteService.restoreDeletedNotes(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAllDeletedNotes/{userName}")
    public ResponseEntity getAllDeletedNotes(@PathVariable String userName) throws NoteNotFoundException {
        List<Note> notesList = noteService.getAllDeletedNotes(userName);
        if (notesList == null) {
            throw new NoteNotFoundException("Notes not found for particular User");
        }
        return new ResponseEntity<>(notesList, HttpStatus.OK);
    }


    @PostMapping("/createNote/addNote")
    public Note createNote(@RequestBody Note noteData) {
        return noteService.createNoteData(noteData);
    }

    @PutMapping("/updateNote/{noteId}")
    public ResponseEntity<Note> updateNote(@PathVariable(value = "noteId") Long noteId,
                                               @RequestBody Note noteDetails) throws NoteNotFoundException {
        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found for this id :: " + noteId));

        final Note updateNoteData = noteService.updateNoteData(noteDetails, false);
        return ResponseEntity.ok(updateNoteData);
    }

    @DeleteMapping("/deleteNote/hard/{noteOwner}/{id}")
    public Map<String, Boolean> deleteNote(@PathVariable(value = "noteOwner") String noteOwner, @PathVariable(value = "id") Long noteId)
            throws NoteNotFoundException, ModificationNotAllowedException {

        boolean noteDeleted = noteService.DeleteNoteDataHardMode(noteId, noteOwner);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/deleteNote/soft/{noteOwner}/{id}")
    public Map<String, Boolean> deleteNoteSoftMode(@PathVariable(value = "noteOwner") String noteOwner, @PathVariable(value = "id") Long noteId)
            throws NoteNotFoundException, ModificationNotAllowedException {

        boolean noteDeleted = noteService.DeleteNoteSoftMode(noteId, noteOwner);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/deleteNote/emptySoftDelete/{noteOwner}/{id}")
    public Map<String, Boolean> emptySoftDelete(@PathVariable(value = "noteOwner") String noteOwner, @PathVariable(value = "id") Long noteId)
            throws NoteNotFoundException, ModificationNotAllowedException {

        boolean noteDeleted = noteService.DeleteNoteSoftMode(noteId, noteOwner);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping("/archiveNote/{noteId}")
    public ResponseEntity<Note> archiveNote(@PathVariable(value = "noteId") Long noteId,
                                           @RequestBody Note noteDetails) throws NoteNotFoundException {
        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found for this id :: " + noteId));

        final Note updateNoteData = noteService.updateNoteData(noteDetails, true);
        return ResponseEntity.ok(updateNoteData);
    }

}
