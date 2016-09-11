package com.mangatracker.core.sources;

import com.mangatracker.core.models.Manga;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mangafox implements Source {
    public static final String SOURCE_NAME = "mangafox";
    private static final String BASE_URL = "http://mangafox.me/manga/";

    private Logger logger = LoggerFactory.getLogger(Mangafox.class);
    private OkHttpClient client;
    private SourceCallback callback;

    /**
     * Fetch all available titles in the current source and convert it to Strings
     * @return List of titles
     * @throws IOException
     * 10.09.2016 Total titles - 17392
     */
    private List<String> parseAllTitles(int startTitle, int endTitle) throws IOException {
        logger.debug("parseAllTitles");
        if (client != null) {
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .build();
            Response response = client.newCall(request).execute();

            int responseCode = response.code();

            String contentType = response.header("Content-Type");
            if (responseCode != 200 || contentType == null) {
                response.body().close();
            }

            MediaType mediaType = MediaType.parse(contentType);
            if (mediaType == null || !mediaType.subtype().equalsIgnoreCase("html")) {
                response.body().close();
            }

            Document mangafoxDoc = null;
            List<String> mangafoxURLs = new ArrayList<>();
            try {
                mangafoxDoc = Jsoup.parse(response.body().string(), BASE_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mangafoxDoc != null) {
                Elements mangafoxLinks = mangafoxDoc
                        .getElementsByClass("manga_list")
                        .select("li")
                        .select("a[href]");
                // for test purposes parseAllTitles only 10 manga url
                int counter = 0;
                for (Element link : mangafoxLinks) {
                    mangafoxURLs.add(link.attr("href"));
                    counter++;
                    if (counter == 10) break;
                }
            }
            return mangafoxURLs.subList(startTitle, endTitle);
        }
        return null;
    }

    /**
     * Parse list of manga urls.
     * Heavy
     * @param urlList List of manga titles
     */
    private List<Manga> parseURLs(List<String> urlList) throws IOException {
        logger.debug("parseURLs");

        List<Manga> list = new ArrayList<>();

        Document mangaDoc = null;

        for (String anUrlList : urlList) {
            try {
                Request request = new Request.Builder()
                        .url(anUrlList)
                        .build();
                Response response = client.newCall(request).execute();
                mangaDoc = Jsoup.parse(response.body().string(), anUrlList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mangaDoc != null) {

                Elements mangaName = mangaDoc
                        .select("div#chapters")
                        .select("h2")
                        .select("a");

                Elements mangaCover = mangaDoc.getElementsByClass("cover");
                Elements images = mangaCover.select("img[src~=(?i)\\.(png|jpe?g|gif)]");

                Elements mangaReleased = mangaDoc
                        .getElementsByTag("table")
                        .select("tr")
                        .select("td")
                        .select("a");
                Element releaseDate = mangaReleased.first();

                Elements mangaCompleted = mangaDoc
                        .getElementsByClass("data")
                        .select("span");
                Element status = mangaCompleted.first();

                Elements mangaLastChapterDate = mangaDoc
                        .getElementsByClass("date");
                Element lastChapterUpdate = mangaLastChapterDate.first();

                Elements mangaLlastChapterName = mangaDoc
                        .getElementsByClass("tips");
                Element lastChapterName = mangaLlastChapterName.first();

                if (mangaLastChapterDate.size() > 0 && mangaLlastChapterName.size() > 0) {
                    for (Element image : images) {
                        String title = mangaName.text().replaceAll("\\[|\\]|\\.|\\#|\\$", "");

                        logger.info("Name - " + title);
                        logger.info("Cover URL - " + image.attr("src"));
                        logger.info("Release date - " + releaseDate.text());
                        logger.info("Status - " + status.text());
                        logger.info("Last chapter update - " + lastChapterUpdate.text());
                        logger.info("Last chapter name - " + lastChapterName.text());
                        logger.info("-----------------------------------------------------------------------");


                        if (title != null && title.length() > 0) {
                            Manga manga = new Manga();
                            manga.setTitle(title);
                            manga.setThumbnailUrl(image.attr("src"));
                            manga.setLastUpdate(System.currentTimeMillis());
                            list.add(manga);
                        }
                    }
                }
            }
        }
        return list;
    }


    @Override
    public String getSourceName() {
        return SOURCE_NAME;
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public void pullLatestUpdatesFromNetwork(int startTitle, int endTitle) {
        try {
            callback.loaded(parseURLs(parseAllTitles(startTitle, endTitle)), SOURCE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOkHttpClient(OkHttpClient client) {
        this.client = client;
    }

    public void setCallback(SourceCallback callback) {
        this.callback = callback;
    }
}
