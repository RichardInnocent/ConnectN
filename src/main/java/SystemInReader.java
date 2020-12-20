import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SystemInReader extends BufferedReader {

  private static final SystemInReader INSTANCE =
      new SystemInReader(new InputStreamReader(System.in));

  private SystemInReader(InputStreamReader streamReader) {
    super(streamReader);
  }

  public static SystemInReader getInstance() {
    return INSTANCE;
  }

}
