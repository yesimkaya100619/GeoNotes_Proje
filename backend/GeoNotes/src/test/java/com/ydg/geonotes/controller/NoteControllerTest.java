package com.ydg.geonotes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydg.geonotes.entity.Location;
import com.ydg.geonotes.entity.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime; // LocalDateTime ekledik

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNote_ShouldReturnOkAndCreatedNote() throws Exception {
        Note note = new Note();
        note.setUserId(1L);
        note.setFolderId(1L);
        note.setTitle("Yeni Not");
        note.setContent("Test İçeriği");
        // HATA DÜZELTME: setCreatedAt yerine setUpdatedAt ve String yerine LocalDateTime.now()
        note.setUpdatedAt(LocalDateTime.now());

        mockMvc.perform(post("/api/notes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Yeni Not"))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void getNotesByUserId_ShouldReturnNoteList() throws Exception {
        Long userId = 2L; // Diğer testle karışmaması için 2L yapalım

        // HATA DÜZELTME: Constructor içindeki son parametreyi "2025-01-01" (String) yerine LocalDateTime.now() yaptık
        Note note = new Note(null, userId, 1L, "Liste Testi", "İçerik", LocalDateTime.now());

        mockMvc.perform(post("/api/notes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note)));

        mockMvc.perform(get("/api/notes/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.title == 'Liste Testi')]").exists());
    }

    @Test
    void getNotesByUserId_WhenNoNotes_ShouldReturnEmptyList() throws Exception {
        // Veritabanında olmayan bir ID
        mockMvc.perform(get("/api/notes/user/9999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
    @Test
    void createNoteWithLocation_ShouldSaveAll() throws Exception {
        Note note = new Note();
        note.setTitle("Konumlu Not");

        Location loc = new Location();
        loc.setLatitude(41.0082);
        loc.setLongitude(28.9784);
        loc.setNote(note);
        note.setLocation(loc);

        mockMvc.perform(post("/api/notes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location.latitude").value(41.0082));
    }
}