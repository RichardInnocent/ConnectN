import java.io.IOException;

public class HumanPlayer extends AbstractPlayer {

  public HumanPlayer(PlayerColour colour) throws NullPointerException {
    super(colour);
  }

  @Override
  public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {
    boolean turnTaken = false;

    while (!turnTaken) {
      try {
        int chosenColumn = getColumnInput(ioHandler);
        board.placePlayerCounterInColumn(this, chosenColumn);
        turnTaken = true;
      } catch (InvalidMoveException e) {
        ioHandler.printLine(e.getMessage());
      }
    }
  }

  private int getColumnInput(IOHandler ioHandler) throws InvalidMoveException {
    String input = getInput(ioHandler);
    try {
      return Integer.parseInt(input); // Null will be thrown as a NumberFormatException
    } catch (NumberFormatException e) {
      throw new InvalidMoveException(input + " is not a valid column number");
    }
  }

  private String getInput(IOHandler ioHandler) {
    try {
      return ioHandler.readLine();
    } catch (IOException e) {
      ioHandler.printLine("Could not read input");
      return null;
    }
  }

  @Override
  public boolean isHuman() {
    return true;
  }
}
