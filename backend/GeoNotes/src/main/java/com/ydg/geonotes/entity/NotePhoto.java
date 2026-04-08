package com.ydg.geonotes.entity;

import jakarta.persistence.*;

@Entity
@Table(name="note_photos")
public class NotePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @ManyToOne
    @JoinColumn(name="note_id")
    private Note note;

    public NotePhoto() {}

    public NotePhoto(Long id, String filePath, Note note) {
        this.id = id;
        this.filePath = filePath;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
