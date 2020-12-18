import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

public class MyConnectN {

  public static void main(String[] args) throws IOException {
    Properties properties = new Properties();
    if (args.length > 0) {
      properties.load(new FileInputStream(args[0]));
    }
    new MyConnectN(properties).playGame();
  }

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

}