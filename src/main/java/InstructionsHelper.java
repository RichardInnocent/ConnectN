import java.util.Collection;

/**
 * Helper class to display instructions.
 */
public class InstructionsHelper {

  /**
   * Displays the instructions to the player.
   * @param players The players in the game.
   * @param view The I/O that will display the instructions to the user.
   */
  public static void displayInstructions(Collection<Player> players, View view) {
    view.printLine("Welcome to Connect 4!");
    view.printf("There are %d players:%s", players.size(), System.lineSeparator());
    printPlayersAndVictoryConditions(players, view);

    if (players.stream().anyMatch(Player::isHuman)) {
      // Only print the instructions on how to play the game if there's a human player
      view.printLine(
          System.lineSeparator() +
              "To play the game type in the number of the column you want to drop you counter in"
      );
    }
    view.printLine();
  }

  /**
   * Prints out the different players in the game.
   */
  private static void printPlayersAndVictoryConditions(
      Collection<Player> players, View view) {
    players.stream()
           .map(Object::toString)
           .map(playerRep -> "- " + playerRep)
           .forEach(view::printLine);
    view.printLine();
    printVictoryConditions(players, view);
  }

  private static void printVictoryConditions(Collection<Player> players, View view) {
    view.printLine("How each player wins:");
    players.forEach(
        player -> view.printLine(
            player.getColour().getName() + ": " + player.getVictoryCondition()
        )
    );
  }

}
