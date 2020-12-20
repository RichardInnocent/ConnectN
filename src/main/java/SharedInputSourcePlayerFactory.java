import java.io.BufferedReader;
import java.util.Objects;

/**
 * A factory that creates {@link Player} instances. All human players created using this factory
 * will share the same input source.
 */
public class SharedInputSourcePlayerFactory implements PlayerFactory {

  private final BufferedReader inputReader;

  /**
   * Creates a new factory that can create {@link Player} instances from a specification. All human
   * players created using this factory will share the same input source.
   * @param inputReader The input source that will be shared by all human players.
   * @throws NullPointerException Thrown of {@code inputReader == null}.
   */
  public SharedInputSourcePlayerFactory(BufferedReader inputReader) throws NullPointerException {
    this.inputReader = Objects.requireNonNull(inputReader, "Input reader is null");
  }

  /**
   * Creates a player.
   * @param config The specification for the player.
   * @return The player.
   */
  @Override
  public Player create(PlayerConfiguration config) {
    if (config.isComputerPlayer()) {
      return new AIPlayer(config.getColour(),config.getVictoryCondition(), config.getDifficulty());
    } else {
      return new HumanPlayer(
          config.getColour(),
          config.getVictoryCondition(),
          inputReader // Give each user the same input source
      );
    }
  }

}
