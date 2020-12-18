import java.util.Optional;

/**
 * A victory condition achieved by a player getting a specified number of counters in a row on the
 * board.
 */
public class ConsecutiveCountersVictoryCondition implements VictoryCondition {

  private final int consecutiveCountersRequired;

  /**
   * Creates a new victory condition where victory can be achieved by a player getting a specified
   * number of counters in a row on the board.
   * @param consecutiveCountersRequired The number of consecutive counters required to achieve
   * victory.
   */
  public ConsecutiveCountersVictoryCondition(int consecutiveCountersRequired) {
    if (consecutiveCountersRequired < 1) {
      throw new IllegalArgumentException("Consecutive counters must be > 0");
    }
    this.consecutiveCountersRequired = consecutiveCountersRequired;
  }

  @Override
  public String toString() {
    return "4 counters in a row";
  }

  public int getConsecutiveCountersRequired() {
    return consecutiveCountersRequired;
  }

  @Override
  public boolean isAchievedForPlayer(Player player, Board board) {
    // Check if the player has won on any of the four direction
    return winsOnHorizontal(player, board) ||
        winsOnVertical(player, board) ||
        winsOnLeadingDiagonal(player, board) ||
        winsOnCounterdiagonal(player, board);
  }

  /**
   * Checks if the player has the appropriate number of consecutive counters in the horizontal
   * direction on any row to trigger the victory condition.
   * @param board The board to check on.
   * @return {@code true} if the player has won in this direction.
   */
  private boolean winsOnHorizontal(Player player, Board board) {
    int consecutiveCount = 0;
    // Loop through each row
    for (int row = 1; row <= board.getHeight(); row++) {
      // Loop through each column
      for (int column = 1; column <= board.getWidth(); column++) {
        // Get the owner at this position
        Optional<Player> ownerOfCounter = board.getOwnerOfCounterAt(row, column);
        // If it's this player, increment the count
        if (ownerOfCounter.isPresent() && ownerOfCounter.get().equals(player)) {
          consecutiveCount++;
          if (consecutiveCount >= consecutiveCountersRequired) {
            // The player has the required number of counters or more in a row - victory achieved!
            return true;
          }
        } else {
          // Another player's counter is detected - reset the count
          consecutiveCount = 0;
        }
      }
      // Did not achieve victory on that row - reset the count ready for the next row.
      consecutiveCount = 0;
    }
    // Didn't win on any row
    return false;
  }

  /**
   * Checks if the player has the appropriate number of consecutive counters in the vertical
   * direction on any column to trigger the victory condition.
   * @param player The player to check.
   * @param board The board to check on.
   * @return {@code true} if the player has won in this direction.
   */
  private boolean winsOnVertical(Player player, Board board) {
    int consecutiveCount = 0;
    for (int column = 1; column <= board.getWidth(); column++) {
      for (int row = 1; row <= board.getHeight(); row++) {
        Optional<Player> ownerOfCounter = board.getOwnerOfCounterAt(row, column);
        if (ownerOfCounter.isPresent() && ownerOfCounter.get().equals(player)) {
          consecutiveCount++;
          if (consecutiveCount >= consecutiveCountersRequired) {
            return true;
          }
        } else {
          consecutiveCount = 0;
        }
      }
      consecutiveCount = 0;
    }
    return false;
  }

