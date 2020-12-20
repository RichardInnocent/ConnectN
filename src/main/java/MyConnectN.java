import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
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
 *   {@code 2 < n < 7}.
 * </p>
 */
public class MyConnectN {

  private final Board board;
  private final Collection<Player> players;
  private final View view;

  public MyConnectN(GameConfig gameConfig) {
    this(gameConfig, System.in, System.out);
  }

  public MyConnectN(GameConfig gameConfig, InputStream inputStream, PrintStream outputStream) {
    this.view = new SingleSourceView(inputStream, outputStream);
    board = new Board(gameConfig.getBoardConfiguration());
    players = gameConfig
        .getPlayerConfigurations()
        .stream()
        .map(Player::create)
        .collect(Collectors.toList());
  }

  public void playGame() {
    InstructionsHelper.displayInstructions(players, view);
    board.printToConsole(view);
    view.printLine();

    do {
      for (Player player : players) {
        player.takeTurn(board, view);
        board.printToConsole(view);

        if (player.isVictoryAchieved(board)) {
          view.printLine(player.getColour().getName() + " player wins!");
          return;
        } else if (board.isFull()) {
          view.printLine("No more moves can be made, and no victor has emerged.");
          view.printLine("The game is a tie!");
          return;
        }
        view.printLine();
      }
    } while (true);
  }

  public static void main(String[] args) {
    try {
      createAndStartGame(args);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void createAndStartGame(String[] args) throws RuntimeException {
    GameConfig gameConfig = createGameConfig(args);
    new MyConnectN(gameConfig).playGame();
  }

  private static GameConfig createGameConfig(String[] args) {
    if (args.length == 0) {
      System.out.println(
          "No config file supplied. Creating a standard game of Connect Four..."
              + System.lineSeparator()
      );
      return new GameConfig(new Properties());
    }

    try {
      int consecutiveCounters = Integer.parseInt(args[0]);
      return new GameConfig(consecutiveCounters);
    } catch (NumberFormatException e) {
      // Not an integer argument - good, maybe it's a configuration file as hoped...
    }

    try {
      Properties gameProperties = new Properties();
      gameProperties.load(new FileInputStream(args[0]));
      return new GameConfig(gameProperties);
    } catch (IOException e) {
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