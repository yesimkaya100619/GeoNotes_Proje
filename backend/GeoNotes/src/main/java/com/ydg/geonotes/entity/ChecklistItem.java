package com.ydg.geonotes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="checklist_items")
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemText;
    private boolean isDone;

    @ManyToOne
    @JoinColumn(name= "note_id")
    @JsonIgnore
    private Note note;

    public ChecklistItem() {}

    public ChecklistItem(Long id, String itemText, boolean isDone, Note note) {
        this.id = id;
        this.itemText = itemText;
        this.isDone = isDone;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public boolean isIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
