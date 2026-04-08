package com.ydg.geonotes.entity;

public enum SyncState {

    SYNCED,      // Sunucuyla eşleşti
    PENDING,     // Yerelde değişiklik var, gönderilmeyi bekliyor
    CONFLICT,    // Çakışma var
    FAILED       // Senkronizasyon hatası

}
