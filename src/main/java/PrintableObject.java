/**
 * Should be inherited by all object types that should be displayed to the user.
 */
@FunctionalInterface
public interface PrintableObject {

  /**
   * Prints the object to the specified view.
   * @param view The view that displays output to the user.
   */
  void printToConsole(View view);

}
