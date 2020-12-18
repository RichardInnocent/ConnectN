import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import org.junit.Test;

public class BoardTest {

  @Test(expected = IllegalArgumentException.class)
  public void testWidthOfBoardCannotBeLessThan3() {
    new Board(BoardConfiguration.forDimensions(new Dimensions(2, 5)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHeightOfBoardCannotBeLessThan3() {
    new Board(BoardConfiguration.forDimensions(new Dimensions(5, 2)));
  }

  @Test
  public void testAllColumnsAreEmptyAtBeginning() {
    int width = 6;
    int height = 7;

    Board board = new Board(BoardConfiguration.forDimensions(new Dimensions(width, height)));
    assertEquals(width, board.getWidth());
    assertEquals(height, board.getHeight());
    assertFalse(board.isFull());

    for (int row = 1; row <= board.getHeight(); row++) {
      for (int column = 1; column <= board.getWidth(); column++) {
        assertTrue(board.getOwnerOfCounterAt(row, column).isEmpty());
      }
    }
  }

  @Test
  public void testCannotPlaceCounterOutsideOfRange() {
    Board board = new Board(BoardConfiguration.forDimensions(new Dimensions(5, 3)));
    Player player = mock(Player.class);

    try {
      board.placePlayerCounterInColumn(player, 0);
      fail("No exception thrown");
    } catch (InvalidMoveException e) {
      assertEquals("There's no column 0", e.getMessage());
    }

    try {
      board.placePlayerCounterInColumn(player, board.getWidth()+1);
      fail("No exception thrown");
    } catch (InvalidMoveException e) {
      assertEquals("There's no column " + (board.getWidth()+1), e.getMessage());
    }

    board.placePlayerCounterInColumn(player, 1);
    board.placePlayerCounterInColumn(player, 1);
    board.placePlayerCounterInColumn(player, 1);

    try {
      board.placePlayerCounterInColumn(player, 1);
      fail("No exception thrown");
    } catch (InvalidMoveException e) {
      assertEquals("No counter can be placed here as the column is full", e.getMessage());
    }
  }

  @Test
  public void testPlacingCounter() {
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);

    Board board = new Board(BoardConfiguration.forDimensions(new Dimensions(3, 3)));
    board.placePlayerCounterInColumn(player1, 1);
    verifyPlayerCounterIsAtPosition(board, player1, 1, 1);

    board.placePlayerCounterInColumn(player2, 2);
    verifyPlayerCounterIsAtPosition(board, player2, 1, 2);

    board.placePlayerCounterInColumn(player1, 3);
    verifyPlayerCounterIsAtPosition(board, player1, 1, 3);

    board.placePlayerCounterInColumn(player2, 1);
    verifyPlayerCounterIsAtPosition(board, player2, 2, 1);

    board.placePlayerCounterInColumn(player1, 2);
    verifyPlayerCounterIsAtPosition(board, player1, 2, 2);

    board.placePlayerCounterInColumn(player2, 3);
    verifyPlayerCounterIsAtPosition(board, player2, 2, 3);

    board.placePlayerCounterInColumn(player1, 1);
    verifyPlayerCounterIsAtPosition(board, player1, 3, 1);

    board.placePlayerCounterInColumn(player2, 2);
    verifyPlayerCounterIsAtPosition(board, player2, 3, 2);

    assertFalse(board.isFull());
    board.placePlayerCounterInColumn(player1, 3);
    verifyPlayerCounterIsAtPosition(board, player1, 3, 3);

    assertTrue(board.isFull());
  }

  private void verifyPlayerCounterIsAtPosition(Board board, Player player, int row, int column) {
    Optional<Player> owner = board.getOwnerOfCounterAt(row, column);
    assertTrue(
        "No counter present at row " + row + " and column " + column,
        owner.isPresent()
    );
    assertEquals("Incorrect at position", player, owner.get());
  }

}