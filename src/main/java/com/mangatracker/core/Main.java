package com.mangatracker.core;

import com.qmetric.spark.authentication.AuthenticationDetails;
import com.qmetric.spark.authentication.BasicAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//        mangaManager.getLatest("mangafox");

        post("/odmin/fetch/:source", ((request, response) -> {
            String source = request.queryParams("source");
            String ipAddress = request.queryParams("ip");
            String port = request.queryParams("port");
            String startTitle = request.queryParams("startTitle");
            String endTitle = request.queryParams("endTitle");
            logger.info("source - " + source);
            logger.info("ip - " + ipAddress);
            logger.info("port - " + port);
            logger.info("startTitle - " + startTitle);
            logger.info("endTitle - " + endTitle);
            mangaManager.getLatest(source, ipAddress, port, Integer.parseInt(startTitle), Integer.parseInt(endTitle));
            response.status(200);
            return "ok";
        }));
    }

    private static MangaListResponse buildMangaList() {
        return new MangaListResponse(mangaManager.getMangaList());
    }

}