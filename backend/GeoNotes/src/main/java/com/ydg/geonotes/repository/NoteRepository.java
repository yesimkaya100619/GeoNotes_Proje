package com.ydg.geonotes.repository;

import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.entity.SyncState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);

    List<Note> findBySyncState(SyncState syncState);

    List<Note> findByIsFavoriteTrue();
}
