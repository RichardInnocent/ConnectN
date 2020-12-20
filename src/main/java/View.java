/**
 * An interface that should be used when displaying information to the user.
 */
public interface View extends AutoCloseable {

  /**
   * Sends the message to the output stream, with no line break at the end.
   * @param message The message to send.
   */
  void send(String message);

  /**
   * Sends the message to the output stream, followed by a line break.
   * @param message The message to print.
   */
  void sendLine(String message);

  /**
   * Sends a new line.
   */
  void sendLine();

  /**
   * Sends the formatted message to the output stream.
   * @param format The message template.
   * @param arguments The arguments that are injected into the message.
   * @see java.io.PrintStream#printf(String, Object...) 
   */
  void sendf(String format, Object... arguments);

}
