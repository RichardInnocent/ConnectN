import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Column implements Copyable<Column> {

  private final Player[] positions;

  public Column(int height) {
    this(new Player[height]);
  }

  private Column(Player[] positions) {
    this.positions = positions;
  }

  public boolean isFull() {
    return Arrays.stream(positions).allMatch(Objects::nonNull);
  }

  public void addCounter(Player owningPlayer) throws InvalidMoveException {
    for (int i = 0; i < positions.length; i++) {
      if (positions[i] == null) {
        positions[i] = owningPlayer;
        return;
      }
    }
    throw new InvalidMoveException("No counter can be placed here as the column is full");
  }

  public Optional<Player> getOwnerOfCounterAtIndex(int index) {
    return Optional.ofNullable(positions[index]);
  }

  public Collection<Player> getPlayersInColumn() {
    return Arrays.stream(positions).filter(Objects::nonNull).collect(Collectors.toSet());
  }

  @Override
  public Column copy() {
    return new Column(Arrays.copyOf(positions, positions.length));
  }
}
