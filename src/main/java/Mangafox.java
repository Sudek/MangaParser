import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.slf4j.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Mangafox {

  private static final String ADDRESS = "http://mangafox.me/manga/";
  Logger logger = LoggerFactory.getLogger(Mangafox.class);

  public ArrayList<String> parse() throws IOException {

    //Cache cache = new Cache(new File(args[0]), cacheByteCount);
    OkHttpClient client = new OkHttpClient.Builder()
            //.cache(cache)
            .connectTimeout(5, TimeUnit.MINUTES)
            .build();

    Request request = new Request.Builder()
            .url(ADDRESS)
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
    ArrayList<String> mangafoxURL = new ArrayList<String>();
    try {
      mangafoxDoc = Jsoup.parse(response.body().string(), ADDRESS);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (mangafoxDoc != null) {
      Elements mangafoxLinks = mangafoxDoc
          .getElementsByClass("manga_list")
          .select("li")
          .select("a[href]");
      for (Element link : mangafoxLinks) {
        mangafoxURL.add(link.attr("href"));
      }
    }
    return mangafoxURL;
  }

  public void parseURL(ArrayList<String> urlList) {
    Document mangaDoc = null;
    for (String anUrlList : urlList) {
      try {
        mangaDoc = Jsoup.connect(anUrlList).get();
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

        for (Element image : images) {
          logger.info("Name - " + mangaName.text());
          logger.info("Cover URL - " + image.attr("src"));
          logger.info("Release date - " + releaseDate.text());
          logger.info("Status - " + status.text());
          logger.info("Last chapter update - " + lastChapterUpdate.text());
          logger.info("Last chapter name - " + lastChapterName.text());
          logger.info("--------------------------------------------------------------------------");
        }
      }
    }
  }
}
