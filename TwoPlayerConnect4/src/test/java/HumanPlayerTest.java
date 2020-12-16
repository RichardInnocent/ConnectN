import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import org.junit.Test;

public class HumanPlayerTest {

  @Test(expected = NullPointerException.class)
  public void constructor_ColourIsNull_ExceptionThrown() {
    new HumanPlayer(null);
  }

  @Test
  public void constructor_ArgumentsAreValid_ColourSet() {
    PlayerColour colour = PlayerColour.CYAN;
    assertEquals(colour, new HumanPlayer(colour).getColour());
  }

  @Test
  public void isHuman_Always_ReturnsTrue() {
    assertTrue(new HumanPlayer(PlayerColour.CYAN).isHuman());
  }

  @Test
  public void takeTurn_ValidInput_TurnTaken() throws IOException {
    IOHandler ioHandler = mock(IOHandler.class);
    Player player = new HumanPlayer(PlayerColour.CYAN);
    Board board = mock(Board.class);
    when(ioHandler.readLine()).thenReturn("3");
    player.takeTurn(board, ioHandler);
    verify(board, times(1)).placePlayerCounterInColumn(player, 3);
  }

  @Test
  public void takeTurn_ReaderThrowsException_InputReRetrieved() throws IOException {
    IOHandler ioHandler = mock(IOHandler.class);
    Player player = new HumanPlayer(PlayerColour.CYAN);
    Board board = mock(Board.class);
    when(ioHandler.readLine()).thenThrow(new IOException()).thenReturn("5");
    player.takeTurn(board, ioHandler);
    verify(board, times(1)).placePlayerCounterInColumn(player, 5);
  }

  @Test
  public void takeTurn_ReaderReturnsNull_InputReRetrieved() throws IOException {
    IOHandler ioHandler = mock(IOHandler.class);
    Player player = new HumanPlayer(PlayerColour.CYAN);
    Board board = mock(Board.class);
    when(ioHandler.readLine()).thenReturn(null, "5");
    player.takeTurn(board, ioHandler);
    verify(board, times(1)).placePlayerCounterInColumn(player, 5);
  }

  @Test
  public void takeTurn_NonNumericInput_InputReRetrieved() throws IOException {
    IOHandler ioHandler = mock(IOHandler.class);
    Player player = new HumanPlayer(PlayerColour.CYAN);
    Board board = mock(Board.class);
    when(ioHandler.readLine()).thenReturn("Not a number", "5");
    player.takeTurn(board, ioHandler);
    verify(board, times(1)).placePlayerCounterInColumn(player, 5);
  }

  @Test
  public void takeTurn_InvalidMove_InputReRetrieved() throws IOException {
    IOHandler ioHandler = mock(IOHandler.class);
    Player player = new HumanPlayer(PlayerColour.CYAN);
    Board board = mock(Board.class);
    when(ioHandler.readLine()).thenReturn("-1", "5");
    doThrow(new InvalidMoveException("Test exception"))
        .when(board).placePlayerCounterInColumn(any(), eq(-1));
    player.takeTurn(board, ioHandler);
    verify(board, times(1)).placePlayerCounterInColumn(player, -1);
    verify(board, times(1)).placePlayerCounterInColumn(player, 5);
  }

  @Test(expected = BoardFullException.class)
  public void takeTurn_BoardFull_ExceptionThrown() throws IOException {
    IOHandler ioHandler = mock(IOHandler.class);
    Player player = new HumanPlayer(PlayerColour.CYAN);
    Board board = mock(Board.class);
    when(ioHandler.readLine()).thenReturn("1");
    doThrow(new BoardFullException()).when(board).placePlayerCounterInColumn(any(), anyInt());
    player.takeTurn(board, ioHandler);
    verify(board, times(1)).placePlayerCounterInColumn(player, -1);
    verify(board, times(1)).placePlayerCounterInColumn(player, 5);
  }

}