package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.Reminder;
import com.ydg.geonotes.repository.NoteRepository;
import com.ydg.geonotes.repository.ReminderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest {

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private ReminderService reminderService; // HATA BURADAYDI: Sınıf adı ReminderService olmalı

    @Test
    @DisplayName("Not için yeni bir hatırlatıcı başarıyla oluşturulmalı")
    void shouldCreateReminderForNote() {
        // Given (Ön Hazırlık)
        Long noteId = 1L;
        LocalDateTime reminderDate = LocalDateTime.now().plusDays(1);
        Note mockNote = new Note();
        mockNote.setId(noteId);

        // Mock davranışlarını tanımlıyoruz
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(mockNote));
        // findByNoteId artık Optional döndüğü için empty() kullanıyoruz
        when(reminderRepository.findByNoteId(noteId)).thenReturn(Optional.empty());
        when(reminderRepository.save(any(Reminder.class))).thenAnswer(i -> i.getArguments()[0]);

        // When (Eylem)
        // Metod adı servis sınıfındakiyle (createOrUpdateReminder) aynı olmalı
        Reminder createdReminder = reminderService.createOrUpdateReminder(noteId, reminderDate);

        // Then (Doğrulama)
        assertNotNull(createdReminder);
        assertEquals(reminderDate, createdReminder.getReminderDate());
        assertEquals(mockNote, createdReminder.getNote());
        assertFalse(createdReminder.isCompleted());
        verify(reminderRepository, times(1)).save(any(Reminder.class));
    }

    @Test
    @DisplayName("Hatırlatıcı başarıyla silinmeli")
    void shouldDeleteReminderSuccessfully() {
        // Given
        Long noteId = 1L;
        Reminder mockReminder = new Reminder();
        mockReminder.setId(10L);

        when(reminderRepository.findByNoteId(noteId)).thenReturn(Optional.of(mockReminder));
        doNothing().when(reminderRepository).delete(mockReminder);

        // When
        reminderService.deleteReminder(noteId);

        // Then
        verify(reminderRepository, times(1)).findByNoteId(noteId);
        verify(reminderRepository, times(1)).delete(mockReminder);
    }

    @Test
    @DisplayName("Hatırlatıcı bulunamadığında hata fırlatmalı")
    void shouldThrowExceptionWhenReminderNotFound() {
        // Given
        Long noteId = 99L;
        when(reminderRepository.findByNoteId(noteId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reminderService.deleteReminder(noteId);
        });

        assertEquals("Hatırlatıcı bulunamadı!", exception.getMessage());
        verify(reminderRepository, never()).delete(any());
    }

}