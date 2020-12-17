/**
 * A computer player. The player should play according to their given {@link AIStrategy}.
 */
public class AIPlayer extends AbstractPlayer {

  private final AIStrategy strategy;

  /**
   * Creates a computer player.
   * @param colour The player's colour.
   * @param strategy The strategy that will be used when taking turns.
   */
  public AIPlayer(PlayerColour colour, AIStrategy strategy) {
    super(colour);
    this.strategy = strategy;
  }

  @Override
  public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {
    strategy.takeTurn(board, this);
  }

  @Override
  public boolean isHuman() {
    return false;
  }
}
