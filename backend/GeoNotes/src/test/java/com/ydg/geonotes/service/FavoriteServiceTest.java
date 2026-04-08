package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    @Test
    @DisplayName("Favori durumu false ise true olmalı")
    void shouldToggleFavoriteStatus() {
        // Given
        Note note = new Note();
        note.setId(1L);
        note.setFavorite(false);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));

        // When
        boolean result = favoriteService.toggleFavorite(1L);

        // Then
        assertTrue(result);
        verify(noteRepository).save(note);
    }
}