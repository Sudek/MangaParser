import com.google.firebase.*;
import com.google.firebase.database.*;
import java.io.*;

public class FirebaseClient {
  final FirebaseDatabase database = FirebaseDatabase.getInstance();
  private DatabaseReference manga;

  public void connect() {
    FirebaseOptions options = null;
    try {
      options = new FirebaseOptions.Builder()
          .setDatabaseUrl("https://manga-581fd.firebaseio.com") //set up address
          .setServiceAccount(new FileInputStream
              ("src/main/resources/mangatrackexample-aa713295d062.json"))
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    FirebaseApp.initializeApp(options);
  }

  public void setManga() {
    manga = database.getReference("/manga_list");

  }

}
