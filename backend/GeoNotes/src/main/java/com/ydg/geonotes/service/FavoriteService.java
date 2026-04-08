package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {

    @Autowired
    private NoteRepository noteRepository;

    @Transactional
    public boolean toggleFavorite(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Not bulunamadı!"));

        note.setFavorite(!note.isFavorite());
        noteRepository.save(note);
        return note.isFavorite();
    }

}
