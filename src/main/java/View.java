/**
 * An interface that should be used when displaying information to the user.
 */
public interface View extends AutoCloseable {

  /**
   * Prints the message to the output stream, with no line break at the end.
   * @param message The message to print.
   */
  void print(String message);

  /**
   * Prints the message to the output stream, followed by a line break.
   * @param message The message to print.
   */
  void printLine(String message);

  /**
   * Prints a new line.
   */
  void printLine();

  /**
   * Prints the formatted message to the output stream.
   * @param format The message template.
   * @param arguments The arguments that are injected into the message.
   * @see java.io.PrintStream#printf(String, Object...) 
   */
  void printf(String format, Object... arguments);

}
