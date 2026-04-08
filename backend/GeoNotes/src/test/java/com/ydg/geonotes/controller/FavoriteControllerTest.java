package com.ydg.geonotes.controller;

import com.ydg.geonotes.controller.FavoriteController;
import com.ydg.geonotes.service.FavoriteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService favoriteService;

    @Test
    @DisplayName("Toggle Favorite başarılı bir şekilde boolean dönmeli")
    void shouldToggleFavoriteSuccessfully() throws Exception {
        Long noteId = 1L;

        when(favoriteService.toggleFavorite(noteId))
                .thenReturn(true);

        mockMvc.perform(post("/api/favorites/{noteId}", noteId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
