package com.ydg.geonotes.entegrasyon;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.ydg.geonotes.entity.ChecklistItem;
import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import com.ydg.geonotes.repository.ChecklistItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ChecklistItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ChecklistItemRepository checklistRepository;

    @Test
    void shouldCreateAndGetChecklistItem() throws Exception {
        // 1. Note oluştur
        Note note = new Note();
        note.setTitle("Yapılacaklar");
        note = noteRepository.save(note);

        // 2. Checklist item oluştur
        ChecklistItem item = new ChecklistItem();
        item.setItemText("Flutter Çalış");
        item.setIsDone(true);
        item.setNote(note);
        checklistRepository.save(item);

        // 3. Test et (Endpoint'in /api/checklist/note/{id} olduğunu varsayıyoruz)
        mockMvc.perform(get("/api/checklist/note/{noteId}", note.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemText").value("Flutter Çalış"))
                .andExpect(jsonPath("$[0].isDone").value(true));
    }

}
