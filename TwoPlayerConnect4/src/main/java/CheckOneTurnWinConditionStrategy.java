import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

public class CheckOneTurnWinConditionStrategy implements AiStrategy {

  private final VictoryCondition victoryCondition;
  private final Random random;

  public CheckOneTurnWinConditionStrategy(VictoryCondition victoryCondition)
      throws NullPointerException {
    this(victoryCondition, new Random());
  }

  CheckOneTurnWinConditionStrategy(VictoryCondition victoryCondition, Random random)
      throws NullPointerException {
    this.victoryCondition =
        Objects.requireNonNull(victoryCondition, "Victory condition is null");
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
    return victoryCondition.isAchievedForPlayer(player, copyOfBoard);
  }
}
