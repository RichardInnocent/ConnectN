import static org.junit.Assert.*;

import org.junit.Test;

public class ConstantColourPlayerTest {

  @Test(expected = NullPointerException.class)
  public void testColourCannotBeNull() {
    new ConstantColourPlayer(null) {
      @Override
      public void takeTurn(Board board) {}
    };
  }

  @Test
  public void testSettingIconFromConstructor() {
    PlayerColour colour = PlayerColour.CYAN;
    Player player = new ConstantColourPlayer(colour) {
      @Override
      public void takeTurn(Board board) {}
    };
    assertEquals(colour, player.getColour());
  }

}