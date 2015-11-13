# <h1> CS2110 TIC-TAC-NINE </h1>

<h3> How to play </h3>

<h4> Installation Process </h4>
Before playing the game, you must:
 - Download the two java files
 - Download the two picture files (x.png and o.png)
 - Have a program that can run the two java files (such as Eclipse) and put them in a java project
 - Put the two picture files into the JRE System Library of the java project(for Eclipse)

<h4> Rules </h4>
The rules are the same as a normal TicTacToe game, except that the goal is to win three games of TicTacToe in a row on a big board composed of 9 smaller boards.

If a tie occurs on a small board:
 - The last person to play will play a game of Rock-Paper-Scissors with a computer
 - If the person wins, then he wins that small board
 - If person and computer play the same hand (ex. both play paper), then the two players will play on the small board again
 - If the computer wins, then the other player will win the board

If a tie occurs on the big board, then it will just be declared as a tie.
Once a person has won the game, then there will be a message that declares that the player has won!

<h3> Features </h3>

Improved GUI Design:
 - X's (blue) and O's (orange) are colored on the small board
 - Big X and Big O are colored
 - "Exit" and "Restart" buttons colored
 - Colored background of buttons
 - Lines created using Graphics2D
 - JLabel describing whose turn it currently is
 - JLabel describing the winner of a game (or a tie)
 
Unique way of tying (using a JOptionPane):
 - Current player must win a game of Rock-Paper-Scissors against a computer
 - If player wins, he wins the small board. If computer wins, other player wins the small board.
 - If they play the same hand, then the small board is replayed.

<h3> Authors </h3>
This game was made by students at the University of Virginia:

Curtis (curtisxk38), Joshua (jy2xj), Savannah (rad-biome), John (jwb7qw)

<h6>Notice: If there are any bugs or errors, please let one of the authors know.</h6>


<h2> Enjoy! </h2>
