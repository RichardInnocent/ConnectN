import static org.junit.Assert.*;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class DimensionsTest {

  @Test
  public void equalsAndHashcode_test() {
    EqualsVerifier.forClass(Dimensions.class).verify();
  }

  @Test
  public void toString_Always_ProducesValidOutput() {
    int width = 3;
    int height = 7;
    assertEquals("[" + width + ", " + height + "]", new Dimensions(width, height).toString());
  }

}