import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * A singleton {@link BufferedReader} instance that reads from {@link System#in}.
 */
public class SystemInReader extends BufferedReader {

  private static final SystemInReader INSTANCE =
      new SystemInReader(new InputStreamReader(System.in));

  private SystemInReader(InputStreamReader streamReader) {
    super(streamReader);
  }

  /**
   * Gets the singleton instance.
   * @return The singleton instance.
   */
  public static SystemInReader getInstance() {
    return INSTANCE;
  }

}
