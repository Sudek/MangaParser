import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.slf4j.*;
import java.io.IOException;

public class Mangafox {
  private static final String ADDRESS = "http://mangafox.me/manga/";
  Logger logger = LoggerFactory.getLogger(Mangafox.class);

  public void parse () {
    Document mangafoxDoc = null;
    try {
      mangafoxDoc = Jsoup.connect(ADDRESS).get();
    } catch (IOException e) {
      e.printStackTrace();
    } if(mangafoxDoc != null) {
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
