package com.ydg.geonotes.entegrasyon;
import com.ydg.geonotes.entity.Location;
import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.LocationRepository;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// Statik importlar (Alt+Enter ile get ve jsonPath'i eklemeyi unutma)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LocationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Note savedNote;

    @BeforeEach
    void setUp() {
        Note note = new Note();
        note.setTitle("Test Notu");
        note.setContent("Konum testi için oluşturuldu.");
        note.setFavorite(false); // KRİTİK: Daha önceki hatayı önlemek için eklendi
        savedNote = noteRepository.save(note);
    }

    @Test
    void shouldReturnLocationWhenRequestedByNoteId() throws Exception {
        Location location = new Location();
        location.setLatitude(39.7767);
        location.setLongitude(30.5206);
        location.setAddressName("Eskişehir, Türkiye");
        location.setNote(savedNote);
        locationRepository.save(location);

        mockMvc.perform(get("/api/locations/note/{noteId}", savedNote.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(39.7767))
                .andExpect(jsonPath("$.addressName").value("Eskişehir, Türkiye"));
    }

    // YENİ: Silme işlemini test eden metot
    @Test
    void shouldDeleteLocation() throws Exception {
        // 1. Arrange: Önce bir konum oluştur
        Location location = new Location();
        location.setLatitude(40.0);
        location.setLongitude(30.0);
        location.setNote(savedNote);
        Location savedLoc = locationRepository.save(location);

        // 2. Act: Silme endpoint'ine istek at (Burada endpoint adının /api/locations/{id} olduğunu varsayıyorum)
        mockMvc.perform(delete("/api/locations/{id}", savedLoc.getId()))
                .andExpect(status().isOk());

        // 3. Assert: Veritabanında kalmadığını doğrula
        org.junit.jupiter.api.Assertions.assertFalse(locationRepository.existsById(savedLoc.getId()));
    }
}
