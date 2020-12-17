import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class AIPlayerTest {

  @Test
  public void testColourIsSet() {
    PlayerColour colour = PlayerColour.BLUE;
    assertEquals(colour, new AIPlayer(colour, mock(AIStrategy.class)).getColour());
  }

  @Test
  public void isHuman_Always_ReturnsFalse() {
    assertFalse(new AIPlayer(PlayerColour.CYAN, mock(AIStrategy.class)).isHuman());
  }

  @Test(expected = BoardFullException.class)
  public void takeTurn_BoardFull_ExceptionThrown() {
    Board board = mock(Board.class);
    when(board.isFull()).thenReturn(true);
    AIStrategy strategy = mock(AIStrategy.class);
    Player player = new AIPlayer(PlayerColour.BLUE, strategy);
    player.takeTurn(board, mock(IOHandler.class));
    verify(strategy, never()).takeTurn(board, player);
  }

  @Test
  public void testStrategyIsUsedWhenTakingTurn() {
    AIStrategy strategy = mock(AIStrategy.class);
    Board board = mock(Board.class);
    Player player = new AIPlayer(PlayerColour.BLUE, strategy);
    player.takeTurn(board, mock(IOHandler.class));
    verify(strategy, times(1)).takeTurn(board, player);
  }

}