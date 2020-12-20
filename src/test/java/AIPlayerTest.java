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
    AIPlayer player =  new AIPlayer(colour, difficulty, victoryCondition);
    assertEquals(colour, player.getColour());
    assertEquals(difficulty, player.getDifficulty());
    assertEquals(victoryCondition, player.getVictoryCondition());
  }

  @Test
  public void isHuman_Always_ReturnsFalse() {
    assertFalse(
        new AIPlayer(PlayerColour.CYAN, Difficulty.EASY, mock(VictoryCondition.class)).isHuman()
    );
  }

  @Test(expected = BoardFullException.class)
  public void takeTurn_BoardFull_ExceptionThrown() {
    Board board = mock(Board.class);
    when(board.isFull()).thenReturn(true);
    AIStrategy strategy = mock(AIStrategy.class);
    Player player = new AIPlayer(PlayerColour.BLUE, Difficulty.EASY, mock(VictoryCondition.class));
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
    Player player = new AIPlayer(PlayerColour.BLUE, difficulty, victoryCondition);
    player.takeTurn(board, mock(IOHandler.class));
    verify(strategy, times(1)).takeTurn(board, player);
  }

}