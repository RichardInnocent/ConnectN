import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class CheckOneTurnWinConditionStrategyTest {

  private final IOHandler ioHandler = mock(IOHandler.class);
  private final Random random = mock(Random.class);
  private final VictoryCondition victoryCondition = new ConsecutiveCountersVictoryCondition(4);
  private final Difficulty difficulty = mock(Difficulty.class);
  private final AIStrategy strategy =
      new CheckOneTurnWinConditionStrategy(victoryCondition, random);
  private final Player aiPlayer = new AIPlayer(PlayerColour.RED, victoryCondition, difficulty);
  private final Player otherPlayer = new AIPlayer(PlayerColour.BLUE, victoryCondition, difficulty);

  @Before
  public void configureDifficulty() {
    when(difficulty.getStrategy(victoryCondition)).thenReturn(strategy);
  }

  @Test
  public void testPicksRandomSlotIfBoardIsEmpty() {
    Board board = new Board(BoardConfiguration.forDimensions(new Dimensions(6, 7)));
    when(random.nextInt(6)).thenReturn(0);
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(1, 1);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testPickWinningHorizontalSlotIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "   OO" + System.lineSeparator() +
            "O AAA"
    );
    assertTrue(board.getOwnerOfCounterAt(1, 2).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(1, 2);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testBlocksWinningHorizontalSlotIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "   AA" + System.lineSeparator() +
            "A OOO"
    );
    assertTrue(board.getOwnerOfCounterAt(1, 2).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(1, 2);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testPickWinningHorizontalSlotOnSecondRowIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "AAA O" + System.lineSeparator() +
            "OAOOO"
    );
    assertTrue(board.getOwnerOfCounterAt(2, 4).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(2, 4);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testBlocksWinningHorizontalSlotOnSecondRowIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "OOO A" + System.lineSeparator() +
            "AOAAA"
    );
    assertTrue(board.getOwnerOfCounterAt(2, 4).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(2, 4);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testPickWinningVerticalSlotIfAvailable() {
    Board board = createBoard(
        "   " + System.lineSeparator() +
        "   " + System.lineSeparator() +
        "A  " + System.lineSeparator() +
        "A  " + System.lineSeparator() +
        "A  "
    );
    assertTrue(board.getOwnerOfCounterAt(4, 1).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(4, 1);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testBlocksWinningVerticalSlotIfAvailable() {
    Board board = createBoard("   " + System.lineSeparator() +
                                  "   " + System.lineSeparator() +
                                  "O  " + System.lineSeparator() +
                                  "O  " + System.lineSeparator() +
                                  "O  "
    );
    assertTrue(board.getOwnerOfCounterAt(4, 1).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(4, 1);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testPickWinningLeadingDiagonalSlotIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
        "     " + System.lineSeparator() +
        " OA  " + System.lineSeparator() +
        " OOA " + System.lineSeparator() +
        " AOOA"
    );
    assertTrue(board.getOwnerOfCounterAt(4, 2).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(4, 2);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testBlocksWinningLeadingDiagonalSlotIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "     " + System.lineSeparator() +
            " AO  " + System.lineSeparator() +
            " AAO " + System.lineSeparator() +
            " OAAO"
    );
    assertTrue(board.getOwnerOfCounterAt(4, 2).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(4, 2);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testPickWinningCounterdiagonalSlotIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "     " + System.lineSeparator() +
            "   AO" + System.lineSeparator() +
            "  AOO" + System.lineSeparator() +
            " AOOA"
    );
    assertTrue(board.getOwnerOfCounterAt(4, 5).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(4, 5);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testBlocksWinningCounterdiagonalSlotIfAvailable() {
    Board board = createBoard(
        "     " + System.lineSeparator() +
            "     " + System.lineSeparator() +
            "   OA" + System.lineSeparator() +
            "  OAA" + System.lineSeparator() +
            " OAAO"
    );
    assertTrue(board.getOwnerOfCounterAt(4, 5).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(4, 5);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  private Board createBoard(String boardAsText) {
    String[] boardLines = boardAsText.split(System.lineSeparator());
    Board board =
        new Board(
            BoardConfiguration.forDimensions(
                new Dimensions(boardLines[0].length(), boardLines.length)
            )
        );
    for (int i = boardLines.length-1; i >= 0; i--) {
      for (int charIndex = 0; charIndex < boardLines[i].length(); charIndex++) {
        if (boardLines[i].charAt(charIndex) == 'A') {
          board.placePlayerCounterInColumn(aiPlayer, charIndex+1);
        } else if (boardLines[i].charAt(charIndex) == 'O') {
          board.placePlayerCounterInColumn(otherPlayer, charIndex+1);
        }
      }
    }
    return board;
  }

}