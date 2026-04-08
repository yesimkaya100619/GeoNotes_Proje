package com.ydg.geonotes.entegrasyon;


import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.NoteRepository;
import com.ydg.geonotes.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TagIntegrationTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private NoteRepository noteRepository;

    @Test
    void testManyToManyRelationship() {
        Note note = noteRepository.save(new Note());

        tagService.addTagToNote(note.getId(), "seyahat");

        Note updatedNote = noteRepository.findById(note.getId()).get();
        assertThat(updatedNote.getTags()).hasSize(1);
        assertThat(updatedNote.getTags().iterator().next().getName()).isEqualTo("seyahat");
    }
}