/**
 * Represents a player in the game of Connect 4. This can be either a human or computer player.
 */
public interface Player {

  /**
   * Takes the player's turn, adding their counter to the board.
   * @param board The board to add the counter to.
   * @param ioHandler The instance that will send/retrieve input to/from the users.
   * @throws BoardFullException Thrown if the board is full.
   */
  void takeTurn(Board board, IOHandler ioHandler) throws BoardFullException;

  /**
   * Gets the player's unique colour.
   * @return The player's unique colour.
   */
  PlayerColour getColour();

  /**
   * Gets the player's victory condition. It's possible to configure each different player to have
   * a different victory condition - one player might win by connecting 4 counters in a row, while
   * another player might win by create a 3x3 grid of counters.
   * @return The player's victory condition.
   */
  VictoryCondition getVictoryCondition();

  /**
   * Returns {@code true} if the player is controlled by a human.
   * @return {@code true} if the player is controlled by a human.
   */
  boolean isHuman();

  /**
   * Determines if the player has achieved their victory condition on the given board.
   * @param board The board.
   * @return {@code true} if the player is victorious.
   */
  boolean isVictoryAchieved(Board board);

  /**
   * Creates a player.
   * @param config The specification for the player.
   * @return The player.
   */
  static Player create(PlayerConfiguration config) {
    if (config.isComputerPlayer()) {
      return new AIPlayer(config.getColour(),config.getVictoryCondition(), config.getDifficulty());
    } else {
      return new HumanPlayer(
          config.getColour(),
          config.getVictoryCondition(),
          SystemInReader.getInstance()
      );
    }
  }

}
