import java.util.Optional;

public class VictoryCondition {

  private final int consecutiveCounters;

  public VictoryCondition(int consecutiveCounters) {
    this.consecutiveCounters = consecutiveCounters;
  }

  public boolean isAchievedForPlayer(Player player, Board board) {
    return winsOnHorizontal(player, board) ||
        winsOnVertical(player, board) ||
        winsOnLeadingDiagonal(player, board) ||
        winsOnCounterdiagonal(player, board);
  }

  private boolean winsOnHorizontal(Player player, Board board) {
    int consecutiveCount = 0;
    for (int row = 1; row <= board.getHeight(); row++) {
      for (int column = 1; column <= board.getWidth(); column++) {
        Optional<Player> ownerOfCounter = board.getOwnerOfCounterAt(row, column);
        if (ownerOfCounter.isPresent() && ownerOfCounter.get().equals(player)) {
          consecutiveCount++;
          if (consecutiveCount >= consecutiveCounters) {
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

  private boolean winsOnVertical(Player player, Board board) {
    int consecutiveCount = 0;
    for (int column = 1; column <= board.getWidth(); column++) {
      for (int row = 1; row <= board.getHeight(); row++) {
        Optional<Player> ownerOfCounter = board.getOwnerOfCounterAt(row, column);
        if (ownerOfCounter.isPresent() && ownerOfCounter.get().equals(player)) {
          consecutiveCount++;
          if (consecutiveCount >= consecutiveCounters) {
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

  private boolean winsOnLeadingDiagonal(Player player, Board board) {
    int minRow = board.getWidth() - (board.getWidth() - consecutiveCounters);
    if (minRow >= 1) {
      // Iterate down left-hand edge
      for (int row = board.getHeight(); row >= minRow; row--) {
        if (winsOnLeadingDiagonalFromStartPoint(player, board, row, 1)) {
          return true;
        }
      }
    }

    int maxColumn = board.getHeight() - consecutiveCounters + 1;

    if (maxColumn >= 2) { // top-left will already have been checked
      // Iterate across top edge
      for (int column = 2; column <= maxColumn; column++) {
        if (winsOnLeadingDiagonalFromStartPoint(player, board, board.getHeight(), column)) {
          return true;
        }
      }
    }

    return false;
  }

  // Checks down and to the right
  private boolean winsOnLeadingDiagonalFromStartPoint(
      Player player, Board board, int startRow, int startColumn) {
    int consecutiveCount = 0;
    for (int row = startRow, column = startColumn;
         row >= 1 && column <= board.getWidth();
         row--, column++) {
      Optional<Player> owner = board.getOwnerOfCounterAt(row, column);
      if (owner.isPresent() && owner.get().equals(player)) {
        consecutiveCount++;
        if (consecutiveCount >= 4) {
          return true;
        }
      } else {
        consecutiveCount = 0;
      }
    }
    return false;
  }

  private boolean winsOnCounterdiagonal(Player player, Board board) {
    int maxColumn = board.getWidth() + 1 - consecutiveCounters;

    // Iterate down left-hand edge of board
    if (maxColumn > 0) {
      for (int column = maxColumn; column >= 1; column--) {
        if (winsOnCounterdiagonalStartingFrom(player, board, 1, column)) {
          return true;
        }
      }
    }

    int maxRow = board.getHeight() + 1 - consecutiveCounters;

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

  private boolean winsOnCounterdiagonalStartingFrom(
      Player player, Board board, int startRow, int startColumn) {
    int consecutiveCount = 0;
    for (int row = startRow, column = startColumn;
         row <= board.getHeight() && column <= board.getWidth();
         row++, column++) {
      Optional<Player> owner = board.getOwnerOfCounterAt(row, column);
      if (owner.isPresent() && owner.get().equals(player)) {
        consecutiveCount++;
        if (consecutiveCount >= consecutiveCounters) {
          return true;
        }
      } else {
        consecutiveCount = 0;
      }
    }
    return false;
  }

}
