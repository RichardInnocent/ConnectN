/**
 * Exception thrown when the board is full and a move is attempted to be made.
 */
public class BoardFullException extends RuntimeException {

  /**
   * Creates a new exception to indicate that a move is attempted to be made when the board is full.
   */
  public BoardFullException() {
    super("The board is full");
  }

}
