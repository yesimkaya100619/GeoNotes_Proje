package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.ChecklistItem;
import com.ydg.geonotes.repository.ChecklistItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChecklistItemServiceTest {

    @Mock
    private ChecklistItemRepository repository;

    @InjectMocks
    private ChecklistItemService service;

    @Test
    @DisplayName("Yeni checklist öğesi başarıyla kaydedilmeli")
    void shouldSaveItemSuccessfully() {
        ChecklistItem item = new ChecklistItem();
        item.setItemText("Market Alışverişi");
        item.setIsDone(false);

        when(repository.save(any(ChecklistItem.class))).thenReturn(item);

        ChecklistItem saved = service.saveItem(item);

        assertEquals("Market Alışverişi", saved.getItemText());
        assertFalse(saved.isIsDone());
        verify(repository, times(1)).save(item);
    }

    @Test
    @DisplayName("Nota ait tüm öğeler listelenmeli")
    void shouldGetItemsByNoteId() {
        ChecklistItem item1 = new ChecklistItem();
        ChecklistItem item2 = new ChecklistItem();
        when(repository.findByNoteId(1L)).thenReturn(Arrays.asList(item1, item2));

        List<ChecklistItem> result = service.getItemsByNoteId(1L);

        assertEquals(2, result.size());
        verify(repository, times(1)).findByNoteId(1L);
    }

    @Test
    @DisplayName("Öğe başarıyla silinmeli")
    void shouldDeleteItem() {
        Long id = 1L;
        doNothing().when(repository).deleteById(id);

        service.deleteItem(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Öğe durumu tersine çevrilmeli (false -> true)")
    void shouldToggleItemStatus() {
        // Arrange
        ChecklistItem item = new ChecklistItem();
        item.setId(1L);
        item.setIsDone(false);

        when(repository.findById(1L)).thenReturn(Optional.of(item));
        when(repository.save(any(ChecklistItem.class))).thenReturn(item);

        // Act
        ChecklistItem result = service.toggleItemStatus(1L);

        // Assert
        assertTrue(result.isIsDone()); // false idi, true olmalı
        verify(repository).save(item);
    }

    @Test
    @DisplayName("Öğe bulunamadığında hata fırlatmalı")
    void shouldThrowExceptionWhenItemNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.toggleItemStatus(99L);
        });
    }
}