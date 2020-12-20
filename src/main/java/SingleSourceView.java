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
  public void print(String message) {
    outputStream.print(message);
  }

  @Override
  public void printLine(String message) {
    outputStream.println(message);
  }

  @Override
  public void printLine() {
    outputStream.println();
  }

  @Override
  public void printf(String format, Object... arguments) {
    outputStream.printf(format, arguments);
  }

  @Override
  public void close() {
    // Close the I/O sources
    outputStream.close();
  }
}
