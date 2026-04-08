package com.ydg.geonotes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ydg.geonotes.entity.Location;
import com.ydg.geonotes.entity.Note;
import com.ydg.geonotes.repository.LocationRepository;
import com.ydg.geonotes.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldReturnLocationForNote() throws Exception{
        Note note= new Note();
        note.setTitle("Konum Test Notu");
        note= noteRepository.save(note);

        Location loc= new Location();
        loc.setLatitude(39.9334);
        loc.setLongitude(32.8597);
        loc.setAddressName("Ankara");
        loc.setNote(note);
        locationRepository.save(loc);

        mockMvc.perform(get("/api/locations/note/{noteId}", note.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(39.9334))
                .andExpect(jsonPath("$.addressName").value("Ankara"));
    }
}

