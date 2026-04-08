package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.Tag;
import com.ydg.geonotes.repository.NoteRepository;
import com.ydg.geonotes.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService {
    @Autowired private TagRepository tagRepository;
    @Autowired private NoteRepository noteRepository;

    @Transactional
    public void addTagToNote(Long noteId, String tagName) {
        Note note = noteRepository.findById(noteId).orElseThrow();

        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));

        note.getTags().add(tag);
        noteRepository.save(note);
    }
}