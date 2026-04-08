package com.ydg.geonotes.controller;

import com.ydg.geonotes.entity.Location;
import com.ydg.geonotes.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/note/{noteId}")
    public ResponseEntity<Location> getLocation(@PathVariable Long noteId) {
        Location location = locationService.getLocationByNoteId(noteId);
        if (location == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(location);
    }

    @PostMapping("/add")
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.saveLocation(location));
    }

    @DeleteMapping("/{id}") // Burası /{id} olmalı
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok().build();
    }

}