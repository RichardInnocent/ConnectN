import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A column within the board.
 */
public class Column implements Copyable<Column> {

  private final Player[] positions;

  /**
   * Creates the column with the given height.
   * @param height The height of the board, i.e. the level of columns.
   */
  public Column(int height) {
    this(new Player[height]);
  }

  private Column(Player[] positions) {
    this.positions = positions;
  }

  /**
   * Determines if the column is full, i.e. no more counters can be placed in this column.
   * @return {@code true} if the column is full.
   */
  public boolean isFull() {
    return Arrays.stream(positions).allMatch(Objects::nonNull);
  }

  /**
   * Adds a counter to the column. The counter will drop to the lowest empty slot.
   * @param owningPlayer The player that owns the counter being placed.
   * @throws InvalidMoveException Thrown if the column is full.
   */
  public void addCounter(Player owningPlayer) throws InvalidMoveException {
    for (int i = 0; i < positions.length; i++) {
      if (positions[i] == null) {
        positions[i] = owningPlayer;
        return;
      }
    }
    throw new InvalidMoveException("No counter can be placed here as the column is full");
  }

  /**
   * Gets the owner of the counter at the given index.
   * @param index The row number. Unlike the implementation in {@link Board}, the index here starts
   * at 0. This is by design as it matches how {@code Board} stores the column numbers internally.
   * This class serves as a component of {@code Board} and should not be exposed outside.
   * @return The owner of the board at the given index, or an empty optional if there is not counter
   * at the given position.
   * @throws IndexOutOfBoundsException Thrown if the given index does not exist.
   */
  public Optional<Player> getOwnerOfCounterAtIndex(int index) throws IndexOutOfBoundsException {
    return Optional.ofNullable(positions[index]);
  }

  /**
   * Gets a collection of all of the players that have a counter within this column.
   * @return A collection of all of the players that have a counter within this column.
   */
  public Collection<Player> getPlayersInColumn() {
    return Arrays.stream(positions).filter(Objects::nonNull).collect(Collectors.toSet());
  }

  /**
   * Creates a semi-deep copy of the column. The returned column can be modified without affecting
   * this column. Note that the player references remain the same.
   * @return A copy of this instance.
   */
  @Override
  public Column copy() {
    return new Column(Arrays.copyOf(positions, positions.length));
  }
}
