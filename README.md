# Graded Assignment 2: Connect N
This is piece of coursework I wrote for CM50273 Principles of Programming at the University of Bath.
Sorry everything's in one package - this was a requirement for the hand-in but does make it a little
difficult to navigate.

## About
This is a game of Connect N with 2-8 human or computer players.

## Starting the game
The game can be instantiated from the terminal in three ways:

### No arguments
```
java MyConnectN
```
This creates a standard game of 2-player Connect Four.

### Passing a configuration file
```
java MyConnectN path/to/config.file
```
Creates a game of Connect N as specified in the configuration file. This is the most versatile
approach. Details of the options available in the configuration file are defined in the
[Setting up the configuration file](#setting-up-the-configuration-file) section.

### Passing the number of consecutive counters
```
java MyConnectN n
```
Note that `n` must be a number in the range `2 < n < 7`.

Creates a game of 3-handed Connect N, where N (the number of counters required to connect in a row
for any player to win) is defined by the value of `n`. Invoking this game state through an
appropriately defined config file is preferred, although this option persists to meet the coursework
specification.

## Setting up the configuration file
There are various parameters that can be set to affect the configuration of the game, as outlined
below.

### Global options
The global options are general options that affect the global configuration of the game.

| Key                        | Expected value                      | Controls |
| -------------------------- | ----------------------------------- | -------- |
| `board.width`              | Integer > 2                         | The width of the game board. |
| `board.height`             | Integer > 2                         | The height of the game board. |
| `players.number`           | Integer between 2 and 8 (inclusive) | The number of players in the game. If not specified, this will be 2. |
| `players.victory.counters` | Integer > 0                         | The default number of counters that each players is required to get in a row to be victorious. This default to 4 if not specified. |
| `players.ai.difficulty`    | `EASY` or `MODERATE`                | The default difficulty for the computer player. This defaults to `MODERATE` if not specified. |

### Player-specific options
It's also possible to override the default options for each player. These are outlined in the table
below. Note that each override is prefixed with `playerX`. `X` should be replaced by the player
number. The player number represents the order in which players take their turns, such that
`player1` always goes first, followed by `player2`, etc.

The overriding control for the number of
created players is still `players.number`, however - if there are specifications for any `playerX`
such that `X` > `players.number`, its configuration will be ignored.

| Key                        | Expected value       | Controls |
| -------------------------- | -------------------- | -------- |
| `playerX.colour`           | `RED`, `YELLOW`, `BLUE`, `GREEN`, `PURPLE`, `ORANGE`, `CYAN` or `WHITE` | The colour of the players as it should be displayed on the board. No two players should share the same colour. If unspecified, the player is allocated a colour that has not yet been supplied to another player. |
| `playerX.ai`               | `true` or `false`    | Whether the player is player by the computer or not. If unspecified, this will default to `false`, although note that the first unspecified player will become a human player if there are no other explicit definitions for at least one human player. A human player won't be created if all players have `playerX.ai=true`. |
| `playerX.ai.difficulty`    | `EASY` or `MODERATE` | The difficulty of the player, overriding the default value. This only applies if the player is a computer player. |
| `playerX.victory.counters` | Integer > 0          | The number of counters that this player is required to get in a row to be victorious, overriding the default value. |