import java.util.Objects;

/**
 * Abstract implementation of a player with a static (unchangeable) player colour.
 */
public abstract class ConstantColourPlayer implements Player {

  private final PlayerColour colour;

  /**
   * Creates a player with the given player colour.
   * @param colour The colour that will be used to refer to the player.
   * @throws NullPointerException Thrown if {@code colour == null}.
   */
  protected ConstantColourPlayer(PlayerColour colour) throws NullPointerException {
    this.colour = Objects.requireNonNull(colour);
  }

  @Override
  public PlayerColour getColour() {
    return colour;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConstantColourPlayer that = (ConstantColourPlayer) o;
    return colour == that.colour;
  }

  @Override
  public int hashCode() {
    return colour.hashCode();
  }
}
