import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InvalidMoveExceptionTest {

  @Test
  public void testMessage() {
    String message = "Test exception message";
    assertEquals(message, new InvalidMoveException(message).getMessage());
  }

}