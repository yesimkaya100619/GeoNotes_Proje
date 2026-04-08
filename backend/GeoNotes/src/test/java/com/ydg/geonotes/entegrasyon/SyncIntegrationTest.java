package com.ydg.geonotes.entegrasyon;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.SyncState;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
public class SyncIntegrationTest {

    @Autowired
    private NoteRepository noteRepository;

    @Test
    @DisplayName("Veritabanına SyncState PENDING olarak kaydedilip geri okunabilmeli")
    void testDatabaseEnumPersistence() {
        // Given
        Note note = new Note();
        note.setTitle("Senkronizasyon Testi");
        note.setSyncState(SyncState.PENDING);

        // When
        Note savedNote = noteRepository.save(note);
        Note fetchedNote = noteRepository.findById(savedNote.getId()).orElseThrow();

        // Then
        assertThat(fetchedNote.getSyncState()).isEqualTo(SyncState.PENDING);
    }

}
