package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.Location;
import com.ydg.geonotes.repository.LocationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    @DisplayName("Not ID'sine göre konum başarıyla getirilmeli")
    void whenGetLocationByNoteId_shouldReturnLocation() {
        // Arrange
        Long noteId = 1L;
        Location location = new Location();
        location.setLatitude(41.0082);
        location.setLongitude(28.9784);
        location.setAddressName("Eskişehir, Türkiye");

        when(locationRepository.findByNoteId(noteId)).thenReturn(location);

        // Act
        Location found = locationService.getLocationByNoteId(noteId);

        // Assert
        assertNotNull(found);
        assertEquals(41.0082, found.getLatitude());
        assertEquals("Eskişehir, Türkiye", found.getAddressName());
        verify(locationRepository, times(1)).findByNoteId(noteId);
    }

    @Test
    @DisplayName("Yeni konum başarıyla kaydedilmeli")
    void whenSaveLocation_shouldReturnSavedLocation() {
        // Arrange
        Location location = new Location();
        location.setLatitude(39.7767);
        location.setLongitude(30.5206);
        location.setAddressName("Anadolu Üniversitesi");

        when(locationRepository.save(any(Location.class))).thenReturn(location);

        // Act
        Location saved = locationService.saveLocation(location);

        // Assert
        assertNotNull(saved);
        assertEquals("Anadolu Üniversitesi", saved.getAddressName());
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    @DisplayName("Konum ID ile başarıyla silinmeli")
    void whenDeleteLocation_shouldInvokeRepositoryDelete() {
        // Arrange
        Long locationId = 10L;
        // void dönen metotlar için doNothing kullanımı opsiyoneldir, verify asıl kanıttır.
        doNothing().when(locationRepository).deleteById(locationId);

        // Act
        locationService.deleteLocation(locationId);

        // Assert
        verify(locationRepository, times(1)).deleteById(locationId);
    }
}