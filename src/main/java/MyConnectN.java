import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <p>A game of Connect N.</p>
 * <br />
 * <h2>Starting the game</h2>
 * <p>The game can be instantiated from the terminal in three ways:</p>
 * <p>
 *   <pre><code>java MyConnectN</code></pre>
 *   This creates a standard game of two-player Connect Four.
 * </p>
 * <br /><br />
 * <p>
 *   <pre><code>java MyConnectN path/to/config.file</code></pre>
 *   Creates a game of Connect N as specified in the configuration file. Details of the options
 *   available in the configuration file are defined in the {@link GameConfig} Javadoc.
 * </p>
 * <br /><br />
 * <p>
 *   <pre><code>java MyConnectN n</code></pre> where {@code n} is an integer in the range
 *   {@code 2 < n < 7}.<br />
 *   Creates a game of 3-handed Connect N, where N (the number of counters required to connect in a
 *   row for any player to win) is defined by the value of {@code n}.
 * </p>
 */
public class MyConnectN {

  private final GameConfig gameConfig;
  private final Board board;
  private final Collection<Player> players;
  private final View view;

  /**
   * Creates the game of Connect N from the provided game configuration. {@link System#in} and
   * {@link System#out} will be used as the single input and output sources respectively.
   * @param gameConfig Contains details of how the game should be configured.
   * @throws NullPointerException Thrown if {@code gameConfig == null}.
   */
  public MyConnectN(GameConfig gameConfig) throws NullPointerException {
    this(gameConfig, System.in, System.out);
  }

  /**
   * Creates a game of Connect N from the provided game configuration.
   * @param gameConfig Contains details of how the game should be configured.
   * @param inputStream Used to retrieve user input.
   * @param outputStream Where game outputs should be printed to.
   * @throws NullPointerException Thrown if {@code gameConfig == null}, {@code inputStream == null}
   * or {@code outputStream == null}.
   */
  public MyConnectN(GameConfig gameConfig, InputStream inputStream, PrintStream outputStream)
      throws NullPointerException {
    this.gameConfig = Objects.requireNonNull(gameConfig, "Game config is null");
    view = new SingleSourceView(outputStream);
    board = new Board(gameConfig.getBoardConfiguration());
    players = createPlayers(gameConfig, inputStream);
  }

  /**
   * Creates the players as specified in the game config.
   * @param gameConfig The game configuration that contains the specifications for the players.
   * @param inputStream The input stream that will be shared by all human players.
   * @return The players.
   * @throws NullPointerException Thrown if {@code inputStream == null}.
   */
  private Collection<Player> createPlayers(GameConfig gameConfig, InputStream inputStream)
      throws NullPointerException {
    PlayerFactory playerFactory =
        new SharedInputSourcePlayerFactory(new BufferedReader(new InputStreamReader(inputStream)));
    return gameConfig
        .getPlayerConfigurations()
        .stream()
        .map(playerFactory::create)
        .collect(Collectors.toList());
  }

  /**
   * Plays the game. Instructions are printed, and then players take their turns in sequence until
   * a player achieves their victory condition, or the board is full.
   */
  public void playGame() {
    // Print the instructions
    new VerboseInstructions(gameConfig).printToConsole(view);

    board.printToConsole(view);
    view.printLine();

    do {
      // Loop through each player (players is already in the order that turns should be taken)
      for (Player player : players) {
        player.takeTurn(board, view); // Take the player's turn
        board.printToConsole(view); // Print the result, after the player has taken their turn

        // Did the player achieve their victory condition on this turn?
        if (player.isVictoryAchieved(board)) {
          // Yes - game over!
          view.printLine(player.getColour().getName() + " player wins!");
          return;
        } else if (board.isFull()) {
          // No, but no more moves can be made as the board is full. The game is a tie!
          view.printLine("No more moves can be made, and no victor has emerged.");
          view.printLine("The game is a tie!");
          return;
        }
        view.printLine();
      }
      // The player wasn't victorious, so it's the next player's turn
    } while (true); // Once at the end of players, keep starting over until the game is over
  }

  /**
   * Starts the game.
   * @param args The command line arguments. See {@link MyConnectN} for details.
   */
  public static void main(String[] args) {
    try {
      createAndStartGame(args);
    } catch (RuntimeException e) {
      // Don't print an ugly stacktrace - the exception message should contain relevant details
      System.out.println(e.getMessage());
    }
  }

  /**
   * Creates and starts the game from the provided command line arguments.
   * @param args The arguments passed in from the command line.
   * @throws RuntimeException Thrown if there is a problem setting up the game.
   */
  private static void createAndStartGame(String[] args) throws RuntimeException {
    GameConfig gameConfig = createGameConfig(args);
    new MyConnectN(gameConfig).playGame();
  }

  /**
   * Creates the game config from the command line arguments.
   * @param args The arguments passed in from the command line.
   * @return The game config that can be used to create the game.
   * @throws RuntimeException Thrown if there is a problem setting up the game - most likely, the
   * configuration is invalid.
   */
  private static GameConfig createGameConfig(String[] args) throws RuntimeException {

    // If there are no command line arguments, create a game of 2-handed Connect Four
    if (args.length == 0) {
      System.out.println(
          "No config file supplied. Creating a standard game of Connect Four..."
              + System.lineSeparator()
      );
      return new GameConfig(new Properties());
    }

    // Is the first input argument a number? If so, create a game of 3-handed connect n
    try {
      int consecutiveCounters = Integer.parseInt(args[0]);
      return new GameConfig(consecutiveCounters);
    } catch (NumberFormatException e) {
      // Not an integer argument - good, maybe it's a configuration file as hoped...
    }

    // Assume the first argument is a path to the configuration file.
    try {
      Properties gameProperties = new Properties();
      // Load the properties from the file
      gameProperties.load(new FileInputStream(args[0]));
      return new GameConfig(gameProperties);
    } catch (IOException e) {
      // Error opening file - most likely it wasn't a valid path to a file
      throw new IllegalArgumentException(
          "Could not find or open file " + args[0] + System.lineSeparator()
              + "This game expects a path to a file as its only argument. "
              + "Create a properties file (an example should have been supplied in the "
              + "submission), and then call the program as follows:" + System.lineSeparator() +
              "java MyConnectN \"path/to/config.properties\""
      );
    }
  }

}