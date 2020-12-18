import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameConfig {

  private final BoardConfiguration boardConfiguration;
  private final List<PlayerConfiguration> playerConfigurations;

  public GameConfig(Properties properties) {
    PropertiesReader propertiesReader = new PropertiesReader(properties);
    this.boardConfiguration = createBoardConfiguration(propertiesReader);
    this.playerConfigurations = createPlayerConfigurations(propertiesReader);
  }

  public BoardConfiguration getBoardConfiguration() {
    return boardConfiguration;
  }

  public List<PlayerConfiguration> getPlayerConfigurations() {
    return new ArrayList<>(playerConfigurations);
  }

  private BoardConfiguration createBoardConfiguration(PropertiesReader propertiesReader) {
    int width = propertiesReader.getInteger("board.width").orElse(6);
    int height = propertiesReader.getInteger("board.height").orElse(7);
    return BoardConfiguration.forDimensions(new Dimensions(width, height));
  }

  private List<PlayerConfiguration> createPlayerConfigurations(PropertiesReader propertiesReader) {
    int numberOfPlayers = getNumberOfPlayers(propertiesReader);

    List<PlayerConfiguration.Builder> playerConfigBuilders =
        IntStream
            .range(1, numberOfPlayers+1)
            .mapToObj(
                playerNo ->
                    createPlayerConfigurationBuilder(
                        playerNo, propertiesReader
                    )
            )
            .collect(Collectors.toList());

    List<PlayerConfiguration> playerConfigurations =
        createPlayerConfigurations(playerConfigBuilders, propertiesReader);

    validatePlayerVictoryConditions(playerConfigurations);

    return playerConfigurations;
  }

  private int getNumberOfPlayers(PropertiesReader propertiesReader)
      throws InvalidConfigurationException {
    int minPlayers = 2;
    int maxPlayers = PlayerColour.values().length;

    int numberOfPlayers = propertiesReader.getInteger("players.number").orElse(minPlayers);

    if (numberOfPlayers < minPlayers || numberOfPlayers > maxPlayers) {
      throw new IllegalArgumentException(
          "Illegal number of players - number of players must be between " + minPlayers + " and "
              + maxPlayers + " (inclusive)"
      );
    }

    return numberOfPlayers;
  }

  private PlayerConfiguration.Builder createPlayerConfigurationBuilder(
      int playerNumber,
      PropertiesReader propertiesReader) {
    String nameInConfig = "player" + playerNumber;

    PlayerConfiguration.Builder configBuilder = PlayerConfiguration.builder();

    getPlayerColour(nameInConfig + ".colour", propertiesReader).ifPresent(configBuilder::setColour);

    propertiesReader.getBoolean(nameInConfig + ".ai")
                    .ifPresent(configBuilder::setComputerPlayer);

    getDifficulty(nameInConfig + ".ai.difficulty", propertiesReader)
        .ifPresent(configBuilder::setDifficulty);

    getConsecutiveCounterVictoryCondition(nameInConfig + ".victory.counters", propertiesReader)
        .ifPresent(configBuilder::setVictoryCondition);

    return configBuilder;
  }

  private Optional<PlayerColour> getPlayerColour(String key, PropertiesReader propertiesReader) {
    return propertiesReader.get(key, PlayerColour::valueOf);
  }

  private Optional<Difficulty> getDifficulty(String key, PropertiesReader propertiesReader) {
    return propertiesReader.get(key, Difficulty::fromName);
  }

  private Optional<ConsecutiveCountersVictoryCondition> getConsecutiveCounterVictoryCondition(
      String key, PropertiesReader propertiesReader) {
    return propertiesReader.getInteger(key).map(ConsecutiveCountersVictoryCondition::new);
  }

  private List<PlayerConfiguration> createPlayerConfigurations(
      List<PlayerConfiguration.Builder> configBuilders, PropertiesReader propertiesReader) {
    Difficulty defaultDifficulty =
        getDifficulty("ai.difficulty", propertiesReader).orElse(Difficulty.MODERATE);

    ConsecutiveCountersVictoryCondition defaultVictoryCondition =
        getConsecutiveCounterVictoryCondition("players.victory.counters", propertiesReader)
            .orElse(new ConsecutiveCountersVictoryCondition(4));

    List<PlayerColour> availableColours = new ArrayList<>(Arrays.asList(PlayerColour.values()));
    int numberOfHumanPlayers = 0;

    for (int playerNumber = 1; playerNumber < configBuilders.size(); playerNumber++) {
      PlayerConfiguration.Builder configBuilder = configBuilders.get(playerNumber-1);
      if (configBuilder.getComputerPlayer() != null && !configBuilder.getComputerPlayer()) {
        numberOfHumanPlayers++;
      }
      PlayerColour colour = configBuilder.getColour();
      if (colour != null) {
        if (!availableColours.remove(colour)) {
          throw new IllegalArgumentException(
              "Player " + playerNumber + " cannot use colour " + colour
                  + " as this colour is already in use"
          );
        }
      }
    }

    for (PlayerConfiguration.Builder configBuilder : configBuilders) {
      // Always endeavour to have at least one human player, unless specified by config
      if (numberOfHumanPlayers == 0 && configBuilder.getComputerPlayer() == null) {
        configBuilder.setComputerPlayer(false);
        numberOfHumanPlayers++;
      }
      if (configBuilder.getVictoryCondition() == null) {
        configBuilder.setVictoryCondition(defaultVictoryCondition);
      }
      if (configBuilder.getDifficulty() == null) {
        configBuilder.setDifficulty(defaultDifficulty);
      }
      if (configBuilder.getColour() == null) {
        configBuilder.setColour(availableColours.get(0));
        availableColours.remove(0);
      }
    }

    return configBuilders
        .stream()
        .map(PlayerConfiguration.Builder::build)
        .collect(Collectors.toList());
  }

  private void validatePlayerVictoryConditions(List<PlayerConfiguration> playerConfigurations) {
    Dimensions boardDimensions = boardConfiguration.getDimensions();

    // We could theoretically validate against the largest dimension as it's possible still possible
    // to connect 7 in a row on a 6x7 board. However, I don't want to add in a special case to meet
    // the coursework guidelines of a max of 6 on a standard-sized board.
    int smallestDimension = Math.min(boardDimensions.getWidth(), boardDimensions.getHeight());

    for (PlayerConfiguration playerConfiguration : playerConfigurations) {
      if (playerConfiguration.getVictoryCondition() instanceof ConsecutiveCountersVictoryCondition) {
        ConsecutiveCountersVictoryCondition victoryCondition =
            (ConsecutiveCountersVictoryCondition) playerConfiguration.getVictoryCondition();
        if (victoryCondition.getConsecutiveCountersRequired() > smallestDimension) {
          throw new IllegalArgumentException(
              "Consecutive counters required cannot be > " + smallestDimension);
        }
      }
    }
  }

}
