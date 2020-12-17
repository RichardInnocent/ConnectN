import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

/**
 * Basic I/O handler implementation, whereby a single input and output source are used.
 */
public class SingleSourceIOHandler implements IOHandler {

  private final PrintStream outputStream;
  private final BufferedReader inputReader;

  /**
   * Creates a new I/O handler whereby a single input and output source are used.
   * @param inputStream The input stream.
   * @param outputStream The output stream.
   */
  public SingleSourceIOHandler(InputStream inputStream, OutputStream outputStream) {
    this(new BufferedReader(new InputStreamReader(inputStream)), new PrintStream(outputStream));
  }

  /**
   * Creates a new I/O handler whereby a single input and output source are used.
   * @param inputReader The input stream.
   * @param outputStream The output stream.
   * @throws NullPointerException Thrown if {@code inputReader == null} or
   * {@code outputStream == null}.
   */
  public SingleSourceIOHandler(BufferedReader inputReader, PrintStream outputStream)
      throws NullPointerException {
    this.inputReader = Objects.requireNonNull(inputReader, "Input reader is null");
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
  public String readLine() throws IOException {
    return inputReader.readLine();
  }

  @Override
  public void close() throws IOException {
    // Close the I/O sources
    inputReader.close();
    outputStream.close();
  }
}
