/**
 * Represents a player in the game of Connect 4. This can be either a human or computer player.
 */
public interface Player {

  /**
   * Takes the player's turn, adding their counter to the board.
   * @param board The board to add the counter to.
   */
  void takeTurn(Board board);

  /**
   * Gets the player's unique colour.
   * @return The player's unique colour.
   */
  PlayerColour getColour();

}
