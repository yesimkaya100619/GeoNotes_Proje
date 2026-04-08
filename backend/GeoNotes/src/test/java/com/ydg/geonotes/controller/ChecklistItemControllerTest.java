package com.ydg.geonotes.controller;

import com.ydg.geonotes.service.ChecklistItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChecklistItemController.class)
class ChecklistItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChecklistItemService service;

    @Test
    void testGetItems() throws Exception {
        when(service.getItemsByNoteId(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/checklist/note/1"))
                .andExpect(status().isOk());
    }
}
