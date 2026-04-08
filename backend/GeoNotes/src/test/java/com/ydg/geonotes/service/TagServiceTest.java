package com.ydg.geonotes.service;


import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.Tag;
import com.ydg.geonotes.repository.NoteRepository;
import com.ydg.geonotes.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @Mock private NoteRepository noteRepository;
    @InjectMocks
    private TagService tagService;

    @Test
    void shouldAddExistingTagToNote() {
        Note note = new Note(); note.setId(1L);
        Tag tag = new Tag("iş");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(tagRepository.findByName("iş")).thenReturn(Optional.of(tag));

        tagService.addTagToNote(1L, "iş");

        assertTrue(note.getTags().contains(tag));
        verify(noteRepository).save(note);
    }
}
