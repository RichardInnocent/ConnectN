import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class MyConnectN {

  private final Board board;
  private final Collection<Player> players;
  private final IOHandler ioHandler;

  public MyConnectN(Properties properties) {
    this(properties, System.in, System.out);
  }

  public MyConnectN(Properties properties, InputStream inputStream, PrintStream outputStream) {
    this.ioHandler = new SingleSourceIOHandler(inputStream, outputStream);
    GameConfig gameConfig = new GameConfig(properties);
    board = new Board(gameConfig.getBoardConfiguration());
    players = gameConfig
        .getPlayerConfigurations()
        .stream()
        .map(Player::create)
        .collect(Collectors.toList());
  }

  public void playGame() {
    InstructionsHelper.displayInstructions(players, ioHandler);
    board.printToConsole(ioHandler);

    do {
      for (Player player : players) {
        player.takeTurn(board, ioHandler);
        board.printToConsole(ioHandler);

        if (player.isVictoryAchieved(board)) {
          ioHandler.printLine(player.getColour().getName() + " player wins!");
          return;
        } else if (board.isFull()) {
          ioHandler.printLine("No more moves can be made, and no victor has emerged.");
          ioHandler.printLine("The game is a tie!");
          return;
        }
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
    Properties properties = getPropertiesFromArguments(args);
    new MyConnectN(properties).playGame();
  }

  private static Properties getPropertiesFromArguments(String[] args) {
    Properties gameProperties = new Properties();
    if (args.length == 0) {
      System.out.println(
          "No config file supplied. Creating a standard game of Connect Four..."
              + System.lineSeparator()
      );
      return gameProperties;
    }

    try {
      gameProperties.load(new FileInputStream(args[0]));
      return gameProperties;
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