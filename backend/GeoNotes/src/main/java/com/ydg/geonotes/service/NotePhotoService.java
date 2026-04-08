package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.NotePhoto;
import com.ydg.geonotes.repository.NotePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotePhotoService {

    @Autowired
    private NotePhotoRepository notePhotoRepository;

    public NotePhoto savePhoto(NotePhoto photo) {
        return notePhotoRepository.save(photo);
    }

    public List<NotePhoto> getPhotosByNoteId(Long noteId) {
        return notePhotoRepository.findByNoteId(noteId);
    }

    public void deletePhoto(Long id) {
        notePhotoRepository.deleteById(id);
    }

}
