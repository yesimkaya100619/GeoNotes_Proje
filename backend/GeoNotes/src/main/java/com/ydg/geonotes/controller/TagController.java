package com.ydg.geonotes.controller;


import com.ydg.geonotes.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/add/{noteId}")
    public ResponseEntity<Void> addTag(@PathVariable Long noteId, @RequestParam String name) {
        tagService.addTagToNote(noteId, name);
        return ResponseEntity.ok().build();
    }
}