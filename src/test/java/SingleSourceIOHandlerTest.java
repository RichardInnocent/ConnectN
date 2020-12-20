import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Test;

public class SingleSourceIOHandlerTest {

  @Test(expected = NullPointerException.class)
  public void constructor_InputStreamIsNull_ExceptionThrown() {
    new SingleSourceIOHandler((BufferedReader) null, mock(PrintStream.class));
  }

  @Test(expected = NullPointerException.class)
  public void constructor_OutputStreamIsNull_ExceptionThrown() {
    new SingleSourceIOHandler(mock(BufferedReader.class), null);
  }

  @Test
  public void print_ValidPrint_MessageIsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    IOHandler ioHandler = new SingleSourceIOHandler(mock(BufferedReader.class), outputStream);
    String message = "Test message";
    ioHandler.print(message);
    verify(outputStream, times(1)).print(message);
  }

  @Test
  public void printLine_ValidMessage_MessageIsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    IOHandler ioHandler = new SingleSourceIOHandler(mock(BufferedReader.class), outputStream);
    String message = "Test message";
    ioHandler.printLine(message);
    verify(outputStream, times(1)).println(message);
  }

  @Test
  public void printLine_NoArguments_EmptyLineIsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    IOHandler ioHandler = new SingleSourceIOHandler(mock(BufferedReader.class), outputStream);
    ioHandler.printLine();
    verify(outputStream, times(1)).println();
  }

  @Test
  public void printf_NoArguments_ArgumentsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    IOHandler ioHandler = new SingleSourceIOHandler(mock(BufferedReader.class), outputStream);
    String format = "Test format";
    ioHandler.printf(format);
    verify(outputStream, times(1)).printf(format);
  }

  @Test
  public void printf_SingleArgument_ArgumentsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    IOHandler ioHandler = new SingleSourceIOHandler(mock(BufferedReader.class), outputStream);
    String format = "Test format";
    Object argument = mock(Object.class);
    ioHandler.printf(format, argument);
    verify(outputStream, times(1)).printf(format, argument);
  }

  @Test
  public void printf_MultipleArguments_ArgumentsPrinted() {
    PrintStream outputStream = mock(PrintStream.class);
    IOHandler ioHandler = new SingleSourceIOHandler(mock(BufferedReader.class), outputStream);
    String format = "Test format";
    Object argument1 = mock(Object.class);
    Object argument2 = mock(Object.class);
    Object argument3 = mock(Object.class);
    ioHandler.printf(format, argument1, argument2, argument3);
    verify(outputStream, times(1)).printf(format, argument1, argument2, argument3);
  }

  @Test
  public void readLine_ValidRead_ResultOfReadIsReturned() throws IOException {
    BufferedReader inputReader = mock(BufferedReader.class);
    IOHandler ioHandler = new SingleSourceIOHandler(inputReader, mock(PrintStream.class));
    String message = "Test message";
    when(inputReader.readLine()).thenReturn(message);
    assertEquals(message, ioHandler.readLine());
  }

  @Test(expected = IOException.class)
  public void readLine_ReaderThrowsException_ExceptionThrown() throws IOException {
    BufferedReader inputReader = mock(BufferedReader.class);
    IOHandler ioHandler = new SingleSourceIOHandler(inputReader, mock(PrintStream.class));
    when(inputReader.readLine()).thenThrow(new IOException());
    ioHandler.readLine();
  }

  @Test
  public void testClose() throws IOException {
    BufferedReader inputReader = mock(BufferedReader.class);
    PrintStream outputStream = mock(PrintStream.class);
    new SingleSourceIOHandler(inputReader, outputStream).close();
    verify(inputReader, times(1)).close();
    verify(outputStream, times(1)).close();
  }

}