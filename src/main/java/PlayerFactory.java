/**
 * A factory that creates {@link Player} instances based on their specification.
 */
public interface PlayerFactory {

  /**
   * Creates a player according to their specification.
   * @param config The player specification.
   * @return A new player.
   */
  Player create(PlayerConfiguration config);
}
