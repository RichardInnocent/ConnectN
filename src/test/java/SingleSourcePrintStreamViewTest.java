import static org.mockito.Mockito.*;

import java.io.PrintStream;
import org.junit.Test;

public class SingleSourcePrintStreamViewTest {

  @Test(expected = NullPointerException.class)
  public void constructor_OutputStreamIsNull_ExceptionThrown() {
    new SingleSourcePrintStreamView(null);
  }

  @Test
  public void print_ValidPrint_MessageIsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    View view = new SingleSourcePrintStreamView(outputStream);
    String message = "Test message";
    view.send(message);
    verify(outputStream, times(1)).print(message);
  }

  @Test
  public void printLine_ValidMessage_MessageIsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    View view = new SingleSourcePrintStreamView(outputStream);
    String message = "Test message";
    view.sendLine(message);
    verify(outputStream, times(1)).println(message);
  }

  @Test
  public void printLine_NoArguments_EmptyLineIsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    View view = new SingleSourcePrintStreamView(outputStream);
    view.sendLine();
    verify(outputStream, times(1)).println();
  }

  @Test
  public void printf_NoArguments_ArgumentsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    View view = new SingleSourcePrintStreamView(outputStream);
    String format = "Test format";
    view.sendf(format);
    verify(outputStream, times(1)).printf(format);
  }

  @Test
  public void printf_SingleArgument_ArgumentsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    View view = new SingleSourcePrintStreamView(outputStream);
    String format = "Test format";
    Object argument = mock(Object.class);
    view.sendf(format, argument);
    verify(outputStream, times(1)).printf(format, argument);
  }

  @Test
  public void printf_MultipleArguments_ArgumentsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    View view = new SingleSourcePrintStreamView(outputStream);
    String format = "Test format";
    Object argument1 = mock(Object.class);
    Object argument2 = mock(Object.class);
    Object argument3 = mock(Object.class);
    view.sendf(format, argument1, argument2, argument3);
    verify(outputStream, times(1)).printf(format, argument1, argument2, argument3);
  }

  @Test
  public void testClose() {
    PrintStream outputStream = mock(PrintStream.class);
    new SingleSourcePrintStreamView(outputStream).close();
    verify(outputStream, times(1)).close();
  }

}