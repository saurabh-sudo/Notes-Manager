package com.examples.Notesmanager.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notes")
public class Note {

    //You have to create an application which can be used to store, update, delete and create Notes
    // . Here Note represents a text-only entity. Which would contain name, and information regarding the creator of the note
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long noteId;

    private String noteData;

    private String noteHeadline;

    private String noteOwner;

    @Temporal(TemporalType.DATE)
    private Date createdDate=new Date();;

    @Temporal(TemporalType.DATE)
    private Date lastUpdateAt=new Date();;

    private boolean isSoftDeleted = false;

    private boolean isArchived=false;

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getNoteData() {
        return noteData;
    }

    public void setNoteData(String noteData) {
        this.noteData = noteData;
    }

    public String getNoteHeadline() {
        return noteHeadline;
    }

    public void setNoteHeadline(String noteHeadline) {
        this.noteHeadline = noteHeadline;
    }

    public String getNoteOwner() {
        return noteOwner;
    }

    public void setNoteOwner(String noteOwner) {
        this.noteOwner = noteOwner;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public boolean isSoftDeleted() {
        return isSoftDeleted;
    }

    public void setSoftDeleted(boolean softDeleted) {
        isSoftDeleted = softDeleted;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteData='" + noteData + '\'' +
                ", noteHeadline='" + noteHeadline + '\'' +
                ", noteOwner='" + noteOwner + '\'' +
                ", createdDate=" + createdDate +
                ", lastUpdateAt=" + lastUpdateAt +
                ", isSoftDeleted=" + isSoftDeleted +
                ", isArchived=" + isArchived +
                '}';
    }
}
