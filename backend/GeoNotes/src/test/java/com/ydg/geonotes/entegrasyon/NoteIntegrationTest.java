package com.ydg.geonotes.entegrasyon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional; // Eklendi

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Testlerin birbirini etkilememesi ve temiz kalması için eklendi
public class NoteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Test başlamadan önce veritabanını temizle
        noteRepository.deleteAll();
    }

    @Test
    public void testCreateNoteAndFetchFromDatabase() throws Exception {
        // 1. Yeni bir not oluştur
        Note note = new Note();
        note.setTitle("Entegrasyon Testi Notu");
        note.setContent("Bu içerik DB'ye kaydedilmeli");
        note.setUserId(1L);
        note.setFavorite(false); // KRİTİK DÜZELTME: Null hatasını önlemek için manuel set edildi

        // 2. API üzerinden kaydet (POST)
        mockMvc.perform(post("/api/notes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Entegrasyon Testi Notu"));

        // 3. API üzerinden kullanıcıya göre notları getir ve DB'den geldiğini doğrula (GET)
        mockMvc.perform(get("/api/notes/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Entegrasyon Testi Notu"))
                .andExpect(jsonPath("$[0].content").value("Bu içerik DB'ye kaydedilmeli"));
    }
}