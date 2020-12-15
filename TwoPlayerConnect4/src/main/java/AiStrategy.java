/**
 * The strategy employed by the AI. This can be injected into the AI players so that they play
 * differently from one another. This could instead form a different part of different AI player
 * child classes, but adding it as a separate class differentiates its responsibility from that of
 * the AI player implementations, helping us to keep the class simple and enforcing the single
 * responsibility principle (https://stackify.com/solid-design-principles/ accessed 08/12/2020).
 */
@FunctionalInterface
public interface AiStrategy {

  /**
   * Takes the turn of the AI player.
   * @param board The board on which the turn will occur.
   * @param player The player that is taking the turn.
   */
  void takeTurn(Board board, Player player);

}
