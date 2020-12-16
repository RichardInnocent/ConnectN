import java.util.Objects;

/**
 * Abstract implementation of a player with a static (unchangeable) player colour.
 */
public abstract class AbstractPlayer implements Player {

  private final PlayerColour colour;

  /**
   * Creates a player with the given player colour.
   * @param colour The colour that will be used to refer to the player.
   * @throws NullPointerException Thrown if {@code colour == null}.
   */
  protected AbstractPlayer(PlayerColour colour) throws NullPointerException {
    this.colour = Objects.requireNonNull(colour);
  }

  @Override
  public PlayerColour getColour() {
    return colour;
  }

  @Override
  public void takeTurn(Board board, IOHandler ioHandler) throws BoardFullException {
    if (board.isFull()) {
      throw new BoardFullException();
    }
    takeTurnOnIncompleteBoard(board, ioHandler);
  }

  /**
   * Takes the player's turn, adding their counter to the board.
   * @param board The board to add the counter to. Note that {@code board} will never be supplied
   * such that {@link Board#isFull()} is {@code true}.
   */
  protected abstract void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler);

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractPlayer that = (AbstractPlayer) o;
    return colour == that.colour;
  }

  @Override
  public int hashCode() {
    return colour.hashCode();
  }
}
