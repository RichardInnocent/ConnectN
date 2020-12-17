import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A strategy whereby a counter is randomly placed in a valid position. No attempt is made at
 * assessing the state of the board.
 */
public class RandomPlacementStrategy implements AIStrategy {

  private final Random random;

  /**
   * Creates a new random placement strategy.
   */
  public RandomPlacementStrategy() {
    this(new Random());
  }

  /**
   * Creates a new random placement strategy.
   * @param random The random instance that will be used to determine where a counter is placed.
   * @throws NullPointerException Thrown if {@code random == null}.
   */
  RandomPlacementStrategy(Random random) throws NullPointerException {
    this.random = Objects.requireNonNull(random, "Random is null");
  }

  @Override
  public void takeTurn(Board board, Player player) {
    // Place a counter in a random one of the spare columns
    List<Integer> potentialColumns = board.getColumnsWithSpareCapacity();
    int columnNumber = potentialColumns.get(random.nextInt(potentialColumns.size()));
    board.placePlayerCounterInColumn(player, columnNumber);
  }

}
