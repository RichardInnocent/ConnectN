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

  public char getIcon() {
    return icon;
  }

  public String getName() {
    return name;
  }
}
