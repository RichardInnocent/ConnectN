import static org.junit.Assert.*;

import org.junit.Test;

public class BoardFullExceptionTest {

  @Test
  public void constructor_NoParams_FieldsAreSetCorrectly() {
    assertEquals("The board is full", new BoardFullException().getMessage());
  }

}