import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class AIPlayerTest {

  @Test
  public void constructor_Valid_AllValuesSet() {
    PlayerColour colour = PlayerColour.BLUE;
    Difficulty difficulty = Difficulty.EASY;
    VictoryCondition victoryCondition = mock(VictoryCondition.class);
    AIPlayer player =  new AIPlayer(colour, victoryCondition, difficulty);
    assertEquals(colour, player.getColour());
    assertEquals(difficulty, player.getDifficulty());
    assertEquals(victoryCondition, player.getVictoryCondition());
  }

  @Test
  public void isHuman_Always_ReturnsFalse() {
    assertFalse(
        new AIPlayer(PlayerColour.CYAN, mock(VictoryCondition.class), Difficulty.EASY).isHuman()
    );
  }

  @Test(expected = BoardFullException.class)
  public void takeTurn_BoardFull_ExceptionThrown() {
    Board board = mock(Board.class);
    when(board.isFull()).thenReturn(true);
    AIStrategy strategy = mock(AIStrategy.class);
    Player player = new AIPlayer(PlayerColour.BLUE, mock(VictoryCondition.class), Difficulty.EASY);
    player.takeTurn(board, mock(IOHandler.class));
    verify(strategy, never()).takeTurn(board, player);
  }

  @Test
  public void testStrategyIsUsedWhenTakingTurn() {
    AIStrategy strategy = mock(AIStrategy.class);
    Difficulty difficulty = mock(Difficulty.class);
    VictoryCondition victoryCondition = mock(VictoryCondition.class);
    when(difficulty.getStrategy(victoryCondition)).thenReturn(strategy);
    Board board = mock(Board.class);
    Player player = new AIPlayer(PlayerColour.BLUE, victoryCondition, difficulty);
    player.takeTurn(board, mock(IOHandler.class));
    verify(strategy, times(1)).takeTurn(board, player);
  }

}