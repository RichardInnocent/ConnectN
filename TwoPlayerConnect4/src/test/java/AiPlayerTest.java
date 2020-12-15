import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class AiPlayerTest {

  @Test
  public void testColourIsSet() {
    PlayerColour colour = PlayerColour.BLUE;
    assertEquals(colour, new AiPlayer(colour, mock(AiStrategy.class)).getColour());
  }

  @Test
  public void testStrategyIsUsedWhenTakingTurn() {
    AiStrategy strategy = mock(AiStrategy.class);
    Board board = mock(Board.class);
    Player player = new AiPlayer(PlayerColour.BLUE, strategy);
    player.takeTurn(board);
    verify(strategy, times(1)).takeTurn(board, player);
  }

}