package com.ydg.geonotes.entegrasyon;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.NotePhoto;
import com.ydg.geonotes.repository.NotePhotoRepository;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class NotePhotoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NotePhotoRepository notePhotoRepository;

    @Test
    void shouldReturnPhotosForNote() throws Exception {
        // 1. Önce bir not kaydet
        Note note = new Note();
        note.setTitle("Fotoğraflı Not");
        note = noteRepository.save(note);

        // 2. Nota bir fotoğraf ekle
        NotePhoto photo = new NotePhoto();
        photo.setFilePath("storage/emulated/0/DCIM/photo.jpg");
        photo.setNote(note);
        notePhotoRepository.save(photo);

        // 3. API Testi
        mockMvc.perform(get("/api/photos/note/{noteId}", note.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].filePath").value("storage/emulated/0/DCIM/photo.jpg"));
    }

    @Test
    void shouldDeletePhoto() throws Exception {
        // 1. Hazırlık: Not ve Fotoğraf oluştur
        Note note = new Note();
        note.setTitle("Silinecek Fotoğraflı Not");
        note.setFavorite(false);
        note = noteRepository.save(note);

        NotePhoto photo = new NotePhoto();
        photo.setFilePath("temp/to_be_deleted.jpg");
        photo.setNote(note);
        photo = notePhotoRepository.save(photo);

        Long photoId = photo.getId();

        // 2. API üzerinden silme işlemi (DELETE)
        mockMvc.perform(delete("/api/photos/{id}", photoId))
                .andExpect(status().isOk());

        // 3. Doğrulama: Veritabanında artık mevcut olmadığını kontrol et
        boolean exists = notePhotoRepository.existsById(photoId);
        assertFalse(exists, "Fotoğraf veritabanından silinmiş olmalı");
    }


}
