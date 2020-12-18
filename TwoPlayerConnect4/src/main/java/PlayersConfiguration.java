import java.util.List;
import java.util.Objects;

public class PlayersConfiguration {

  public List<PlayerConfiguration> playerConfigurations;

  private PlayersConfiguration(List<PlayerConfiguration> playerConfigurations) {
    this.playerConfigurations = Objects.requireNonNull(playerConfigurations);
  }

  public List<PlayerConfiguration> getPlayerConfigurations() {
    return playerConfigurations;
  }

}
