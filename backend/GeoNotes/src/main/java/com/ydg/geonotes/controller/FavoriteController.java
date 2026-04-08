package com.ydg.geonotes.controller;

import com.ydg.geonotes.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/{noteId}")
    public ResponseEntity<Boolean> toggleFavorite(@PathVariable Long noteId) {
        return ResponseEntity.ok(favoriteService.toggleFavorite(noteId));
    }

}
