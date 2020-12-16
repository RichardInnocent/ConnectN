public class AIPlayer extends AbstractPlayer {

  private final AiStrategy strategy;

  public AIPlayer(PlayerColour colour, AiStrategy strategy) {
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