  /**
   * Checks if the player has the appropriate number of consecutive counters on the leading diagonal
   * (top left to bottom right) direction.
   * @param player The player to check.
   * @param board The board to check on.
   * @return {@code true} if the player has won in this direction.
   */
  private boolean winsOnLeadingDiagonal(Player player, Board board) {
    /* We could check each possible diagonal in turn. In the code below, we would start at the
     * bottom left, then one above it etc until all diagonals are covered. However, this isn't
     * strictly required. The bottom right starting position, for example, would only contain one
     * counter. Therefore, if consecutiveCountersRequired > 1, there's no point in checking this
     * diagonal.
     * The minRow and maxColumn conditions are therefore used to ensure that we only iterate
     * over the rows that could potentially trigger a win condition.
     */
    int minRow = board.getWidth() - (board.getWidth() - consecutiveCountersRequired);

    if (minRow >= 1) {
      // Iterate down left-hand edge
      for (int row = board.getHeight(); row >= minRow; row--) {
        // Check if this diagonal has n in a row
        if (winsOnLeadingDiagonalFromStartPoint(player, board, row, 1)) {
          return true;
        }
      }
    }

    int maxColumn = board.getHeight() - consecutiveCountersRequired + 1;

    if (maxColumn >= 2) { // top-left will already have been checked
      // Iterate across top edge
      for (int column = 2; column <= maxColumn; column++) {
        // Check if this diagonal has n in a row
        if (winsOnLeadingDiagonalFromStartPoint(player, board, board.getHeight(), column)) {
          return true;
        }
      }
    }

    return false;
  }

  // Checks down and to the right from the given starting position
  private boolean winsOnLeadingDiagonalFromStartPoint(
      Player player, Board board, int startRow, int startColumn) {
    int consecutiveCount = 0;
    for (int row = startRow, column = startColumn;
         row >= 1 && column <= board.getWidth();
         row--, column++) {
      Optional<Player> owner = board.getOwnerOfCounterAt(row, column);
      if (owner.isPresent() && owner.get().equals(player)) {
        consecutiveCount++;
        if (consecutiveCount >= consecutiveCountersRequired) {
          // Victory achieved!
          return true;
        }
      } else {
        // Another player's counter found - reset the count
        consecutiveCount = 0;
      }
    }
    return false;
  }

  /**
   * Checks if the player has the appropriate number of consecutive counters on the counterdiagonal
   * (bottom left to top right) direction.
   * @param player The player to check.
   * @param board The board to check on.
   * @return {@code true} if the player has won in this direction.
   */
  private boolean winsOnCounterdiagonal(Player player, Board board) {
    /* We could check each possible diagonal in turn. In the code below, we would start at the top
     * left, then one below it etc until all diagonals are covered. However, this isn't strictly
     * required. The top left starting position, for example, would only contain one counter.
     * Therefore, if consecutiveCountersRequired > 1, there's no point in checking this diagonal.
     * The minRow and maxColumn conditions are therefore used to ensure that we only iterate
     * over the rows that could potentially trigger a win condition.
     */
    int maxColumn = board.getWidth() + 1 - consecutiveCountersRequired;

    // Iterate down left-hand edge of board
    if (maxColumn > 0) {
      for (int column = maxColumn; column >= 1; column--) {
        if (winsOnCounterdiagonalStartingFrom(player, board, 1, column)) {
          return true;
        }
      }
    }

    int maxRow = board.getHeight() + 1 - consecutiveCountersRequired;

    // Iterate across bottom of board
    if (maxRow >= 2) { // bottom-left will already have been checked
      for (int row = 2; row <= maxRow; row++) {
        if (winsOnCounterdiagonalStartingFrom(player, board, row, 1)) {
          return true;
        }
      }
    }

    return false;
  }

  // Checks up and to the right from the given starting position
  private boolean winsOnCounterdiagonalStartingFrom(
      Player player, Board board, int startRow, int startColumn) {
    int consecutiveCount = 0;
    for (int row = startRow, column = startColumn;
         row <= board.getHeight() && column <= board.getWidth();
         row++, column++) {
      Optional<Player> owner = board.getOwnerOfCounterAt(row, column);
      if (owner.isPresent() && owner.get().equals(player)) {
        consecutiveCount++;
        if (consecutiveCount >= consecutiveCountersRequired) {
          // Victory achieved!
          return true;
        }
      } else {
        // Another player's counter found - reset the count
        consecutiveCount = 0;
      }
    }
    return false;
  }

}
