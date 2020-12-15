import java.util.List;
import java.util.Objects;
import java.util.Random;

class RandomPlacementStrategy implements AiStrategy {

  private final Random random;

  public RandomPlacementStrategy() {
    this(new Random());
  }

  RandomPlacementStrategy(Random random) throws NullPointerException {
    this.random = Objects.requireNonNull(random, "Random is null");
  }

  @Override
  public void takeTurn(Board board, Player player) {
    List<Integer> potentialColumns = board.getColumnsWithSpareCapacity();
    int columnNumber = potentialColumns.get(random.nextInt(potentialColumns.size()));
    board.placePlayerCounterInColumn(player, columnNumber);
  }

}
