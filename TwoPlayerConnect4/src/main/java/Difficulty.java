import java.util.function.Function;

/**
 * A difficulty that the AI can be set to. Each difficulty selects an appropriate strategy given
 * the current victory conditions its aiming to achieve. This could have been an enum, but this
 * would prevent us from mocking the underlying strategy in tests (without using something like
 * Powermockito).
 */
public class Difficulty {

  public static Difficulty EASY =
      new Difficulty("Easy", victoryConditions -> new RandomPlacementStrategy());
  public static Difficulty MODERATE =
      new Difficulty("Moderate", CheckOneTurnWinConditionStrategy::new);

  private final String name;
  private final Function<VictoryCondition, AIStrategy> strategyFunction;

  private Difficulty(String name, Function<VictoryCondition, AIStrategy> strategyFunction) {
    this.name = name;
    this.strategyFunction = strategyFunction;
  }

  /**
   * Gets the user-friendly name of the difficulty.
   * @return The user-friendly name of the difficulty.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the AI strategy that should be used given the specified victory condition.
   * @param victoryCondition The victory condition that the AI strategy should work towards.
   * @return The appropriate strategy for this difficulty level.
   */
  public AIStrategy getStrategy(VictoryCondition victoryCondition) {
    return strategyFunction.apply(victoryCondition);
  }

  /**
   * Gets the difficulty with the given name, ignoring case.
   * @param name The difficulty with the given name.
   * @return The difficulty with the given name.
   * @throws IllegalArgumentException Thrown if no difficulty with the given name exists.
   */
  public static Difficulty fromName(String name) throws IllegalArgumentException {
    // Would have been nice if this was an enum so we could use valueOf instead, but this would
    // prevent us from creating dummy difficulties in test cases without something like PowerMockito
    if (EASY.name.equalsIgnoreCase(name)) {
      return EASY;
    }
    if (MODERATE.name.equalsIgnoreCase(name)) {
      return MODERATE;
    }
    throw new IllegalArgumentException("No difficulty found with the name " + name);
  }

}
