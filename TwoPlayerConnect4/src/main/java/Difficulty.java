import java.util.function.Function;

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

  public String getName() {
    return name;
  }

  public AIStrategy getStrategy(VictoryCondition victoryCondition) {
    return strategyFunction.apply(victoryCondition);
  }

  public static Difficulty fromName(String name) {
    if (EASY.name.equalsIgnoreCase(name)) {
      return EASY;
    }
    if (MODERATE.name.equalsIgnoreCase(name)) {
      return MODERATE;
    }
    throw new IllegalArgumentException("No difficulty found with the name " + name);
  }

}
