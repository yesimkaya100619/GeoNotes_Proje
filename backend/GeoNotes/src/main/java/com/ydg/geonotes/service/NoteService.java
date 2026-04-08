package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public Note saveNote(Note note){
        return noteRepository.save(note);
    }
    public List<Note> getAllNotesByUserId(Long userId){
        return noteRepository.findByUserId(userId);
    }

    // NoteService.java içine eklenecekler:

    public Note updateNote(Long id, Note noteDetails) {
        // Önce notun var olup olmadığını kontrol ediyoruz
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not bulunamadı: " + id));

        // Alanları güncelliyoruz
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        note.setFavorite(noteDetails.isFavorite());
        note.setSyncState(noteDetails.getSyncState());
        note.setUpdatedAt(java.time.LocalDateTime.now()); // Güncelleme zamanını damgalıyoruz

        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Silinecek not bulunamadı: " + id));
        noteRepository.delete(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

}
