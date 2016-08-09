import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    Mangafox mf = new Mangafox();
    try {
      mf.parse();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}