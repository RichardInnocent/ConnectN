import java.util.Objects;

/**
 * A set of dimensions with a width and height.
 */
public final class Dimensions {

  private final int width;
  private final int height;

  /**
   * Creates a new dimensions instance with the given width and height.
   * @param width The width.
   * @param height The height.
   */
  public Dimensions(int width, int height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Gets the width.
   * @return The width.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the height.
   * @return The height.
   */
  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    return "[" + width + ", " + height + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dimensions that = (Dimensions) o;
    return width == that.width && height == that.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }
}
