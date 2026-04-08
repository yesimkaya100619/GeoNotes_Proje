package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Folder;
import com.ydg.geonotes.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    @Autowired
    private FolderRepository folderRepository;

    // Yeni klasör oluşturur veya mevcut olanı günceller
    public Folder saveFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    // Kullanıcı ID'sine göre klasörleri getirir
    public List<Folder> getFoldersByUserId(Long userId) {
        return folderRepository.findByUserId(userId);
    }

    // Klasör silme (Opsiyonel - İhtiyacın olabilir)
    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }
}