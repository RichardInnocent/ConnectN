public class AiPlayer extends ConstantColourPlayer {

  private final AiStrategy strategy;

  public AiPlayer(PlayerColour colour, AiStrategy strategy) {
    super(colour);
    this.strategy = strategy;
  }

  @Override
  public void takeTurn(Board board) {
    strategy.takeTurn(board, this);
  }
}
