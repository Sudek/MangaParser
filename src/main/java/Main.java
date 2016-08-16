import java.io.IOException;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    Mangafox mf = new Mangafox();
    ArrayList<String> mangafoxNameURL = new ArrayList<String>();
    try {
      mangafoxNameURL = mf.parse();
    } catch (IOException e) {
      e.printStackTrace();
    }
    mf.parseURL(mangafoxNameURL);
  }
}