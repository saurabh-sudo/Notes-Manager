package com.examples.Notesmanager.repository;

import com.examples.Notesmanager.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.noteOwner = :noteOwner and isSoftDeleted = 0 and isArchived = 0")
    List<Note> findByNoteOwner(@Param("noteOwner") String noteOwner);

    @Modifying
    @Query("delete from Note n where n.noteOwner = :noteOwner and isSoftDeleted = 1 and isArchived = 0 ")
    void deleteByFlag(@Param("noteOwner") String noteOwner);

    @Query("SELECT n FROM Note n WHERE n.noteOwner = :noteOwner and isSoftDeleted = 1 and isArchived = 0")
    List<Note> findDeletedByNoteOwner(@Param("noteOwner") String noteOwner);

    @Modifying
    @Transactional
    @Query("update Note n set isSoftDeleted = 0 where n.noteOwner = :noteOwner and isSoftDeleted = 1")
    void restoreAccToNoteOwner(@Param("noteOwner") String noteOwner);
}
