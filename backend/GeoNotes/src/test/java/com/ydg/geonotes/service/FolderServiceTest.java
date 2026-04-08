package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Folder;
import com.ydg.geonotes.repository.FolderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FolderServiceTest {

    @Mock
    private FolderRepository folderRepository;

    @InjectMocks
    private FolderService folderService;

    @Test
    public void testGetFoldersByUserId() {
        Folder f1 = new Folder(); f1.setName("İş");
        Folder f2 = new Folder(); f2.setName("Okul");

        when(folderRepository.findByUserId(1L)).thenReturn(Arrays.asList(f1, f2));

        List<Folder> result = folderService.getFoldersByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("İş", result.get(0).getName());
    }

    @Test
    @DisplayName("Yeni klasör başarıyla kaydedilmeli")
    public void testSaveFolder() {
        // Arrange
        Folder folder = new Folder();
        folder.setName("Kişisel");
        folder.setUserId(1L);
        when(folderRepository.save(any(Folder.class))).thenReturn(folder);

        // Act
        Folder savedFolder = folderService.saveFolder(folder);

        // Assert
        assertNotNull(savedFolder);
        assertEquals("Kişisel", savedFolder.getName());
        verify(folderRepository, times(1)).save(folder);
    }

    @Test
    @DisplayName("Klasör ID ile başarıyla silinmeli")
    public void testDeleteFolder() {
        // Arrange
        Long folderId = 10L;
        doNothing().when(folderRepository).deleteById(folderId);

        // Act
        folderService.deleteFolder(folderId);

        // Assert
        verify(folderRepository, times(1)).deleteById(folderId);
    }

}