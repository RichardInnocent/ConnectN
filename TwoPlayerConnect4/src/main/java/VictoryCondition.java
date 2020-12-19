/**
 * The condition required for a player to win the game.
 */
@FunctionalInterface
public interface VictoryCondition {

  /**
   * Determines if the given player is victorious on the given board.
   * @param player The player to check.
   * @param board The board to check on.
   * @return {@code true} if the player is victorious.
   */
  boolean isAchievedForPlayer(Player player, Board board);
}
