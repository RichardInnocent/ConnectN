import java.io.IOException;

public interface IOHandler extends AutoCloseable {

  void print(String message);
  void printLine(String message);
  void printLine();
  void printf(String format, Object... arguments);

  String readLine() throws IOException;

}
