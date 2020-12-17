/**
 * An exception thrown if a player attempts to make an invalid move.
 */
public class InvalidMoveException extends RuntimeException {

  /**
   * Creates an exception indicating that a player attempted to make an invalid move.
   * @param message The exception message.
   */
  public InvalidMoveException(String message) {
    super(message);
  }

}
