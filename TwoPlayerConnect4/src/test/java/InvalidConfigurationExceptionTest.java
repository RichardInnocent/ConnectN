import static org.junit.Assert.*;

import org.junit.Test;

public class InvalidConfigurationExceptionTest {

  @Test
  public void constructor_SetMessage_MessageIsSet() {
    String message = "Test message";
    assertEquals(message, new InvalidMoveException(message).getMessage());
  }

}