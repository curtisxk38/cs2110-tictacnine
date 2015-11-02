import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class TicTacNineGUI{
	int playerTurn;
	TicTacNine game;
	
	int width = 350;
	int height = 400;
	
	
	JFrame winFrame = new JFrame();
	
	public TicTacNineGUI () {
		createAndShowGUI();
		game = new TicTacNine();
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
		
		
		winFrame.setBounds(width/2, height- 100, 100, 100);
		// --------------------------------------
		//Make buttons
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				JButton x = new JButton(" ");
				x.setBounds(50 + i * 100, 50 + j*100, 50, 50);
				x.addActionListener(new ClickAction(x, i, j));
				frame.add(x);
			}
		}
		//Win text
		JTextArea winText = new JTextArea("Hello");
		winText.setBounds(width/2, height - 60, 50, 50);
		winFrame.add(winText);
		// Display the window
		frame.setLayout(null);
		frame.setLocationRelativeTo(null); // center it
		frame.setVisible(true);
	}
	private void gameTick() {
		if(game.checkWinner(game.smallBoard)) {
			winFrame.setVisible(true);
		}
		else if(game.isArrayFull(game.smallBoard)) {
			//tie
		}
		
		// Toggle turns
		if(playerTurn == 1) {
			playerTurn = 2;
		}
		else {
			playerTurn = 1;
		}

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
