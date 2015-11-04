import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class TicTacNineGUI{
	int playerTurn;
	TicTacNine game;
	
	int width = 350;
	int height = 400;
	
	
	ArrayList<JButton> mainButtons = new ArrayList<JButton>();
	
	public TicTacNineGUI () {
		createAndShowGUI();
		game = new TicTacNine();
		playerTurn = 1;
	}
	
	private void properClear(int[][] myArray) {
		game.clearArray(myArray);
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
		frame.setBounds(100, 100, width, height);
		
		// --------------------------------------
		//Make buttons
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				JButton x = new JButton(" ");
				x.setBounds(50 + i * 100, 50 + j*100, 50, 50);
				x.addActionListener(new ClickAction(x, j, i));
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
				properClear(game.smallBoard);
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
			System.out.println("P" +playerTurn + " wins!");
		}
		else if(game.isArrayFull(game.smallBoard)) {
			properClear(game.smallBoard);
			System.out.println("Tie! Keep playing until one player wins!");
		}
		
		// Toggle turns
		if(playerTurn == 1) {
			playerTurn = 2;
		}
		else {
			playerTurn = 1;
		}
		//game.printArray();
		//System.out.println(game.checkColumns(game.smallBoard));

	}
	private class ClickAction extends AbstractAction {
		private int x;
		private int y;
		private JButton b;
		
		public ClickAction(JButton button, int x, int y) {
			b = button;
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(b.getText().equals(" ")) {
				game.takeTurn(x, y, playerTurn);
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
