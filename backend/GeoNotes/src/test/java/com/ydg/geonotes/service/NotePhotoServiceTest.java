package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.NotePhoto;
import com.ydg.geonotes.repository.NotePhotoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotePhotoServiceTest {

    @Mock
    private NotePhotoRepository notePhotoRepository;

    @InjectMocks
    private NotePhotoService notePhotoService;

    @Test
    void shouldSavePhotoSuccessfully() {
        NotePhoto photo = new NotePhoto();
        photo.setFilePath("/images/note1.jpg");

        when(notePhotoRepository.save(any(NotePhoto.class))).thenReturn(photo);

        NotePhoto saved = notePhotoService.savePhoto(photo);

        assertEquals("/images/note1.jpg", saved.getFilePath());
        verify(notePhotoRepository, times(1)).save(photo);
    }

    @Test
    @DisplayName("Not ID'sine göre fotoğraflar listelenmeli")
    void shouldGetPhotosByNoteId() {
        // Given
        Long noteId = 1L;
        NotePhoto photo1 = new NotePhoto();
        NotePhoto photo2 = new NotePhoto();
        List<NotePhoto> mockPhotos = Arrays.asList(photo1, photo2);

        when(notePhotoRepository.findByNoteId(noteId)).thenReturn(mockPhotos);

        // When
        List<NotePhoto> result = notePhotoService.getPhotosByNoteId(noteId);

        // Then
        assertEquals(2, result.size());
        verify(notePhotoRepository, times(1)).findByNoteId(noteId);
    }

    @Test
    @DisplayName("Fotoğraf ID ile başarıyla silinmeli")
    void shouldDeletePhotoSuccessfully() {
        // Given
        Long photoId = 10L;
        // deleteById void bir metot olduğu için doNothing() kullanılır
        doNothing().when(notePhotoRepository).deleteById(photoId);

        // When
        notePhotoService.deletePhoto(photoId);

        // Then
        verify(notePhotoRepository, times(1)).deleteById(photoId);
    }

}
