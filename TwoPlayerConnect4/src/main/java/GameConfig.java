import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Holds the specification for the game.
 */
public class GameConfig {

  private static final String BOARD_WIDTH_KEY = "board.width";
  private static final String BOARD_HEIGHT_KEY = "board.height";
  private static final String NUMBER_OF_PLAYERS_KEY = "players.number";
  private static final String VICTORY_COUNTERS = "players.victory.counters";
  private static final String AI_DIFFICULTY_KEY = "ai.difficulty";
  private static final String PLAYER_COLOUR_KEY_SUFFIX = ".colour";
  private static final String PLAYER_AI_SUFFIX = ".ai";
  private static final String PLAYER_AI_DIFFICULTY_SUFFIX = '.' + AI_DIFFICULTY_KEY;
  private static final String PLAYER_VICTORY_COUNTERS_SUFFIX = ".victory.counters";

  private final BoardConfiguration boardConfiguration;
  private final List<PlayerConfiguration> playerConfigurations;

  /**
   * The game specification <i>should</i> be provided using a properties file. However, to meet
   * the coursework requirements, it may be necessary to also add in a method where the game
   * can be configured using a single integer argument, i.e. the number of consecutive counters.
   * In this case, a 3-handed game of Connect N should be created, where N is the number passed in
   * @param consecutiveCounters {@code N}, as described above.
   * @throws RuntimeException Thrown if {@code consecutiveCounters < 3} or
   * {@code consecutiveCounters > 6}.
   */
  public GameConfig(int consecutiveCounters) throws RuntimeException {
    this(createPropertiesFromConsecutiveCounters(consecutiveCounters));
  }

  /**
   * As a workaround for the {@link #GameConfig(int)} constructor, a {@link Properties} instance
   * can be generated and passed into the {@link #GameConfig(Properties)} constructor as normal.
   * In this case, a 3-handed game of Connect N should be created.
   * @param consecutiveCounters {@code N}, as described above.
   * @return A valid properties instance that accurately describes a game of 3-handed Connect N.
   */
  private static Properties createPropertiesFromConsecutiveCounters(int consecutiveCounters) {
    if (consecutiveCounters < 3 || consecutiveCounters > 6) {
      throw new InvalidConfigurationException("Illegal number of counters. Must be >2 and <7");
    }
    Properties properties = new Properties();
    properties.setProperty(VICTORY_COUNTERS, Integer.toString(consecutiveCounters));
    properties.setProperty(NUMBER_OF_PLAYERS_KEY, "3");
    return properties;
  }

  /**
   * Creates a new game configuration based on the specified properties.
   * @param properties The properties to build the game configuration from.
   * @throws RuntimeException Thrown if the configuration specified in the {@code properties}
   * instance is invalid.
   */
  public GameConfig(Properties properties) throws RuntimeException{
    PropertiesReader propertiesReader = new PropertiesReader(properties);
    this.boardConfiguration = createBoardConfiguration(propertiesReader);
    this.playerConfigurations = createPlayerConfigurations(propertiesReader);
  }

  /**
   * Gets the specification for the game board.
   * @return The specification for the game board.
   */
  public BoardConfiguration getBoardConfiguration() {
    return boardConfiguration;
  }

  /**
   * Gets the specifications for each player.
   * @return The specifications for each player.
   */
  public List<PlayerConfiguration> getPlayerConfigurations() {
    return new ArrayList<>(playerConfigurations);
  }

  private BoardConfiguration createBoardConfiguration(PropertiesReader propertiesReader) {
    // If the properties aren't specified, set them to the default (a 6x7 board)
    int width = propertiesReader.getInteger(BOARD_WIDTH_KEY).orElse(6);
    int height = propertiesReader.getInteger(BOARD_HEIGHT_KEY).orElse(7);
    return BoardConfiguration.forDimensions(new Dimensions(width, height));
  }

  /**
   * Creates the configuration for the players. The number of players created is determined by the
   * value specified in the {@code players.number} value (which defaults to 2 if not specified).
   * Any additional players specified in the config file are ignored, so, for example, if the
   * configuration specifies {@code players.number=3}, any configuration relating to {@code player4}
   * would be ignored.
   * @param propertiesReader The mechanism for reading the properties.
   * @return The player specifications.
   * @throws RuntimeException Thrown if the configuration for the player is invalid.
   */
  private List<PlayerConfiguration> createPlayerConfigurations(PropertiesReader propertiesReader) {
    int numberOfPlayers = getNumberOfPlayers(propertiesReader);

    // Loop through each player and create their configuration as specified in the config
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

    // Complete the player configurations and build them
    List<PlayerConfiguration> playerConfigurations =
        createPlayerConfigurations(playerConfigBuilders, propertiesReader);

    // Ensure the player victory conditions specified are valid, e.g. is it theoretically possible
    // for them to achieve victory on a board of this size?
    validatePlayerVictoryConditions(playerConfigurations);

    return playerConfigurations;
  }

