import java.util.Objects;

/**
 * Contains the configuration used to create a player.
 */
public class PlayerConfiguration {

  private final PlayerColour playerColour;
  private final boolean computerPlayer;
  private final Difficulty difficulty;
  private final VictoryCondition victoryCondition;

  // Create the player configuration from the builder.
  private PlayerConfiguration(Builder builder) throws NullPointerException {
    this.playerColour = Objects.requireNonNull(builder.colour, "Player colour not set");
    // Defaults to computer player if not specified
    this.computerPlayer = builder.computerPlayer == null || builder.computerPlayer;
    this.difficulty =
        this.computerPlayer ?
            Objects.requireNonNull(builder.difficulty, "Difficulty not specified") : null;
    this.victoryCondition =
        Objects.requireNonNull(builder.victoryCondition, "Victory condition is null");
  }

  /**
   * Gets the colour of the player.
   * @return The colour of the player.
   */
  public PlayerColour getColour() {
    return playerColour;
  }

  /**
   * Determines whether or not the computer should be played by a computer.
   * @return {@code true} if the player is a computer player.
   */
  public boolean isComputerPlayer() {
    return computerPlayer;
  }

  /**
   * Gets the difficulty of the player (only applicable for computer players).
   * @return The difficulty of the player.
   * @see #isComputerPlayer()
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  /**
   * Gets the victory condition that will allow the player to win the game.
   * @return The victory condition that will allow the player to win the game.
   */
  public VictoryCondition getVictoryCondition() {
    return victoryCondition;
  }

  /**
   * Creates a new builder for {@link PlayerConfiguration} instances.
   * @return A new builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * A builder for {@link PlayerConfiguration} instances.
   */
  public static class Builder {

    private PlayerColour colour;
    private Boolean computerPlayer;
    private Difficulty difficulty;
    private VictoryCondition victoryCondition;

    /**
     * Sets the player's colour.
     * @param playerColour The player's colour.
     * @return This builder for chaining.
     */
    public Builder setColour(PlayerColour playerColour) {
      this.colour = playerColour;
      return this;
    }

    /**
     * Gets the player's colour.
     * @return The player's colour.
     */
    public PlayerColour getColour() {
      return colour;
    }

    /**
     * Sets whether or not this player should be played by a computer player. This is not mandatory,
     * and the player will default to being a computer player if not set.
     * @param computerPlayer Should be set to {@code true} if the player should be played by a
     * computer.
     * @return This builder for chaining.
     */
    public Builder setComputerPlayer(boolean computerPlayer) {
      this.computerPlayer = computerPlayer;
      return this;
    }

    /**
     * Determines whether the player should be played by a computer.
     * @return {@code true} if the player should be played by a computer.
     */
    public Boolean getComputerPlayer() {
      return computerPlayer;
    }

    /**
     * Sets the difficulty of the player (only applicable to computer players).
     * @param difficulty The difficulty of the player.
     * @return This builder, for chaining.
     */
    public Builder setDifficulty(Difficulty difficulty) {
      this.difficulty = difficulty;
      return this;
    }

    /**
     * Gets the difficulty of the player (only applicable to computer players).
     * @return The difficulty of the player.
     */
    public Difficulty getDifficulty() {
      return difficulty;
    }

    /**
     * Sets the victory condition for the player.
     * @param victoryCondition The victory condition for the player.
     * @return This builder for chaining.
     */
    public Builder setVictoryCondition(VictoryCondition victoryCondition) {
      this.victoryCondition = victoryCondition;
      return this;
    }

    /**
     * Gets the victory condition for the player.
     * @return The victory condition for the player.
     */
    public VictoryCondition getVictoryCondition() {
      return victoryCondition;
    }

    /**
     * Builds the player configuration.
     * @return The player configuration.
     * @throws NullPointerException Thrown if any of the mandatory values have not been set.
     */
    public PlayerConfiguration build() throws NullPointerException {
      return new PlayerConfiguration(this);
    }
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(playerColour.getName());
    if (isComputerPlayer()) {
      stringBuilder.append(" - Computer (").append(difficulty.getName()).append(')');
    }
    return stringBuilder.toString();
  }
}
