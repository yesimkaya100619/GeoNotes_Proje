package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.SyncState;
import com.ydg.geonotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SyncService {

    @Autowired
    private NoteRepository noteRepository;

    // Not güncellendiğinde durumu PENDING yap
    @Transactional
    public void markAsPending(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow();
        note.setSyncState(SyncState.PENDING);
        note.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(note);
    }

    // Başarılı senkronizasyon sonrası durumu SYNCED yap
    @Transactional
    public void confirmSync(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow();
        note.setSyncState(SyncState.SYNCED);
        noteRepository.save(note);
    }

    }