  /**
   * Gets the number of players in the config, or 2 if not specified.
   * @param propertiesReader The mechanism for retrieving values from the configuration file.
   * @return The number of players in the game.
   * @throws InvalidConfigurationException Thrown if the number of players has been incorrectly
   * specified, or if there aren't enough colours to support the number of players specified.
   */
  private int getNumberOfPlayers(PropertiesReader propertiesReader)
      throws RuntimeException {
    int minPlayers = 2;
    int maxPlayers = PlayerColour.values().length;

    int numberOfPlayers = propertiesReader.getInteger(NUMBER_OF_PLAYERS_KEY).orElse(minPlayers);

    if (numberOfPlayers < minPlayers || numberOfPlayers > maxPlayers) {
      throw new InvalidConfigurationException(
          "Illegal number of players - number of players must be between " + minPlayers + " and "
              + maxPlayers + " (inclusive) on a board of size " + boardConfiguration.getDimensions()
      );
    }

    return numberOfPlayers;
  }

  /**
   * Creates the player configurations. Note that this only returns a builder instance as the
   * returned player configurations are likely to be incomplete. For example, there's no
   * requirement that the config file specifies every players' colour. In this case, players that
   * have a received an explicit colour will have it added to their specification in this method.
   * The remaining players should have their colours allocated depending on the remaining available
   * colours after this method has been invoked.
   * @param playerNumber The player number (player 1 is the first player).
   * @param propertiesReader The instance used to retrieve values from the properties file.
   * @return The (potentially incomplete) configuration for a player.
   * @throws RuntimeException Thrown if the one or more values have been incorrectly specified in
   * the properties file.
   */
  private PlayerConfiguration.Builder createPlayerConfigurationBuilder(
      int playerNumber,
      PropertiesReader propertiesReader) throws RuntimeException {
    // The prefix is playerN, where N is the player number
    String playerPrefix = "player" + playerNumber;

    PlayerConfiguration.Builder configBuilder = PlayerConfiguration.builder();

    // If the player has a colour specified, add it the specification
    getPlayerColour(playerPrefix + PLAYER_COLOUR_KEY_SUFFIX, propertiesReader)
        .ifPresent(configBuilder::setColour);

    // If the player is designated as a human/computer player, add it the specification
    propertiesReader.getBoolean(playerPrefix + PLAYER_AI_SUFFIX)
                    .ifPresent(configBuilder::setComputerPlayer);

    // If the player has a difficulty specified, add it the specification
    getDifficulty(playerPrefix + PLAYER_AI_DIFFICULTY_SUFFIX, propertiesReader)
        .ifPresent(configBuilder::setDifficulty);

    // If the player has a victory condition specified, add it to the specification
    getConsecutiveCounterVictoryCondition(
        playerPrefix + PLAYER_VICTORY_COUNTERS_SUFFIX, propertiesReader)
        .ifPresent(configBuilder::setVictoryCondition);

    return configBuilder;
  }

