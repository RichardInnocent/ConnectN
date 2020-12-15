import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class HumanPlayer extends ConstantColourPlayer {

  private final BufferedReader inputReader;

  public HumanPlayer(PlayerColour colour, BufferedReader inputReader) throws NullPointerException {
    super(colour);
    this.inputReader = Objects.requireNonNull(inputReader, "Input reader is null");
  }

  @Override
  public void takeTurn(Board board) {
    boolean turnTaken = false;

    while (!turnTaken) {
      try {
        int chosenColumn = getColumnInput();
        board.placePlayerCounterInColumn(this, chosenColumn);
        turnTaken = true;
      } catch (InvalidMoveException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private int getColumnInput() throws InvalidMoveException {
    String input = getInput();
    try {
      return Integer.parseInt(input); // Null will be thrown as a NumberFormatException
    } catch (NumberFormatException e) {
      throw new InvalidMoveException(input + " is not a valid column number");
    }
  }

  private String getInput() {
    try {
      return inputReader.readLine();
    } catch (IOException e) {
      System.err.println("Could not read input");
      return null;
    }
  }
}
