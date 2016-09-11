package com.mangatracker.core.sources;


public interface Source {
    String getSourceName();
    String getBaseUrl();
    void pullLatestUpdatesFromNetwork(int startTitle, int endTitle);

//
//    Observable<String> getInitialUpdateUrl();
//
//    Observable<List<String>> getGenres();
//
//    Observable<Manga> pullMangaFromNetwork(RequestWrapper request);
//
//    Observable<List<Chapter>> pullChaptersFromNetwork(RequestWrapper request);
//
//    Observable<String> pullImageUrlsFromNetwork(RequestWrapper request);
//
//    Observable<String> recursivelyConstructDatabase(String url);
}
