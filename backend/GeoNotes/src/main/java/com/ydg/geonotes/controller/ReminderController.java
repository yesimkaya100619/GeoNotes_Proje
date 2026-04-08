package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.Reminder;
import com.ydg.geonotes.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/{noteId}")
    public ResponseEntity<Reminder> setReminder(
            @PathVariable Long noteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return ResponseEntity.ok(reminderService.createOrUpdateReminder(noteId, date));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> removeReminder(@PathVariable Long noteId) {
        reminderService.deleteReminder(noteId);
        return ResponseEntity.noContent().build();
    }
}