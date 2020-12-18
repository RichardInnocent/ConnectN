import java.util.Collection;

/**
 * Helper class to display instructions.
 */
public class InstructionsHelper {

  /**
   * Displays the instructions to the player.
   * @param players The players in the game.
   * @param ioHandler The I/O that will display the instructions to the user.
   */
  public static void displayInstructions(Collection<Player> players, IOHandler ioHandler) {
    ioHandler.printLine("Welcome to Connect 4!");
    ioHandler.printf("There are %d players:%s", players.size(), System.lineSeparator());
    printPlayersAndVictoryConditions(players, ioHandler);

    if (players.stream().anyMatch(Player::isHuman)) {
      // Only print the instructions on how to play the game if there's a human player
      ioHandler.printLine(
          "\nTo play the game type in the number of the column you want to drop you counter in");
    }
    ioHandler.printLine();
  }

  /**
   * Prints out the different players in the game.
   */
  private static void printPlayersAndVictoryConditions(
      Collection<Player> players, IOHandler ioHandler) {
    players.stream()
           .map(Object::toString)
           .map(playerRep -> "- " + playerRep)
           .forEach(ioHandler::printLine);
    ioHandler.printLine();
    printVictoryConditions(players, ioHandler);
  }

  private static void printVictoryConditions(Collection<Player> players, IOHandler ioHandler) {
    ioHandler.printLine("How each player wins:");
    players.forEach(
        player -> ioHandler.printLine(
            player.getColour().getName() + ": " + player.getVictoryCondition()
        )
    );
  }

}
