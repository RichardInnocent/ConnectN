import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerColourTest {

  @Test
  public void testPlayerColours() {
    assertEquals("Red", PlayerColour.RED.getName());
    assertEquals('r', PlayerColour.RED.getIcon());
    assertEquals("Yellow", PlayerColour.YELLOW.getName());
    assertEquals('y', PlayerColour.YELLOW.getIcon());
    assertEquals("Blue", PlayerColour.BLUE.getName());
    assertEquals('b', PlayerColour.BLUE.getIcon());
    assertEquals("Green", PlayerColour.GREEN.getName());
    assertEquals('g', PlayerColour.GREEN.getIcon());
    assertEquals("Purple", PlayerColour.PURPLE.getName());
    assertEquals('p', PlayerColour.PURPLE.getIcon());
    assertEquals("Orange", PlayerColour.ORANGE.getName());
    assertEquals('o', PlayerColour.ORANGE.getIcon());
    assertEquals("Cyan", PlayerColour.CYAN.getName());
    assertEquals('c', PlayerColour.CYAN.getIcon());
    assertEquals("White", PlayerColour.WHITE.getName());
    assertEquals('w', PlayerColour.WHITE.getIcon());
  }

}