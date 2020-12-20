import java.util.Objects;

/**
 * Responsible for displaying instructions to the user.
 */
public abstract class Instructions implements ViewableObject {

  private final GameConfig gameConfig;

  protected Instructions(GameConfig gameConfig) throws NullPointerException {
    this.gameConfig = Objects.requireNonNull(gameConfig, "Game config is null");
  }

  /**
   * Prints the instructions.
   * @param view The view that displays output to the user.
   */
  @Override
  public void view(View view) {
    printInstructions(view, gameConfig);
  }

  /**
   * Prints the instructions to the user.
   * @param view The view that displays output to the user.
   * @param gameConfig The game config that should be used to generate the instructions.
   */
  protected abstract void printInstructions(View view, GameConfig gameConfig);
}
