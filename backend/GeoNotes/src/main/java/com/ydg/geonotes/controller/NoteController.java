package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/add")
    public ResponseEntity<Note> createNote(@RequestBody Note note){
        return ResponseEntity.ok(noteService.saveNote(note));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getNotes(@PathVariable Long userId){
        return ResponseEntity.ok(noteService.getAllNotesByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note noteDetails) {
        return ResponseEntity.ok(noteService.updateNote(id, noteDetails));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok("Not başarıyla silindi.");
    }

}
