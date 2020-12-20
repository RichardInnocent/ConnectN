import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import org.junit.Test;

public class RandomPlacementStrategyTest {

  private static final Player PLAYER = mock(Player.class);

  private final Random random = mock(Random.class);
  private final Board board = mock(Board.class);
  private final RandomPlacementStrategy strategy = new RandomPlacementStrategy(random);

  @Test
  public void takeTurn_SingleColumn_CounterIsPlacedInOnlyColumn() {
    when(board.getColumnsWithSpareCapacity()).thenReturn(Collections.singletonList(4));
    when(random.nextInt(3)).thenReturn(0);
    strategy.takeTurn(board, PLAYER);
    verify(board).placePlayerCounterInColumn(PLAYER, 4);
  }

  @Test
  public void takeTurn_MultipleColumns_CounterIsPlacedInRandomColumn() {
    when(board.getColumnsWithSpareCapacity()).thenReturn(Arrays.asList(4, 7, 8));
    when(random.nextInt(3)).thenReturn(0);
    strategy.takeTurn(board, PLAYER);
    verify(board).placePlayerCounterInColumn(PLAYER, 4);
  }

}