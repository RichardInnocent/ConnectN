import java.util.List;
import java.util.stream.Collectors;

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

  VictoryCondition getVictoryCondition();

  /**
   * Returns {@code true} if the player is controlled by a human.
   * @return {@code true} if the player is controlled by a human.
   */
  boolean isHuman();

  boolean isVictoryAchieved(Board board);

  static Player create(PlayerConfiguration config) {
    if (config.isComputerPlayer()) {
      return new AIPlayer(config.getColour(), config.getDifficulty(), config.getVictoryCondition());
    } else {
      return new HumanPlayer(config.getColour(), config.getVictoryCondition());
    }
  }

}
