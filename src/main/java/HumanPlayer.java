import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * A player on the board that is controlled by a person, not a computer.
 */
public class HumanPlayer extends AbstractPlayer {

  private final BufferedReader inputReader;

  /**
   * Creates a player on the board that is controlled by a person, not a computer.
   * @param colour The player colour.
   * @param victoryCondition The condition that the player must meet to be victorious.
   * @param reader The method of receiving user input.
   * @throws NullPointerException Thrown if {@code colour == null},
   * {@code victoryCondition == null} or {@code reader == null}.
   */
  public HumanPlayer(PlayerColour colour, VictoryCondition victoryCondition, BufferedReader reader)
      throws NullPointerException {
    super(colour, victoryCondition);
    this.inputReader = Objects.requireNonNull(reader);
  }

  @Override
  public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {
    ioHandler.printLine(getColour().getName() + " player it's your turn!");
    // Continually retrieve input until the user enters a valid column number
    while (true) {
      try {
        int chosenColumn = getColumnInput(ioHandler);
        board.placePlayerCounterInColumn(this, chosenColumn);
        // A valid move has been taken - exit out of the while loop
        return;
      } catch (InvalidMoveException e) {
        ioHandler.printLine(e.getMessage());
      }
    }
  }

  /**
   * Gets the column number input by the user.
   * @param ioHandler The I/O method used to retrieve user input.
   * @return The column number input by the user. There's no guarantee that this is a valid on a
   * given board.
   * @throws InvalidMoveException Thrown if the user's input cannot be parsed to a number.
   */
  private int getColumnInput(IOHandler ioHandler) throws InvalidMoveException {
    ioHandler.print("Choose a column: ");
    String input = getInput(ioHandler);
    try {
      // Hopefully the user's input is a number so parse it and return it
      return Integer.parseInt(input); // Null will be thrown as a NumberFormatException
    } catch (NumberFormatException e) {
      // If the user's input is not a number, it's not a valid move.
      throw new InvalidMoveException(input + " is not a valid column number");
    }
  }

  /**
   * Gets the user's input directly from the terminal.
   * @param ioHandler The I/O method to retrieve user input.
   * @return The user's input, or {@code null} if the an {@link IOException} was encountered.
   */
  private String getInput(IOHandler ioHandler) {
    try {
      return inputReader.readLine();
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
