import java.util.Objects;

/**
 * A computer player. The player should play according to their given {@link AIStrategy}.
 */
public class AIPlayer extends AbstractPlayer {

  private final Difficulty difficulty;
  private final VictoryCondition victoryCondition;

  /**
   * Creates a computer player.
   * @param colour The player's colour.
   * @param difficulty The difficulty of the AI player.
   * @param victoryCondition The victory condition that will allow the player to win.
   * @throws NullPointerException Thrown if {@code colour == null}, {@code difficult == null} or
   * {@code victoryCondition == null}.
   */
  public AIPlayer(PlayerColour colour, Difficulty difficulty, VictoryCondition victoryCondition) {
    super(colour, victoryCondition);
    this.difficulty = Objects.requireNonNull(difficulty, "Difficulty is null");
    this.victoryCondition = Objects.requireNonNull(victoryCondition, "Victory condition is null");
  }

  /**
   * Gets the difficulty of the computer player
   * @return The difficulty.
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  @Override
  public VictoryCondition getVictoryCondition() {
    return victoryCondition;
  }

  @Override
  public String toString() {
    return super.toString() + " - Computer (" + difficulty.getName() + ')';
  }

  @Override
  public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {
    ioHandler.printLine(getColour().getName() + " player is thinking...");
    difficulty.getStrategy(victoryCondition).takeTurn(board, this);
    ioHandler.printLine();
  }

  @Override
  public boolean isHuman() {
    return false;
  }
}
