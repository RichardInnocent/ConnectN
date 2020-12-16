import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

public class SingleSourceIOHandler implements IOHandler {

  private final PrintStream outputStream;
  private final BufferedReader inputReader;

  public SingleSourceIOHandler(InputStream inputStream, OutputStream outputStream) {
    this(new BufferedReader(new InputStreamReader(inputStream)), new PrintStream(outputStream));
  }

  public SingleSourceIOHandler(BufferedReader inputReader, PrintStream outputStream) {
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

  public String readLine() throws IOException {
    return inputReader.readLine();
  }

  @Override
  public void close() throws IOException {
    inputReader.close();
    outputStream.close();
  }
}
