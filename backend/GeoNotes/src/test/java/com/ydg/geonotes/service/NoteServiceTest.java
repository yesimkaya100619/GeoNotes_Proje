package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime; // LocalDateTime eklemeyi unutma
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void whenSaveNote_shouldReturnSavedNote() {
        // Given
        // DÜZELTME: 2025-01-01 yerine LocalDateTime.now() kullanıyoruz
        Note note = new Note(null, 1L, null, "Test Başlık", "İçerik", LocalDateTime.now());

        Mockito.when(noteRepository.save(any(Note.class))).thenReturn(note);

        // When
        Note savedNote = noteService.saveNote(note);

        // Then
        assertEquals("Test Başlık", savedNote.getTitle());
        // verify kısmında metodun 1 kez çağrıldığını doğruluyoruz
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void whenGetAllNotesByUserId_shouldReturnNoteList() {
        // Given
        Long userId = 1L;
        Note note1 = new Note(null, userId, null, "Başlık 1", "İçerik 1", LocalDateTime.now());
        Note note2 = new Note(null, userId, null, "Başlık 2", "İçerik 2", LocalDateTime.now());
        List<Note> mockNotes = List.of(note1, note2);

        Mockito.when(noteRepository.findByUserId(userId)).thenReturn(mockNotes);

        // When
        List<Note> result = noteService.getAllNotesByUserId(userId);

        // Then
        assertEquals(2, result.size());
        assertEquals("Başlık 1", result.get(0).getTitle());
        verify(noteRepository, times(1)).findByUserId(userId);
    }

}