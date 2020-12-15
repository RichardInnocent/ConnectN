import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board implements PrintableObject, Copyable<Board> {

  private final int width;
  private final int height;
  private final Column[] columns;

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

  private Board(Board board) {
    this.width = board.width;
    this.height = board.height;
    this.columns = new Column[board.columns.length];
    for (int i = 0; i < this.columns.length; i++) {
      this.columns[i] = board.columns[i].copy();
    }
  }

  private void populateColumns(int height) {
    for (int i = 0; i < columns.length; i++) {
      columns[i] = new Column(height);
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void placePlayerCounterInColumn(Player player, int columnNumber)
      throws InvalidMoveException {
    if (columnNumber < 1 || columnNumber > columns.length) {
      throw new InvalidMoveException("There's no column " + columnNumber);
    }
    columns[columnNumber-1].addCounter(player);
  }

  public boolean isFull() {
    return Arrays.stream(columns).allMatch(Column::isFull);
  }

  public List<Integer> getColumnsWithSpareCapacity() {
    return IntStream.rangeClosed(0, columns.length-1)
                    .filter(number -> !columns[number].isFull())
                    .map(index -> index + 1)
                    .boxed()
                    .collect(Collectors.toList());
  }

  @Override
  public void printToConsole(PrintStream out) {
    for (int row = height-1; row >= 0; row--) {
      for (int column = 0; column < width; column++) {
        char icon = columns[column]
            .getOwnerOfCounterAtIndex(row)
            .map(player -> player.getColour().getIcon())
            .orElse(' ');
        out.print("| " + icon + " ");
      }
      out.println("|");
    }
    for (int columnNumber = 1; columnNumber <= width; columnNumber++) {
      out.print("| " + columnNumber + " ");
    }
    out.println("|\n");
  }

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

  public Collection<Player> getAllPlayersOnBoard() {
    return Arrays.stream(columns)
                 .map(Column::getPlayersInColumn)
                 .flatMap(Collection::stream)
                 .collect(Collectors.toSet());
  }

  @Override
  public Board copy() {
    return new Board(this);
  }
}
