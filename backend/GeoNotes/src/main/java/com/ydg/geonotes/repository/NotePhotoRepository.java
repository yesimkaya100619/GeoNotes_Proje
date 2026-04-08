package com.ydg.geonotes.repository;

import com.ydg.geonotes.entity.NotePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotePhotoRepository extends JpaRepository<NotePhoto,Long> {

    List<NotePhoto> findByNoteId(Long noteId);

}

