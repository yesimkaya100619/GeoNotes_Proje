package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.ChecklistItem;
import com.ydg.geonotes.service.ChecklistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklist")
public class ChecklistItemController {

    @Autowired
    private ChecklistItemService service;

    @GetMapping("/note/{noteId}")
    public ResponseEntity<List<ChecklistItem>> getByNote(@PathVariable Long noteId) {
        return ResponseEntity.ok(service.getItemsByNoteId(noteId));
    }

    @PostMapping("/add")
    public ResponseEntity<ChecklistItem> add(@RequestBody ChecklistItem item) {
        return ResponseEntity.ok(service.saveItem(item));
    }

}
