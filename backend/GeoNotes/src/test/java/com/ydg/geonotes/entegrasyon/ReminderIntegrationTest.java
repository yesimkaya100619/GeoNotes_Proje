package com.ydg.geonotes.entegrasyon;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.Reminder;
import com.ydg.geonotes.repository.NoteRepository;
import com.ydg.geonotes.repository.ReminderRepository;
import com.ydg.geonotes.service.ReminderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReminderIntegrationTest {
    @Autowired
    private ReminderService reminderService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @Test
    void testReminderPersistence() {
        Note note = new Note(); note.setTitle("Test");
        note = noteRepository.save(note);

        LocalDateTime date = LocalDateTime.now().plusHours(5);
        Reminder saved = reminderService.createOrUpdateReminder(note.getId(), date);

        assertNotNull(saved.getId());
        assertEquals(note.getId(), saved.getNote().getId());
    }

    @Test
    @DisplayName("Hatırlatıcı veritabanından başarıyla silinmeli")
    void testCreateAndDeleteReminderIntegration() {
        // 1. Hazırlık: Not ve Hatırlatıcı oluştur
        Note note = new Note();
        note.setTitle("Silinecek Hatırlatıcı Notu");
        note.setFavorite(false);
        note = noteRepository.save(note);

        LocalDateTime date = LocalDateTime.now().plusDays(1);
        reminderService.createOrUpdateReminder(note.getId(), date);

        // 2. Eylem: Silme metodunu çağır
        reminderService.deleteReminder(note.getId());

        // 3. Doğrulama: Repository üzerinden gerçekten silindi mi kontrol et
        Optional<Reminder> deletedReminder = reminderRepository.findByNoteId(note.getId());
        assertTrue(deletedReminder.isEmpty(), "Veritabanında hatırlatıcı bulunmamalıydı");
    }

}