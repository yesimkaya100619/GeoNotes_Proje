package com.ydg.geonotes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydg.geonotes.controller.FolderController;
import com.ydg.geonotes.entity.Folder;
import com.ydg.geonotes.service.FolderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FolderController.class)
class FolderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FolderService folderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateFolder() throws Exception {
        Folder folder = new Folder();
        folder.setId(1L);
        folder.setName("Yeni Klasör");

        when(folderService.saveFolder(any(Folder.class)))
                .thenReturn(folder);

        mockMvc.perform(post("/api/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(folder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Yeni Klasör"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldGetFoldersByUserId() throws Exception {
        Folder f1 = new Folder();
        f1.setName("Kişisel");

        when(folderService.getFoldersByUserId(1L))
                .thenReturn(Arrays.asList(f1));

        mockMvc.perform(get("/api/folders/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Kişisel"));
    }
}
