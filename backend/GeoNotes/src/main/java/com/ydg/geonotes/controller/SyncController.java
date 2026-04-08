package com.ydg.geonotes.controller;

import com.ydg.geonotes.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class SyncController {
    @Autowired
    private SyncService syncService;

    @PostMapping("/confirm/{noteId}")
    public ResponseEntity<String> confirm(@PathVariable Long noteId) {
        syncService.confirmSync(noteId);
        return ResponseEntity.ok("Senkronizasyon onaylandı.");
    }

    @PostMapping("/pending/{noteId}")
    public ResponseEntity<String> markPending(@PathVariable Long noteId) {
        syncService.markAsPending(noteId);
        return ResponseEntity.ok("Durum PENDING olarak güncellendi.");
    }

}
