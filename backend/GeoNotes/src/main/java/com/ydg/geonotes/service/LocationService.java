package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Location;
import com.ydg.geonotes.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

  @Autowired
    private LocationRepository locationRepository;

  public Location getLocationByNoteId(Long noteId){
      return locationRepository.findByNoteId(noteId);
  }

  @Transactional
    public Location saveLocation(Location location){
      return locationRepository.save(location);
  }

  @Transactional
  public void deleteLocation(Long id){
      locationRepository.deleteById(id);
  }
}
