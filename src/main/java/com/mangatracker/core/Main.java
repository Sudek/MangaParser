package com.mangatracker.core;

import com.qmetric.spark.authentication.*;
import org.slf4j.*;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import static spark.Spark.*;

public class Main {
  private static Logger logger = LoggerFactory.getLogger(Main.class);
  private static MangaManager mangaManager;

  public static void main(String[] args) {

    mangaManager = MangaManager.getInstance();

    before(new BasicAuthenticationFilter("/odmin/*", new AuthenticationDetails("foo", "bar")));
    //        Map<String, String> params = new HashMap<>();
    //        params.put("items", "asd");

    get("/odmin/dashboard", (request, response) -> new ModelAndView(buildMangaList(), "dashboard.mustache"),
        new MustacheTemplateEngine());

    post("/odmin/fetch/:source", ((request, response) -> {
      logger.debug("fetch " + request.queryParams("source"));
      mangaManager.getLatest(request.queryParams("source"));
      response.status(200);
      return "ok";
    }));
  }

  private static MangaListResponse buildMangaList() {
    return new MangaListResponse(mangaManager.getMangaList());
  }

  //
  //    public static void main(String[] args) {
  //        Mangafox mf = new Mangafox();
  //        ArrayList<String> mangafoxNameURL = new ArrayList<String>();
  //        try {
  //            mangafoxNameURL = mf.parse();
  //        } catch (IOException e) {
  //            e.printStackTrace();
  //        }
  //        mf.parseURL(mangafoxNameURL);
  //    }
}