package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.Folder;
import com.ydg.geonotes.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @PostMapping
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder){
        // ResponseEntity.ok(...) kullandığın için dönüş tipi ResponseEntity<Folder> olmalı
        return ResponseEntity.ok(folderService.saveFolder(folder));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Folder>> getFoldersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(folderService.getFoldersByUserId(userId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        folderService.deleteFolder(id);
        return ResponseEntity.noContent().build();
    }

}
