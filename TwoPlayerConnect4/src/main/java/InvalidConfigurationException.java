/**
 * An exception indicating that the configuration specified is invalid, i.e. using it would create
 * an unplayable game.
 */
public class InvalidConfigurationException extends RuntimeException {

  /**
   * Creates an exception indicating that the configuration specified is invalid.
   * @param message The exception message.
   */
  public InvalidConfigurationException(String message) {
    super(message);
  }

}
