import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import org.junit.Test;

public class ColumnTest {

  private final Player player1 = mock(Player.class);
  private final Player player2 = mock(Player.class);

  @Test
  public void testAddingCounters() {
    Column column = new Column(3);
    for (int i = 0; i < 3; i++) {
      assertTrue(column.getOwnerOfCounterAtIndex(i).isEmpty());
    }

    column.addCounter(player1);
    assertEquals(Optional.of(player1), column.getOwnerOfCounterAtIndex(0));
    assertTrue(column.getOwnerOfCounterAtIndex(1).isEmpty());
    assertTrue(column.getOwnerOfCounterAtIndex(2).isEmpty());

    column.addCounter(player2);
    assertEquals(Optional.of(player1), column.getOwnerOfCounterAtIndex(0));
    assertEquals(Optional.of(player2), column.getOwnerOfCounterAtIndex(1));
    assertTrue(column.getOwnerOfCounterAtIndex(2).isEmpty());

    column.addCounter(player1);
    assertEquals(Optional.of(player1), column.getOwnerOfCounterAtIndex(0));
    assertEquals(Optional.of(player2), column.getOwnerOfCounterAtIndex(1));
    assertEquals(Optional.of(player1), column.getOwnerOfCounterAtIndex(2));

    assertTrue(column.isFull());

    try {
      column.addCounter(player2);
      fail("No exception thrown");
    } catch (InvalidMoveException e) {
      assertEquals("No counter can be placed here as the column is full", e.getMessage());
    }
  }

}