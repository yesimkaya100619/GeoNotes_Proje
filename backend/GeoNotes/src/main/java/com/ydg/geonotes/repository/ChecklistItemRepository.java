package com.ydg.geonotes.repository;

import com.ydg.geonotes.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem,Long> {
    List<ChecklistItem> findByNoteId(Long noteId);
}
