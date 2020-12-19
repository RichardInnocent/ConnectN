import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConsecutiveCountersVictoryConditionTest {

  private static final Player CHECKED_PLAYER = mock(Player.class, "X");
  private static final Player UNCHECKED_PLAYER = mock(Player.class, "-");
  private static final VictoryCondition VICTORY_CONDITION = new ConsecutiveCountersVictoryCondition(4);

  @BeforeClass
  public static void setUpPlayers() {
    when(CHECKED_PLAYER.getColour()).thenReturn(PlayerColour.PURPLE);
    when(UNCHECKED_PLAYER.getColour()).thenReturn(PlayerColour.ORANGE);
  }

  @Test
  public void testNoWin() {
    Board board = createBoard(
        "xxx" + System.lineSeparator() +
            "xxx" + System.lineSeparator() +
            "xxx"
    );
    assertFalse(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testHorizontalWinAtBottomLeft() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
        "     " + System.lineSeparator() +
        "xxxx "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testHorizontalWinAtBottomRight() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "     " + System.lineSeparator() +
            " xxxx"
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testHorizontalWinAtTopLeft() {
    Board board = createBoard(
        "xxxx " + System.lineSeparator() +
            "     " + System.lineSeparator() +
            "     "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testHorizontalWinAtTopRight() {
    Board board = createBoard(
        " xxxx" + System.lineSeparator() +
            "     " + System.lineSeparator() +
            "     "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testHorizontalWinNotTriggeredByFlowingOntoNextRow() {
    Board board = createBoard(
        "xx xx" + System.lineSeparator() +
            "xx xx" + System.lineSeparator() +
            "xx xx"
    );
    assertFalse(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testVerticalWinAtBottomLeft() {
    Board board = createBoard(
        "   " + System.lineSeparator() +
            "x  " + System.lineSeparator() +
            "x  " + System.lineSeparator() +
            "x  " + System.lineSeparator() +
            "x  "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testVerticalWinAtBottomRight() {
    Board board = createBoard(
        "   " + System.lineSeparator() +
            "  x" + System.lineSeparator() +
            "  x" + System.lineSeparator() +
            "  x" + System.lineSeparator() +
            "  x"
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testVerticalWinAtTopLeft() {
    Board board = createBoard(
        "x  " + System.lineSeparator() +
        "x  " + System.lineSeparator() +
        "x  " + System.lineSeparator() +
        "x  " + System.lineSeparator() +
        "   "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testVerticalWinAtTopRight() {
    Board board = createBoard(
        "  x" + System.lineSeparator() +
        "  x" + System.lineSeparator() +
        "  x" + System.lineSeparator() +
        "  x" + System.lineSeparator() +
        "   "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testVerticalWinNotTriggeredByFlowingOntoNextRow() {
    Board board = createBoard(
        "x x" + System.lineSeparator() +
            "x x" + System.lineSeparator() +
            "   " + System.lineSeparator() +
            "x x" + System.lineSeparator() +
            "x x"
    );
    assertFalse(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testLeadingDiagonalWinFromTopLeft() {
    Board board = createBoard(
        "x    " + System.lineSeparator() +
            " x   " + System.lineSeparator() +
            "  x  " + System.lineSeparator() +
            "   x " + System.lineSeparator() +
            "     "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testLeadingDiagonalWinFromBottomRight() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            " x   " + System.lineSeparator() +
            "  x  " + System.lineSeparator() +
            "   x " + System.lineSeparator() +
            "    x"
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testLeadingDiagonalWinFromBottomLeftMostPosition() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "x    " + System.lineSeparator() +
            " x   " + System.lineSeparator() +
            "  x  " + System.lineSeparator() +
            "   x "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testLeadingDiagonalWinFromTopRightMostPosition() {
    Board board = createBoard(
        "x   " + System.lineSeparator() +
            " x  " + System.lineSeparator() +
            "  x " + System.lineSeparator() +
            "   x" + System.lineSeparator() +
            "    "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testLeadingDiagonalWinNotTriggeredByFlowingOver() {
    Board board = createBoard(
        " xxx" + System.lineSeparator() +
            "x xx" + System.lineSeparator() +
            "xx x"
    );
    assertFalse(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testCounterdiagonalWinFromBottomLeft() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "   x " + System.lineSeparator() +
            "  x  " + System.lineSeparator() +
            " x   " + System.lineSeparator() +
            "x    "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testCounterdiagonalWinFromTopRight() {
    Board board = createBoard(
        "    x" + System.lineSeparator() +
            "   x " + System.lineSeparator() +
            "  x  " + System.lineSeparator() +
            " x   " + System.lineSeparator() +
            "     "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testCounterdiagonalWinFromTopLeftMostPosition() {
    Board board = createBoard(
        "   x " + System.lineSeparator() +
            "  x  " + System.lineSeparator() +
            " x   " + System.lineSeparator() +
            "x    " + System.lineSeparator() +
            "     "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testCounterdiagonalWinFromBottomRightMostPosition() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "    x" + System.lineSeparator() +
            "   x " + System.lineSeparator() +
            "  x  " + System.lineSeparator() +
            " x   "
    );
    assertTrue(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  @Test
  public void testCounterdiagonalWinNotTriggeredByFlowingOver() {
    Board board = createBoard(
            "xxx x" + System.lineSeparator() +
            "xx xx" + System.lineSeparator() +
            "x xxx"
    );
    assertFalse(VICTORY_CONDITION.isAchievedForPlayer(CHECKED_PLAYER, board));
  }

  private Board createBoard(String boardAsText) {
    String[] rows = boardAsText.split(System.lineSeparator());
    Board board =
        new Board(
            BoardConfiguration.forDimensions(
                new Dimensions(rows[rows.length-1].length(), rows.length)
            )
        );

    for (int rowIndex = rows.length-1; rowIndex >= 0; rowIndex--) {
      String row = rows[rowIndex];
      for (int i = 0; i < row.length() && i < board.getWidth(); i++) {
        char currentChar = row.charAt(i);
        Player player =
            currentChar == 'x' || currentChar == 'X' ? CHECKED_PLAYER : UNCHECKED_PLAYER;
        board.placePlayerCounterInColumn(player, i+1);
      }
    }

    return board;
  }

}