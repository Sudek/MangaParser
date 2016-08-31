package com.mangatracker.core;


import com.mangatracker.core.models.Manga;

import java.util.List;

public class MangaListResponse {
    private List<Manga> items;

    public MangaListResponse() {
    }

    public MangaListResponse(List<Manga> items) {
        this.items = items;
    }

    public List<Manga> getItems() {
        return items;
    }

    public void setItems(List<Manga> items) {
        this.items = items;
    }
}
