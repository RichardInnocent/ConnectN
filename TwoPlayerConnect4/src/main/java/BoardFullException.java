public class BoardFullException extends RuntimeException {

  public BoardFullException() {
    super("The board is full");
  }

}
