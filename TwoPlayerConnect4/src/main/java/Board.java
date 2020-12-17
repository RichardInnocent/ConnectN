import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The game board. The board can be mutated, and provides methods to report on its position, but is
 * not responsible for determining the outcome of any given move.
 */
public class Board implements PrintableObject, Copyable<Board> {

  private final int width;
  private final int height;
  private final Column[] columns;

  /**
   * Creates a new game board.
   * @param width The width of the board, i.e. the number of columns.
   * @param height The height of the board, i.e. the number of counters that can be stacked on top
   * of one another before the column is full.
   * @throws IllegalArgumentException Thrown if {@code width < 3} or {@code height < 3}.
   */
  public Board(int width, int height) throws IllegalArgumentException {
    if (width < 3) {
      throw new IllegalArgumentException("Height of board cannot be less than 3");
    }
    if (height < 3) {
      throw new IllegalArgumentException("Width of board cannot be less than 3");
    }
    this.width = width;
    this.height = height;
    this.columns = new Column[width];
    populateColumns(height);
  }

  /**
   * Copy constructor that creates a semi-deep copy of the board. The new board can be modified
   * without affecting this board, although the player references remain the same.
   * @param board The board to copy.
   */
  private Board(Board board) {
    this.width = board.width;
    this.height = board.height;
    this.columns = new Column[board.columns.length];
    for (int i = 0; i < this.columns.length; i++) {
      this.columns[i] = board.columns[i].copy();
    }
  }

  // Creates the columns
  private void populateColumns(int height) {
    for (int i = 0; i < columns.length; i++) {
      columns[i] = new Column(height);
    }
  }

  /**
   * Gets the width of the board, i.e. the number of columns.
   * @return The width of the board.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height of the board, i.e. the number of counters that can be stacked on top of one
   * another before the column is full.
   * @return The height of the board.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Places the player's counter in the given column.
   * @param player The player that owns the counter being placed. The column numbers match that
   * shown in the UI, where the column numbers start at 1, with 1 being the left-most column.
   * @param columnNumber The column number to place the counter in.
   * @throws InvalidMoveException Thrown if the the given column does not exist, or the column is
   * full.
   */
  public void placePlayerCounterInColumn(Player player, int columnNumber)
      throws InvalidMoveException {
    if (columnNumber < 1 || columnNumber > columns.length) {
      // Invalid column number
      throw new InvalidMoveException("There's no column " + columnNumber);
    }
    columns[columnNumber-1].addCounter(player);
  }

  /**
   * Determines whether the board is full such that no more counters can be placed.
   * @return {@code true} if the board is full.
   */
  public boolean isFull() {
    return Arrays.stream(columns).allMatch(Column::isFull);
  }

  /**
   * Gets a list of all of the column numbers of columns that are not full.
   * @return A list of all columns numbers of columns that are not full. If {@link #isFull()}, this
   * will return an empty list.
   */
  public List<Integer> getColumnsWithSpareCapacity() {
    return IntStream.rangeClosed(0, columns.length-1) // Loop over the columns
                    .filter(number -> !columns[number].isFull()) // Remove full columns
                    .map(index -> index + 1) // Map to the column numbers shown in the UI
                    .boxed()
                    .collect(Collectors.toList());
  }

  @Override
  public void printToConsole(IOHandler ioHandler) {
    printBoard(ioHandler);
    printColumnNumbers(ioHandler);
  }

  /**
   * Prints the board to the I/O specified. This excludes column numbers.
   * @param ioHandler The I/O where the board should be printed.
   */
  private void printBoard(IOHandler ioHandler) {
    // Loop through each row
    for (int row = height-1; row >= 0; row--) {
      // Loop through each column
      for (int column = 0; column < width; column++) {
        // Get the player icon to print at that location - empty elements should be blank
        char icon = columns[column]
            .getOwnerOfCounterAtIndex(row)
            .map(player -> player.getColour().getIcon())
            .orElse(' ');
        // Print the icon, separated by a pipe
        ioHandler.print("| " + icon + " ");
      }
      // Print the pipe at the end of the row
      ioHandler.printLine("|");
    }
  }

  /**
   * Prints the column numbers of the board. This should be used in conjunction with
   * {@link #printBoard(IOHandler)}.
   * @param ioHandler The I/O where the column numbers should be printed.
   */
  private void printColumnNumbers(IOHandler ioHandler) {
    // Print the column numbers
    for (int columnNumber = 1; columnNumber <= width; columnNumber++) {
      ioHandler.print("| " + columnNumber + " ");
    }

    // Print the pipe at the end of the row
    ioHandler.printLine("|\n");
  }

  /**
   * Gets the owner of the player at a given position on the board.
   * @param rowNumber The row number. Row numbers start at the bottom of the board at 1 and
   * increment up the rows.
   * @param columnNumber The column number. The column number starts at 1 at the left-hand side,
   * and increments to the right.
   * @return The owner of the counter at the given position, or an empty {@link Optional} if there's
   * no counter.
   * @throws IllegalArgumentException Thrown if {@code rowNumber} or {@code columnNumber} are out
   * of bounds.
   */
  public Optional<Player> getOwnerOfCounterAt(int rowNumber, int columnNumber)
      throws IllegalArgumentException {
    if (rowNumber < 1 || rowNumber > height) {
      throw new IllegalArgumentException("Invalid row number " + rowNumber);
    }
    if (columnNumber < 1 || columnNumber > width) {
      throw new IllegalArgumentException("Invalid column number " + columnNumber);
    }
    return columns[columnNumber-1].getOwnerOfCounterAtIndex(rowNumber-1);
  }

  /**
   * Gets a list of all players that have counters on the board.
   * @return A list of all players that currently have counters on the board.
   */
  public Collection<Player> getAllPlayersOnBoard() {
    return Arrays.stream(columns)
                 .map(Column::getPlayersInColumn)
                 .flatMap(Collection::stream)
                 .collect(Collectors.toSet());
  }

  /**
   * Copies the board, creating a semi-deep copy that can be modified without affecting this board.
   * Note that the underlying player references are unchanged.
   * @return A copy of this board.
   */
  @Override
  public Board copy() {
    return new Board(this);
  }
}
