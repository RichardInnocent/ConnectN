import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class AbstractPlayerTest {

  @Test(expected = NullPointerException.class)
  public void constructor_ColourIsNull_ExceptionThrown() {
    new AbstractPlayer(null, mock(VictoryCondition.class)) {
      @Override
      public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {}

      @Override
      public boolean isHuman() {
        return false;
      }
    };
  }

  @Test(expected = NullPointerException.class)
  public void constructor_VictoryConditionIsNull_ExceptionThrown() {
    new AbstractPlayer(PlayerColour.CYAN, null) {
      @Override
      public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {}

      @Override
      public boolean isHuman() {
        return false;
      }
    };
  }

  @Test
  public void constructor_ParametersAreValid_ParametersSet() {
    PlayerColour colour = PlayerColour.CYAN;
    VictoryCondition victoryCondition = mock(VictoryCondition.class);
    Player player = new AbstractPlayer(colour, victoryCondition) {
      @Override
      public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {}

      @Override
      public boolean isHuman() {
        return false;
      }
    };
    assertEquals(colour, player.getColour());
    assertEquals(victoryCondition, player.getVictoryCondition());
  }

  @Test(expected = BoardFullException.class)
  public void takeTurn_FullBoard_ExceptionThrown() {
    Player player = new AbstractPlayer(PlayerColour.CYAN, mock(VictoryCondition.class)) {
      @Override
      protected void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {}

      @Override
      public boolean isHuman() {
        return false;
      }
    };
    Board board = mock(Board.class);
    when(board.isFull()).thenReturn(true);
    player.takeTurn(board, mock(IOHandler.class));
  }

  @Test
  public void takeTurn_IncompleteBoard_TurnTaken() {
    int columnNumber = 1;
    Player player = new AbstractPlayer(PlayerColour.CYAN, mock(VictoryCondition.class)) {
      @Override
      protected void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {
        board.placePlayerCounterInColumn(this, columnNumber);
      }

      @Override
      public boolean isHuman() {
        return false;
      }
    };
    Board board = mock(Board.class);
    when(board.isFull()).thenReturn(false);
    player.takeTurn(board, mock(IOHandler.class));
    verify(board, times(1)).placePlayerCounterInColumn(player, columnNumber);
  }

}