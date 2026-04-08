package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.NotePhoto;
import com.ydg.geonotes.service.NotePhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class NotePhotoController {

    @Autowired
    private NotePhotoService notePhotoService;

    @PostMapping("/add")
    public ResponseEntity<NotePhoto> addPhoto(@RequestBody NotePhoto photo) {
        return ResponseEntity.ok(notePhotoService.savePhoto(photo));
    }

    @GetMapping("/note/{noteId}")
    public ResponseEntity<List<NotePhoto>> getPhotosByNote(@PathVariable Long noteId) {
        return ResponseEntity.ok(notePhotoService.getPhotosByNoteId(noteId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        notePhotoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }

}
