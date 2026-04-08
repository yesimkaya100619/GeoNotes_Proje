package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.NotePhoto;
import com.ydg.geonotes.service.NotePhotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotePhotoController.class)
public class NotePhotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotePhotoService notePhotoService;

    @Test
    @DisplayName("Yeni bir fotoğraf başarıyla eklenebilmeli")
    void shouldAddPhotoSuccessfully() throws Exception {
        NotePhoto photo = new NotePhoto();
        // HATALI: photo.setPhotoUrl("...");
        // DOĞRU:
        photo.setFilePath("http://example.com/image.jpg");

        when(notePhotoService.savePhoto(any(NotePhoto.class))).thenReturn(photo);

        // JSON gövdesindeki anahtar kelimeyi de filePath olarak güncellemelisin
        String photoJson = "{\"filePath\": \"http://example.com/image.jpg\"}";

        mockMvc.perform(post("/api/photos/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(photoJson))
                .andExpect(status().isOk())
                // JSON yanıtındaki alanı kontrol ederken de filePath kullanmalısın
                .andExpect(jsonPath("$.filePath").value("http://example.com/image.jpg"));
    }

    @Test
    @DisplayName("Nota ait fotoğraflar listelenebilmeli")
    void shouldGetPhotosByNoteId() throws Exception {
        NotePhoto photo1 = new NotePhoto();
        NotePhoto photo2 = new NotePhoto();

        when(notePhotoService.getPhotosByNoteId(1L)).thenReturn(Arrays.asList(photo1, photo2));

        mockMvc.perform(get("/api/photos/note/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Liste boş olduğunda 200 OK ve boş dizi dönmeli")
    void shouldReturnEmptyListWhenNoPhotosFound() throws Exception {
        when(notePhotoService.getPhotosByNoteId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/photos/note/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Fotoğraf başarıyla silinebilmeli")
    void shouldDeletePhotoSuccessfully() throws Exception {
        doNothing().when(notePhotoService).deletePhoto(1L);

        mockMvc.perform(delete("/api/photos/1"))
                .andExpect(status().isOk());
    }
}