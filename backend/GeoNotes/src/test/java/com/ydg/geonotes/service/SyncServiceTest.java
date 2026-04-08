package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.SyncState;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SyncServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private SyncService syncService;

    @Test
    @DisplayName("Senkronizasyon onaylandığında durum SYNCED olmalı")
    void shouldUpdateStatusToSyncedWhenConfirmSyncCalled() {
        // Given
        Note mockNote = new Note();
        mockNote.setId(1L);
        mockNote.setSyncState(SyncState.PENDING);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));

        // When
        syncService.confirmSync(1L);

        // Then
        assertEquals(SyncState.SYNCED, mockNote.getSyncState());
        verify(noteRepository, times(1)).save(mockNote);
    }

    @Test
    @DisplayName("Not güncellendiğinde durum PENDING olmalı")
    void shouldUpdateStatusToPendingWhenMarkAsPendingCalled() {
        // Given
        Note mockNote = new Note();
        mockNote.setId(1L);
        mockNote.setSyncState(SyncState.SYNCED);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));

        // When
        syncService.markAsPending(1L);

        // Then
        assertEquals(SyncState.PENDING, mockNote.getSyncState());
        assertNotNull(mockNote.getUpdatedAt(), "UpdatedAt zamanı güncellenmiş olmalı");
        verify(noteRepository, times(1)).save(mockNote);
    }

    @Test
    @DisplayName("Not bulunamadığında markAsPending hata fırlatmalı")
    void shouldThrowExceptionWhenNoteNotFoundInMarkAsPending() {
        // Given
        when(noteRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then (orElseThrow testi)
        assertThrows(NoSuchElementException.class, () -> {
            syncService.markAsPending(99L);
        });
    }

    @Test
    @DisplayName("Not bulunamadığında confirmSync hata fırlatmalı")
    void shouldThrowExceptionWhenNoteNotFoundInConfirmSync() {
        // Given
        when(noteRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            syncService.confirmSync(99L);
        });
    }

}
