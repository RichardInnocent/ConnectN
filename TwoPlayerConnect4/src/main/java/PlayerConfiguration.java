import java.util.Objects;

public class PlayerConfiguration {

  private final PlayerColour playerColour;
  private final boolean computerPlayer;
  private final Difficulty difficulty;
  private final VictoryCondition victoryCondition;

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

  public PlayerColour getColour() {
    return playerColour;
  }

  public boolean isComputerPlayer() {
    return computerPlayer;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public VictoryCondition getVictoryCondition() {
    return victoryCondition;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private PlayerColour colour;
    private Boolean computerPlayer;
    private Difficulty difficulty = Difficulty.MODERATE;
    private VictoryCondition victoryCondition;

    public Builder setColour(PlayerColour playerColour) {
      this.colour = playerColour;
      return this;
    }

    public PlayerColour getColour() {
      return colour;
    }

    public Builder setComputerPlayer(boolean computerPlayer) {
      this.computerPlayer = computerPlayer;
      return this;
    }

    public Boolean getComputerPlayer() {
      return computerPlayer;
    }

    public Builder setDifficulty(Difficulty difficulty) {
      this.difficulty = difficulty;
      return this;
    }

    public Difficulty getDifficulty() {
      return difficulty;
    }

    public Builder setVictoryCondition(VictoryCondition victoryCondition) {
      this.victoryCondition = victoryCondition;
      return this;
    }

    public VictoryCondition getVictoryCondition() {
      return victoryCondition;
    }

    public PlayerConfiguration build() throws NullPointerException {
      return new PlayerConfiguration(this);
    }
  }

}
