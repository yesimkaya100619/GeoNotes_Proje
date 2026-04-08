package com.ydg.geonotes.entegrasyon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydg.geonotes.entity.Folder;
import com.ydg.geonotes.repository.FolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Test verilerinin her testten sonra temizlenmesini sağlar
public class FolderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        folderRepository.deleteAll();
    }

    @Test
    @DisplayName("Klasör başarıyla oluşturulmalı ve kullanıcıya göre listelenmeli")
    public void testCreateFolderAndListByUser() throws Exception {
        Folder folder = new Folder();
        folder.setName("Seyahat");
        folder.setUserId(99L);

        // 1. Klasör Oluşturma (POST)
        mockMvc.perform(post("/api/folders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(folder)))
                .andExpect(status().isOk());

        // 2. Kullanıcıya Göre Listeleme (GET)
        mockMvc.perform(get("/api/folders/user/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Seyahat"));
    }
    @Test
    @DisplayName("Klasör ID ile başarıyla silinebilmeli")
    public void testDeleteFolderIntegration() throws Exception {
        // 1. Arrange
        Folder folder = new Folder();
        folder.setName("Silinecek Klasör");
        folder.setUserId(1L);
        Folder savedFolder = folderRepository.save(folder);

        // 2. Act
        mockMvc.perform(delete("/api/folders/{id}", savedFolder.getId()))
                .andExpect(status().isNoContent()); // 200 yerine 204 bekliyoruz

        // 3. Assert
        boolean exists = folderRepository.existsById(savedFolder.getId());
        assertFalse(exists);
    }
}