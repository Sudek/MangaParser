package com.mangatracker.core;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.mangatracker.core.models.Manga;
import com.mangatracker.core.sources.Mangafox;
import com.mangatracker.core.sources.SourceCallback;
import okhttp3.OkHttpClient;
import org.slf4j.*;
import org.slf4j.Logger;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MangaManager implements ValueEventListener, SourceCallback {
    private Logger logger = LoggerFactory.getLogger(MangaManager.class);

    private static MangaManager instance;
    private OkHttpClient client;
    private final DatabaseReference manga;

    /**
     * Firebase list of manga
     */
    private List<Manga> mangaList;

    public static MangaManager getInstance() {
        if (instance == null) {
            synchronized (MangaManager.class) {
                if (instance == null) {
                    instance = new MangaManager();
                }
            }
        }
        return instance;
    }

    private MangaManager() {
        // init firebase app
        //set up key address
        InputStream is = MangaManager.class.getResourceAsStream
            ("/keys/key-firebase.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(is)
                 //set up db address
                .setDatabaseUrl("https://manga-581fd.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        manga = firebaseDatabase.getReference("manga");
    }

    public void fetchMangaList() {
        manga.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        mangaList = new ArrayList<>();

        for (DataSnapshot snapshot : list) {
            mangaList.add(snapshot.getValue(Manga.class));
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
//        Log.e(TAG, "Failed to read value.", databaseError.toException());
    }


    public List<Manga> getMangaList() {
        return mangaList;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void getLatest(String source, String ipAddress, String port, int startTitle, int endTitle) {
        // set proxy
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipAddress, Integer.parseInt(port)));
        //Cache cache = new Cache(new File(args[0]), cacheByteCount);
        client = new OkHttpClient.Builder()
                .proxy(proxy)
                //.cache(cache)
                .connectTimeout(3, TimeUnit.MINUTES)
                .build();

        switch (source) {
            case Mangafox.SOURCE_NAME:

                Mangafox mf = new Mangafox();
                mf.setCallback(this);
                mf.setOkHttpClient(client);
                mf.pullLatestUpdatesFromNetwork(startTitle, endTitle);

                break;
        }
    }

    private void updateMangaDb(List<Manga> list) {
        for (Manga m : list) {
            manga.child(m.getTitle()).setValue(m);
        }
    }

    @Override
    public void loaded(List<Manga> list, String source) {
        updateMangaDb(list);
    }
}
