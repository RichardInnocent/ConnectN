/**
 * A player's colour.
 */
public enum PlayerColour {
  RED("Red", 'r'),
  YELLOW("Yellow", 'y'),
  BLUE("Blue", 'b'),
  GREEN("Green", 'g'),
  PURPLE("Purple", 'p'),
  ORANGE("Orange", 'o'),
  CYAN("Cyan", 'c'),
  WHITE("White", 'w');

  private final String name;
  private final char icon;

  PlayerColour(String name, char icon) {
    this.name = name;
    this.icon = icon;
  }

  /**
   * Gets the single-character icon for the colour.
   * @return The single-character icon for the colour.
   */
  public char getIcon() {
    return icon;
  }

  /**
   * Gets the user-friendly name of the icon.
   * @return The user-friendly name of the icon.
   */
  public String getName() {
    return name;
  }
}
