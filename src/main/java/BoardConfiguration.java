import java.util.Objects;

/**
 * The specification for creating a {@link Board}.
 */
public class BoardConfiguration {

  private final Dimensions dimensions;

  private BoardConfiguration(Dimensions dimensions) throws NullPointerException {
    this.dimensions = Objects.requireNonNull(dimensions, "Dimensions are null");
  }

  /**
   * Gets the dimensions of the board.
   * @return The dimensions of the board.
   */
  public Dimensions getDimensions() {
    return dimensions;
  }

  /**
   * Creates a configuration for the given dimensions.
   * @param dimensions The dimensions of the board.
   * @return A new board specification.
   * @throws NullPointerException Thrown of {@code dimensions == null}.
   */
  public static BoardConfiguration forDimensions(Dimensions dimensions)
      throws NullPointerException {
    return new BoardConfiguration(dimensions);
  }

}
