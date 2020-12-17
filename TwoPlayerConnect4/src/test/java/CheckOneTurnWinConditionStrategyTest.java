import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import org.junit.Test;

public class CheckOneTurnWinConditionStrategyTest {

  private final IOHandler ioHandler = mock(IOHandler.class);
  private final Random random = mock(Random.class);
  private final VictoryCondition victoryCondition = new ConsecutiveCountersVictoryCondition(4);
  private final AIStrategy strategy =
      new CheckOneTurnWinConditionStrategy(Collections.singleton(victoryCondition), random);
  private final Player aiPlayer = new AIPlayer(PlayerColour.RED, strategy);
  private final Player otherPlayer = new AIPlayer(PlayerColour.BLUE, new RandomPlacementStrategy());

  @Test
  public void testPicksRandomSlotIfBoardIsEmpty() {
    Board board = new Board(6, 7);
    when(random.nextInt(6)).thenReturn(0);
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(1, 1);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  @Test
  public void testPickWinningHorizontalSlotIfAvailable() {
    Board board = createBoard(
        "     \n" +
            "   OO\n" +
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
        "     \n" +
            "   AA\n" +
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
        "     \n" +
            "AAA O\n" +
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
        "     \n" +
            "OOO A\n" +
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
    Board board = createBoard("   \n" +
        "   \n" +
        "A  \n" +
        "A  \n" +
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
    Board board = createBoard("   \n" +
                                  "   \n" +
                                  "O  \n" +
                                  "O  \n" +
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
        "     \n" +
        "     \n" +
        " OA  \n" +
        " OOA \n" +
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
        "     \n" +
            "     \n" +
            " AO  \n" +
            " AAO \n" +
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
        "     \n" +
            "     \n" +
            "   AO\n" +
            "  AOO\n" +
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
        "     \n" +
            "     \n" +
            "   OA\n" +
            "  OAA\n" +
            " OAAO"
    );
    assertTrue(board.getOwnerOfCounterAt(4, 5).isEmpty());
    aiPlayer.takeTurn(board, ioHandler);
    Optional<Player> player = board.getOwnerOfCounterAt(4, 5);
    assertTrue(player.isPresent());
    assertEquals(aiPlayer, player.get());
  }

  private Board createBoard(String boardAsText) {
    String[] boardLines = boardAsText.split("\n");
    Board board = new Board(boardLines[0].length(), boardLines.length);
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