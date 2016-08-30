import com.google.firebase.*;
import com.google.firebase.database.*;
import java.io.*;
import java.util.*;

public class FirebaseClient {

  public void connect() {
    FirebaseOptions options = null;
    try {
      options = new FirebaseOptions.Builder()
          //set up db address
          .setDatabaseUrl("https://manga-581fd.firebaseio.com")
          //set up address to private key
          .setServiceAccount(new FileInputStream
              ("src/main/resources/mangatrackexample-aa713295d062.json"))
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    FirebaseApp.initializeApp(options);
  }

  public void setManga() {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference manga = database.getReference("/manga_list");
    Date dateLastUpdate = new Date();
    Date dateReleased = new Date();
    Map<String, Manga> mangaMap = new HashMap<String, Manga>();
    mangaMap.put("Manga14", new Manga("471", "Zoka", "http:asd.com/sd", 10, dateLastUpdate,
        dateReleased, false));
    manga.setValue(mangaMap);
  }
}
