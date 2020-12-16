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
    assertEquals(colour, new AIPlayer(colour, mock(AiStrategy.class)).getColour());
  }

  @Test
  public void isHuman_Always_ReturnsFalse() {
    assertFalse(new AIPlayer(PlayerColour.CYAN, mock(AiStrategy.class)).isHuman());
  }

  @Test(expected = BoardFullException.class)
  public void takeTurn_BoardFull_ExceptionThrown() {
    Board board = mock(Board.class);
    when(board.isFull()).thenReturn(true);
    AiStrategy strategy = mock(AiStrategy.class);
    Player player = new AIPlayer(PlayerColour.BLUE, strategy);
    player.takeTurn(board, mock(IOHandler.class));
    verify(strategy, never()).takeTurn(board, player);
  }

  @Test
  public void testStrategyIsUsedWhenTakingTurn() {
    AiStrategy strategy = mock(AiStrategy.class);
    Board board = mock(Board.class);
    Player player = new AIPlayer(PlayerColour.BLUE, strategy);
    player.takeTurn(board, mock(IOHandler.class));
    verify(strategy, times(1)).takeTurn(board, player);
  }

}