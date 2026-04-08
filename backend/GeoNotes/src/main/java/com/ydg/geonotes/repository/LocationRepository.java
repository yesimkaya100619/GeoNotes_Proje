package com.ydg.geonotes.repository;

import com.ydg.geonotes.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
   Location findByNoteId(Long noteId);
}
