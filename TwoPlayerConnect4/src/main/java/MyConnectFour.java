import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MyConnectFour {

  public static void main(String[] args){
    new MyConnectFour().playGame();
  }

  private final Board board = new Board(6, 7);
  private final Collection<Player> players;
  private final VictoryCondition victoryCondition = new ConsecutiveCountersVictoryCondition(4);
  private final IOHandler ioHandler;

  public MyConnectFour() {
    this(System.in, System.out);
  }

  public MyConnectFour(InputStream inputStream, PrintStream outputStream) {
    this.ioHandler = new SingleSourceIOHandler(inputStream, outputStream);
    AIStrategy strategy =
        new CheckOneTurnWinConditionStrategy(Collections.singleton(victoryCondition));
    players = Arrays.asList(
        new HumanPlayer(PlayerColour.RED),
        new AIPlayer(PlayerColour.YELLOW, strategy)
    );
  }

  public void playGame() {
    InstructionsHelper.displayInstructions(players, ioHandler);
    board.printToConsole(ioHandler);

    do {
      for (Player player : players) {
        player.takeTurn(board, ioHandler);
        board.printToConsole(ioHandler);

        if (victoryCondition.isAchievedForPlayer(player, board)) {
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