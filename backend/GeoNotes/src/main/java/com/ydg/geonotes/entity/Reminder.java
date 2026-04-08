package com.ydg.geonotes.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reminder_date", nullable = false)
    private LocalDateTime reminderDate;

    @Column(name = "is_completed")
    private boolean isCompleted = false;

    @OneToOne
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    private Note note;

    // Getter ve Setterlar...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getReminderDate() { return reminderDate; }
    public void setReminderDate(LocalDateTime reminderDate) { this.reminderDate = reminderDate; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public Note getNote() { return note; }
    public void setNote(Note note) { this.note = note; }
}