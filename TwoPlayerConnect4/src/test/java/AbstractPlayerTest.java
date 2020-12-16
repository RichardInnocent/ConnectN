import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class AbstractPlayerTest {

  @Test(expected = NullPointerException.class)
  public void constructor_ColourIsNull_ExceptionThrown() {
    new AbstractPlayer(null) {
      @Override
      public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {}

      @Override
      public boolean isHuman() {
        return false;
      }
    };
  }

  @Test
  public void constructor_ColourIsValid_ColourSet() {
    PlayerColour colour = PlayerColour.CYAN;
    Player player = new AbstractPlayer(colour) {
      @Override
      public void takeTurnOnIncompleteBoard(Board board, IOHandler ioHandler) {}

      @Override
      public boolean isHuman() {
        return false;
      }
    };
    assertEquals(colour, player.getColour());
  }

  @Test(expected = BoardFullException.class)
  public void takeTurn_FullBoard_ExceptionThrown() {
    Player player = new AbstractPlayer(PlayerColour.CYAN) {
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
    Player player = new AbstractPlayer(PlayerColour.CYAN) {
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