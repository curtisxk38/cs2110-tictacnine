import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class TicTacNineGUI{
	int playerTurn;
	TicTacNine game;
	
	int width = 500;
	int height = 600;
	
	boolean toggleNeeded;
	
	ArrayList<JButton> mainButtons = new ArrayList<JButton>();
	
	// Keep track of which 3x3 is currently being played on
	// because that 3x3 is what smallBoars should be;
	int[] currentSmallBoard = {-1, -1};
	
	public TicTacNineGUI () {
		createAndShowGUI();
		game = new TicTacNine();
		playerTurn = 1;
		toggleNeeded = true;
	}
	private void clearSmall() {
		game.clearArray(game.smallBoard);
		for(JButton b : mainButtons) {
			ClickAction a = (ClickAction)b.getAction();
			if(a != null && a.getBigX() == currentSmallBoard[0] && a.getBigY() == currentSmallBoard[1]) {
				b.setText(" ");
			}	
		}
	}
	
	
	private void clearBig() {
		// Currently not what it should be
		game.clearArray(game.smallBoard);
		game.clearArray(game.bigBoard);
		for(JButton x : mainButtons) {
			x.setText(" ");
		}
		playerTurn = 1;
	}
	
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("TicTacNine");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, width, height);
		
		// --------------------------------------
		//Make buttons
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				JButton x = new JButton(" ");
				x.setBounds(25 + i * 50, 25 + j*50, 50, 50);
				x.addActionListener(new ClickAction(x, j%3, i%3, j/3, i/3));
				frame.add(x);
				x.setContentAreaFilled(false);
				x.setFocusPainted(false);
				x.setOpaque(false);
				mainButtons.add(x);
			}
		}
		
		//Make quit button
		JButton quitButton = new JButton("Exit");
		quitButton.setBounds(width/2 - 90, height - 80, 80, 50);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		frame.add(quitButton);
		
		//Make Restart button
		JButton restartButton = new JButton("Restart");
		restartButton.setBounds(width/2, height - 80, 90, 50);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				clearBig();
			}
		});
		frame.add(restartButton);

		// Display the window
		frame.setLayout(null);
		frame.setLocationRelativeTo(null); // center it
		frame.setVisible(true);
	}
	
	private void gameTick() {
		if(game.checkWinner(game.smallBoard)) {
			if(game.checkWinner(game.bigBoard)) {
				// Win entire game
				System.out.println("P" + playerTurn + "wins the whole game!");
			}
			// Win current smallBoard
			System.out.println("P" +playerTurn + " wins this spot!");
			game.setBigBoard(currentSmallBoard[0], currentSmallBoard[1], playerTurn);
			clearSmall();
			
			//Draw some visual for winning this square on the bigboard
			
			// Reset the array that keeps track of where the smallBoard is
			currentSmallBoard[0] = -1;
			currentSmallBoard[1] = -1;
			// We don't want to toggle players, because the player who won gets to go again
			toggleNeeded = false;
			
		}
		else if(game.isArrayFull(game.smallBoard)) {
			// Tie on smallBoard
			// We decided to make the players replay the small game until there is no tie
			clearSmall();
			System.out.println("Tie! Keep playing until one player wins!");
		}
		else if(game.isArrayFull(game.bigBoard)) {
			// The big board has tied
			System.out.println("The game ends in a tie.");
		}
		if(toggleNeeded) {
			togglePlayers();
		}
		toggleNeeded = true;
		//game.printArray(game.smallBoard);

	}
	private void togglePlayers() {
		// Toggle turns
		if(playerTurn == 1) {
			playerTurn = 2;
		}
		else {
			playerTurn = 1;
		}
	}
	
	private class ClickAction extends AbstractAction {
		private int smallX;
		private int smallY;
		private int bigX;
		private int bigY;
		private JButton b;
		
		public ClickAction(JButton button, int smallX, int smallY, int bigX, int bigY) {
			b = button;
			this.smallX = smallX;
			this.smallY = smallY;
			this.bigX = bigX;
			this.bigY = bigY;
		}
		public int getBigX() {
			return bigX;
		}
		public int getBigY() {
			return bigY;
		}
		public void debugPrint() {
			System.out.println("Small: (" + smallX + ", " + smallY + ")");
			System.out.println("Big: (" + bigX + ", " + bigY + ")");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currentSmallBoard[0] == -1 && currentSmallBoard[1] == -1) {
				currentSmallBoard[0] = bigX;
				currentSmallBoard[1] = bigY;
			}
			if(bigX == currentSmallBoard[0] && bigY == currentSmallBoard[1] && b.getText().equals(" ")) {
				game.takeTurn(smallX, smallY, playerTurn);
				debugPrint();
				if (playerTurn == 1) {
					b.setText("X");
				}
				else {
					b.setText("O");
				}
				
				gameTick();
			}

		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TicTacNineGUI ex = new TicTacNineGUI();
            }
		});
	}
}
