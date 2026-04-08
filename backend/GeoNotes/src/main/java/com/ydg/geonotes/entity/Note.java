package com.ydg.geonotes.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long folderId;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_favorite", nullable = false)
    private boolean isFavorite = false;

    @Enumerated(EnumType.STRING)
    @Column(name="sync_state")
    private SyncState syncState = SyncState.SYNCED; // Varsayılan değer

    // Note.java içerisine eklenecekler:

    @OneToOne(mappedBy = "note", cascade = CascadeType.ALL)
    private Location location;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<NotePhoto> photos;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<ChecklistItem> checklistItems;

    private LocalDateTime updatedAt; // String yerine LocalDateTime daha sağlıklıdır

    public Note() {
    }

    public Note(Long id, Long userId, Long folderId, String title, String content, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.folderId = folderId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
        this.isFavorite = false; // Bunu ekleyerek garantiye alıyoruz
        this.syncState = SyncState.SYNCED; // Bunu da eklemek iyi olur
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "note_tags",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }

    // Getter ve Setterlar

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public SyncState getSyncState() { return syncState; }
    public void setSyncState(SyncState syncState) { this.syncState = syncState; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFolderId() { return folderId; }
    public void setFolderId(Long folderId) { this.folderId = folderId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        if (location != null) {
            location.setNote(this); // Çift yönlü ilişkiyi kurar
        }
    }
}