  /**
   * Takes the existing player configurations as they were specified in the properties file,
   * completes them (e.g. adding a colour or a difficulty if not previously specified), and builds
   * the configurations.
   * @param configBuilders The player configurations as specified in the properties file.
   * @param propertiesReader The instance used to read properties (used to read the default values).
   * @return Completed player configurations.
   * @throws RuntimeException Thrown if the default difficulty or victory condition are incorrectly
   * specified in the properties file, or if two or more players share a colour in the
   * configuration.
   */
  private List<PlayerConfiguration> createPlayerConfigurations(
      List<PlayerConfiguration.Builder> configBuilders, PropertiesReader propertiesReader)
      throws RuntimeException {

    // Get the default difficulty that should be used when no difficulty has been specified
    Difficulty defaultDifficulty =
        getDifficulty(AI_DIFFICULTY_KEY, propertiesReader).orElse(Difficulty.MODERATE);

    // Gets the default victory condition that should be used when no victory condition has been
    // specified
    ConsecutiveCountersVictoryCondition defaultVictoryCondition =
        getConsecutiveCounterVictoryCondition(VICTORY_COUNTERS, propertiesReader)
            .orElse(new ConsecutiveCountersVictoryCondition(4));

    // Get a list of all colours that are available - we can use this to check for duplicate colours
    // and assign spare colours to player configurations with no colour
    List<PlayerColour> availableColours = new ArrayList<>(Arrays.asList(PlayerColour.values()));

    // Count the number of human players specified. While players should default to computer
    // players, make sure that at least one of them is human unless the config states explicitly
    // that they should all be computer.
    int numberOfHumanPlayers = 0;

    // Loop through each player, updating the available colours and the number of human players
    for (int playerNumber = 1; playerNumber < configBuilders.size(); playerNumber++) {
      PlayerConfiguration.Builder configBuilder = configBuilders.get(playerNumber-1);
      if (configBuilder.getComputerPlayer() != null && !configBuilder.getComputerPlayer()) {
        numberOfHumanPlayers++;
      }

      PlayerColour colour = configBuilder.getColour();
      if (colour != null) {
        // Player config has its colour stated explicitly - will this produce duplicates?
        if (!availableColours.remove(colour)) {
          // Colour is a duplicate - how would we tell the counters apart on the board?
          throw new InvalidConfigurationException(
              "Player " + playerNumber + " cannot use colour " + colour
                  + " as this colour is already in use"
          );
        }
      }
    }

    // Complete the player configurations, adding a colour and difficulty etc
    for (PlayerConfiguration.Builder configBuilder : configBuilders) {
      // Always endeavour to have at least one human player, unless specified by config
      if (numberOfHumanPlayers == 0 && configBuilder.getComputerPlayer() == null) {
        configBuilder.setComputerPlayer(false);
        numberOfHumanPlayers++; // Make sure we don't turn the next player into a human too!
      }
      if (configBuilder.getVictoryCondition() == null) {
        configBuilder.setVictoryCondition(defaultVictoryCondition);
      }
      if (configBuilder.getDifficulty() == null) {
        configBuilder.setDifficulty(defaultDifficulty);
      }
      if (configBuilder.getColour() == null) {
        configBuilder.setColour(availableColours.remove(0));
      }
    }

    return configBuilders
        .stream()
        .map(PlayerConfiguration.Builder::build)
        .collect(Collectors.toList());
  }

  /**
   * <p>Checks that the player victory conditions are possible - a player can't connect 5 in a row
   * on a 3x3 board! Check that the number of consecutive counters doesn't exceed the minimum
   * dimension on the board.</p>
   * <p>It's theoretically possible to validate against the largest dimension as it's still possible
   * to connect 7 in a row on a 6x7 board. However, this is part of the coursework spec and
   * shouldn't be added as a special case.</p>
   * @param playerConfigurations The player configurations.
   * @throws InvalidConfigurationException Thrown if configuration for any of the players is
   * impossible.
   */
  private void validatePlayerVictoryConditions(List<PlayerConfiguration> playerConfigurations)
      throws InvalidConfigurationException {
    Dimensions boardDimensions = boardConfiguration.getDimensions();

    int smallestDimension = Math.min(boardDimensions.getWidth(), boardDimensions.getHeight());

    for (PlayerConfiguration playerConfiguration : playerConfigurations) {
      // This only applies to ConsecutiveCountersVictoryConditions
      if (playerConfiguration.getVictoryCondition() instanceof ConsecutiveCountersVictoryCondition) {
        ConsecutiveCountersVictoryCondition victoryCondition =
            (ConsecutiveCountersVictoryCondition) playerConfiguration.getVictoryCondition();
        // Is the victory condition possible?
        if (victoryCondition.getConsecutiveCountersRequired() > smallestDimension) {
          // No! The configuration is invalid.
          throw new InvalidConfigurationException(
              "Consecutive counters required cannot be > " + smallestDimension);
        }
      }
    }
  }

  /**
   * Gets the player colour that corresponds to the value for the given key.
   * @param key The key.
   * @param propertiesReader The instance used to read values from the properties file.
   * @return A player colour, or an empty optional if no colour has been specified.
   */
  private Optional<PlayerColour> getPlayerColour(String key, PropertiesReader propertiesReader) {
    return propertiesReader.get(key, PlayerColour::valueOf);
  }

  /**
   * Gets the difficulty that corresponds to the value for the given key.
   * @param key The key.
   * @param propertiesReader The instance used to read values from the properties file.
   * @return A difficulty, or an empty optional if no difficulty has been specified.
   */
  private Optional<Difficulty> getDifficulty(String key, PropertiesReader propertiesReader) {
    return propertiesReader.get(key, Difficulty::fromName);
  }

  /**
   * Gets a victory condition where the player must connect a number of consecutive counters.
   * @param key The key.
   * @param propertiesReader The instance used to read values from the properties file.
   * @return A victory condition, or an empty optional if no victory condition has been specified.
   */
  private Optional<ConsecutiveCountersVictoryCondition> getConsecutiveCounterVictoryCondition(
      String key, PropertiesReader propertiesReader) {
    return propertiesReader.getInteger(key).map(ConsecutiveCountersVictoryCondition::new);
  }

}
