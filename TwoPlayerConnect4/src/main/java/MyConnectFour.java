import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MyConnectFour {

  public static void main(String[] args){
    new MyConnectFour().playGame();
  }

  private final Board board = new Board(6, 7);
  private final List<Player> players;
  private final VictoryCondition victoryCondition = new VictoryCondition(4);
  private final PrintStream outputStream;

  public MyConnectFour() {
    this(System.in, System.out);
  }

  public MyConnectFour(InputStream inputStream, PrintStream outputStream) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    this.outputStream = Objects.requireNonNull(outputStream, "Output stream is null");
    AiStrategy strategy = new CheckOneTurnWinConditionStrategy(victoryCondition);
    players = Arrays.asList(
        new HumanPlayer(PlayerColour.RED, reader),
        new AiPlayer(PlayerColour.YELLOW, strategy)
    );
  }

  public void playGame() {
    outputStream.println("Welcome to Connect 4");
    outputStream.println("There are 2 players red and yellow, represented by r and y on the board.");
    outputStream.println("You control the red player, while yellow is controlled by the computer.");
    outputStream.println("To play the game type in the number of the column you want to drop you counter in");
    outputStream.println("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
    outputStream.println();

    board.printToConsole(outputStream);

    do {
      for (Player player : players) {
        player.takeTurn(board);
        board.printToConsole(outputStream);

        if (victoryCondition.isAchievedForPlayer(player, board)) {
          outputStream.println(player.getColour().getName() + " player wins!");
          return;
        } else if (board.isFull()) {
          outputStream.println("No more moves can be made, and no victor has emerged.");
          outputStream.println("The game is a tie!");
          return;
        }
      }
    } while (true);
  }

}