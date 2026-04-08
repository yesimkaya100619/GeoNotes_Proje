package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.Reminder;
import com.ydg.geonotes.service.ReminderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReminderController.class)
public class ReminderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReminderService reminderService;

    @Test
    @DisplayName("Not için başarıyla hatırlatıcı kurulabilmeli")
    void shouldSetReminderSuccessfully() throws Exception {
        // Hazırlık
        Long noteId = 1L;
        LocalDateTime reminderDate = LocalDateTime.of(2025, 12, 30, 10, 0);

        Reminder reminder = new Reminder();
        reminder.setId(100L);
        reminder.setReminderDate(reminderDate);
        reminder.setCompleted(false);

        when(reminderService.createOrUpdateReminder(eq(noteId), any(LocalDateTime.class)))
                .thenReturn(reminder);

        // İstek: ISO formatında tarih gönderimi (2025-12-30T10:00:00)
        mockMvc.perform(post("/api/reminders/{noteId}", noteId)
                        .param("date", "2025-12-30T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    @DisplayName("Hatırlatıcı başarıyla silinebilmeli")
    void shouldRemoveReminderSuccessfully() throws Exception {
        Long noteId = 1L;
        doNothing().when(reminderService).deleteReminder(noteId);

        // Controller ResponseEntity.noContent() döndüğü için 204 bekliyoruz
        mockMvc.perform(delete("/api/reminders/{noteId}", noteId))
                .andExpect(status().isNoContent());
    }
}