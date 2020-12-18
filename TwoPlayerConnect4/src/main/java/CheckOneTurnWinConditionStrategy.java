import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

/**
 * Simple strategy implementation where all moves are random, unless there's a chance to immediately
 * either win or block another player from winning by placing the counter.
 */
public class CheckOneTurnWinConditionStrategy implements AIStrategy {

  private final VictoryCondition victoryCondition;
  private final Random random;

  /**
   * Creates a new one turn win condition strategy, optimising for the given win conditions.
   * @param victoryCondition The victory condition to optimise for.
   * @throws NullPointerException Thrown if {@code victoryConditions == null}.
   */
  public CheckOneTurnWinConditionStrategy(VictoryCondition victoryCondition)
      throws NullPointerException {
    this(victoryCondition, new Random());
  }

  /**
   * Creates a new one turn win condition strategy, optimising for the given win conditions.
   * @param victoryCondition The victory conditions to optimise for.
   * @param random The random instance used when taking a random move.
   * @throws NullPointerException Thrown if {@code victoryConditions == null} or
   * {@code random == null}.
   */
  CheckOneTurnWinConditionStrategy(VictoryCondition victoryCondition, Random random)
      throws NullPointerException {
    this.victoryCondition =
        Objects.requireNonNull(victoryCondition, "Victory condition collection is null");
    this.random = Objects.requireNonNull(random, "Random is null");
  }

  @Override
  public void takeTurn(Board board, Player player) {
    board.placePlayerCounterInColumn(player, getColumnForMove(board, player));
  }

  private int getColumnForMove(Board board, Player player) {
    OptionalInt winningColumn = getWinningMoveForPlayer(board, player);
    if (winningColumn.isPresent()) {
      return winningColumn.getAsInt();
    }

    Optional<Integer> winningMoveForOtherPlayer =
        board.getAllPlayersOnBoard()
             .stream()
             .filter(p -> p != player)
             .map(p -> getWinningMoveForPlayer(board, p))
             .filter(OptionalInt::isPresent)
             .map(OptionalInt::getAsInt)
             .findFirst();

    if (winningMoveForOtherPlayer.isPresent()) {
      return winningMoveForOtherPlayer.get();
    }

    List<Integer> possibleColumns = board.getColumnsWithSpareCapacity();
    return possibleColumns.get(random.nextInt(possibleColumns.size()));
  }

  private OptionalInt getWinningMoveForPlayer(Board board, Player player) {
    return board.getColumnsWithSpareCapacity()
                .stream()
                .filter(columnIndex -> isWinningMove(board, player, columnIndex))
                .mapToInt(Integer::intValue)
                .findAny();
  }

  private boolean isWinningMove(Board board, Player player, int column) {
    Board copyOfBoard = board.copy();
    copyOfBoard.placePlayerCounterInColumn(player, column);
    if (victoryCondition.isAchievedForPlayer(player, copyOfBoard)) {
      return true;
    }
    return false;
  }
}
