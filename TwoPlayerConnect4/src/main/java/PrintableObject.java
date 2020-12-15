import java.io.PrintStream;

/**
 * Should be inherited by all object types that should be printed to a print stream.
 */
@FunctionalInterface
public interface PrintableObject {

  /**
   * Prints the object to the specified print stream.
   */
  void printToConsole(PrintStream printStream);

}
