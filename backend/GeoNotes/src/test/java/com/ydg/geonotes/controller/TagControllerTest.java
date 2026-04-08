package com.ydg.geonotes.controller;

import com.ydg.geonotes.service.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Test
    @DisplayName("Nota başarıyla yeni bir etiket eklenebilmeli")
    void shouldAddTagToNoteSuccessfully() throws Exception {
        // Hazırlık
        Long noteId = 1L;
        String tagName = "Seyahat";

        // void dönen metotlar için doNothing kullanımı
        doNothing().when(tagService).addTagToNote(noteId, tagName);

        // İstek: /api/tags/add/1?name=Seyahat
        mockMvc.perform(post("/api/tags/add/{noteId}", noteId)
                        .param("name", tagName))
                .andExpect(status().isOk());
    }
}