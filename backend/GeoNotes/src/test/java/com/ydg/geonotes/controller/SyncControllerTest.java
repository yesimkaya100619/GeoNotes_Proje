package com.ydg.geonotes.controller;

import com.ydg.geonotes.service.SyncService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SyncController.class)
public class SyncControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SyncService syncService;

    @Test
    @DisplayName("Senkronizasyon başarıyla onaylanmalı")
    void shouldConfirmSyncSuccessfully() throws Exception {
        Long noteId = 1L;

        // Servis metodunun çağrıldığında hiçbir şey yapmamasını sağla (void dönen metotlar için)
        doNothing().when(syncService).confirmSync(noteId);

        mockMvc.perform(post("/api/sync/confirm/{noteId}", noteId))
                .andExpect(status().isOk())
                .andExpect(content().string("Senkronizasyon onaylandı."));
    }

    @Test
    @DisplayName("Durum başarıyla PENDING olarak güncellenmeli")
    void shouldMarkAsPendingSuccessfully() throws Exception {
        Long noteId = 1L;

        doNothing().when(syncService).markAsPending(noteId);

        mockMvc.perform(post("/api/sync/pending/{noteId}", noteId))
                .andExpect(status().isOk())
                .andExpect(content().string("Durum PENDING olarak güncellendi."));
    }
}