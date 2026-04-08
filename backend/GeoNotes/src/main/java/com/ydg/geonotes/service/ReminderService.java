package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.Reminder;
import com.ydg.geonotes.repository.NoteRepository;
import com.ydg.geonotes.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Transactional
    public Reminder createOrUpdateReminder(Long noteId, LocalDateTime date) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Not bulunamadı!"));

        Reminder reminder = reminderRepository.findByNoteId(noteId)
                .orElse(new Reminder());

        reminder.setNote(note);
        reminder.setReminderDate(date);
        reminder.setCompleted(false);

        return reminderRepository.save(reminder);
    }

    @Transactional
    public void deleteReminder(Long noteId) {
        Reminder reminder = reminderRepository.findByNoteId(noteId)
                .orElseThrow(() -> new RuntimeException("Hatırlatıcı bulunamadı!"));
        reminderRepository.delete(reminder);
    }
}