package com.mangatracker.core.sources;


import com.mangatracker.core.models.Manga;

import java.util.List;

public interface SourceCallback {
    void loaded(List<Manga> list, String source);
}
