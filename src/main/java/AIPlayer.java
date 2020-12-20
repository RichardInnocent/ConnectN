import java.util.Objects;

/**
 * A computer player. The player should play according to their given {@link AIStrategy}.
 */
public class AIPlayer extends AbstractPlayer {

  private final Difficulty difficulty;

  /**
   * Creates a computer player.
   * @param colour The player's colour.
   * @param victoryCondition The victory condition that will allow the player to win.
   * @param difficulty The difficulty of the AI player.
   * @throws NullPointerException Thrown if {@code colour == null}, {@code difficult == null} or
   * {@code victoryCondition == null}.
   */
  public AIPlayer(PlayerColour colour, VictoryCondition victoryCondition, Difficulty difficulty) {
    super(colour, victoryCondition);
    this.difficulty = Objects.requireNonNull(difficulty, "Difficulty is null");
  }

  /**
   * Gets the difficulty of the computer player
   * @return The difficulty.
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  @Override
  public String toString() {
    return super.toString() + " - Computer (" + difficulty.getName() + ')';
  }

  @Override
  public void takeTurnOnIncompleteBoard(Board board, View view) {
    view.sendLine(getColour().getName() + " player is thinking...");
    difficulty.getStrategy(getVictoryCondition()).takeTurn(board, this);
    view.sendLine();
  }

  @Override
  public boolean isHuman() {
    return false;
  }
}
