import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Displays information to the user, using a single output source.
 */
public class SingleSourceView implements View {

  private final PrintStream outputStream;

  /**
   * Creates a new view whereby a single output source is used.
   * @param outputStream The output stream.
   * @throws NullPointerException Thrown if {@code outputStream == null}.
   */
  public SingleSourceView(OutputStream outputStream) throws NullPointerException {
    this(new PrintStream(outputStream));
  }

  /**
   * Creates a new view whereby a single output source is used.
   * @param outputStream The output stream.
   * @throws NullPointerException Thrown if {@code inputReader == null} or
   * {@code outputStream == null}.
   */
  public SingleSourceView(PrintStream outputStream) throws NullPointerException {
    this.outputStream = Objects.requireNonNull(outputStream, "Output stream is null");
  }

  @Override
  public void send(String message) {
    outputStream.print(message);
  }

  @Override
  public void sendLine(String message) {
    outputStream.println(message);
  }

  @Override
  public void sendLine() {
    outputStream.println();
  }

  @Override
  public void sendf(String format, Object... arguments) {
    outputStream.printf(format, arguments);
  }

  @Override
  public void close() {
    // Close the I/O sources
    outputStream.close();
  }
}
