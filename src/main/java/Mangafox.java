import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.slf4j.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Mangafox {

  private static final String ADDRESS = "http://mangafox.me/manga/";
  Logger logger = LoggerFactory.getLogger(Mangafox.class);

  public void parse() throws IOException {

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
      return;
    }

    MediaType mediaType = MediaType.parse(contentType);
    if (mediaType == null || !mediaType.subtype().equalsIgnoreCase("html")) {
      response.body().close();
      return;
    }

    Document mangafoxDoc = null;
    try {
      mangafoxDoc = Jsoup.parse(response.body().string(), ADDRESS);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (mangafoxDoc != null) {
      Elements mangafoxElementsByClass = mangafoxDoc.getElementsByClass("manga_list");
      Elements mangafoxElemLi = mangafoxElementsByClass.select("li");
      Elements mangafoxLinks = mangafoxElemLi.select("a[href]");
      for (Element link : mangafoxLinks) {
        logger.info("Link - " + link.attr("href"));
        logger.info("Name - " + link.text());
      }
    }
  }
}
