import java.util.Objects;

public class BoardConfiguration {

  private final Dimensions dimensions;

  private BoardConfiguration(Dimensions dimensions) throws NullPointerException {
    this.dimensions = Objects.requireNonNull(dimensions, "Dimensions are null");
  }

  public Dimensions getDimensions() {
    return dimensions;
  }

  public static BoardConfiguration forDimensions(Dimensions dimensions)
      throws NullPointerException {
    return new BoardConfiguration(dimensions);
  }

}
