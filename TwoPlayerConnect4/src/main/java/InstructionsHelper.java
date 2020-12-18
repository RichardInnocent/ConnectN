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
    ioHandler.printf("There are %d players:" + System.lineSeparator(), players.size());
    printPlayers(players, ioHandler);
    ioHandler.printLine("To play the game type in the number of the column you want to drop you counter in");
    ioHandler.printLine("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
    ioHandler.printLine();
  }

  /**
   * Prints out the different players in the game.
   */
  private static void printPlayers(Collection<Player> players, IOHandler ioHandler) {
    players.stream()
           .map(Object::toString)
           .map(playerRep -> "- " + playerRep)
           .forEach(ioHandler::printLine);
  }

}
