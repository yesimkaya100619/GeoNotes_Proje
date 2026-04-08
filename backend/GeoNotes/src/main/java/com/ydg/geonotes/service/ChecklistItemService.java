package com.ydg.geonotes.service;

import com.ydg.geonotes.entity.ChecklistItem;
import com.ydg.geonotes.repository.ChecklistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChecklistItemService {

    @Autowired
    private ChecklistItemRepository repository;

    public List<ChecklistItem> getItemsByNoteId(Long noteId) {
        return repository.findByNoteId(noteId);
    }

    @Transactional
    public ChecklistItem saveItem(ChecklistItem item) {
        return repository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public ChecklistItem toggleItemStatus(Long id) {
        // repository.findById artık doğru tipi (Optional<ChecklistItem>) dönecektir
        ChecklistItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item bulunamadı"));

        // Entity'deki isIsDone isimlendirmesine göre güncelledik
        item.setIsDone(!item.isIsDone());

        return repository.save(item);
    }

}
