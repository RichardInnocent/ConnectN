import java.util.Collection;
import java.util.List;

/**
 * Displays instructions to the user in the following sections:
 * <ul>
 *   <li>Introductory text</li>
 *   <li>Number of players</li>
 *   <li>Player setup</li>
 * </ul>
 */
public class VerboseInstructions extends Instructions {

  /**
   * Creates a new verbose instructions instance that will display instructions based on the
   * provided game configuration.
   * @param gameConfig The game configuration.
   * @throws NullPointerException Thrown if {@code gameConfig == null}.
   */
  public VerboseInstructions(GameConfig gameConfig) throws NullPointerException {
    super(gameConfig);
  }

  @Override
  public void printInstructions(View view, GameConfig gameConfig) {
    view.printLine("Welcome to Connect N!");

    List<PlayerConfiguration> playerConfigurations = gameConfig.getPlayerConfigurations();

    view.printf("There are %d players:%s", playerConfigurations.size(), System.lineSeparator());
    printPlayersAndVictoryConditions(playerConfigurations, view);

    if (playerConfigurations.stream().anyMatch(config -> !config.isComputerPlayer())) {
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
      Collection<PlayerConfiguration> playerConfigurations, View view) {
    playerConfigurations
        .stream()
        .map(Object::toString)
        .map(playerRep -> "- " + playerRep)
        .forEach(view::printLine);
    view.printLine();
    printVictoryConditions(playerConfigurations, view);
  }

  private static void printVictoryConditions(Collection<PlayerConfiguration> players, View view) {
    // Each player can have different victory conditions - print them out by player.
    view.printLine("How each player wins:");
    players.forEach(
        player -> view.printLine(
            player.getColour().getName() + ": " + player.getVictoryCondition()
        )
    );
  }

}
