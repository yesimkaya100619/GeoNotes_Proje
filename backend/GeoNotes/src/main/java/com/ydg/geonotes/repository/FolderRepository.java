package com.ydg.geonotes.repository;

import com.ydg.geonotes.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder,Long> {
    List<Folder> findByUserId(Long userId);
    Folder save(Folder folder);
    void deleteById(Long folderId);
    void deleteAll();

}
