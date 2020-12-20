/**
 * Should be inherited by all object types that should be printed to a print stream.
 */
@FunctionalInterface
public interface PrintableObject {

  /**
   * Prints the object to the specified IO output.
   * @param ioHandler The IO output that the object should be printed to.
   */
  void printToConsole(IOHandler ioHandler);

}
