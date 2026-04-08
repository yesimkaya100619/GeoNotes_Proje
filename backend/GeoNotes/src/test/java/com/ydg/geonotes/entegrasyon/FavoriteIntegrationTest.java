package com.ydg.geonotes.entegrasyon;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc'yi kullanabilmek için gerekli
@Transactional
public class FavoriteIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // HTTP isteklerini simüle eder

    @Autowired
    private NoteRepository noteRepository;

    @Test
    void testFavoritePersistenceViaController() throws Exception {
        // 1. Veritabanına bir not kaydet
        Note note = new Note();
        note.setTitle("Favori Testi");
        note.setFavorite(false);
        note = noteRepository.save(note);

        // 2. Controller üzerinden (URL yoluyla) isteği gönder
        // Bu adım JaCoCo'daki "FavoriteController" satırını yeşile çevirir
        mockMvc.perform(post("/api/favorites/{noteId}", note.getId()))
                .andExpect(status().isOk());

        // 3. Veritabanından durumu tekrar kontrol et
        Note updatedNote = noteRepository.findById(note.getId()).get();
        assertTrue(updatedNote.isFavorite());
    }
}