import java.util.Collection;

public class InstructionsHelper {

  public static void displayInstructions(Collection<Player> players, IOHandler ioHandler) {
    ioHandler.printLine("Welcome to Connect 4!");
    ioHandler.printf("There are %d players:" + System.lineSeparator(), players.size());
    printPlayers(players, ioHandler);
    ioHandler.printLine("To play the game type in the number of the column you want to drop you counter in");
    ioHandler.printLine("A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
    ioHandler.printLine();
  }

  private static void printPlayers(Collection<Player> players, IOHandler ioHandler) {
    players.stream()
           .map(InstructionsHelper::getBasicPlayerRepresentation)
           .map(playerRep -> "- " + playerRep)
           .forEach(ioHandler::printLine);
  }

  private static String getBasicPlayerRepresentation(Player player) {
    String basicRepresentation = player.getColour().getName();
    if (!player.isHuman()) {
      basicRepresentation += " (Computer)";
    }
    return basicRepresentation;
  }

}
