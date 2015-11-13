import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TicTacNineGUI{
	
	JFrame frame;
	
	TicTacNine game;
	int playerTurn;
	
	int width = 500;
	int height = 620;
	
	boolean toggleNeeded;
	TicTacGraphics graph;
	JLabel l = new JLabel();
	JLabel turnDisplay = new JLabel();
	boolean won = false;
	
	private BufferedImage oImage;
	private BufferedImage xImage;
	
	ArrayList<BoardTile> mainButtons = new ArrayList<BoardTile>();
	
	// Keep track of which 3x3 is currently being played on
	// because that 3x3 is what smallBoars should be;
	int[] currentSmallBoard = {-1, -1};
	
	public TicTacNineGUI () {
		game = new TicTacNine();
		graph = new TicTacGraphics(game.bigBoard);
		frame = new JFrame("TicTacNine");
		playerTurn = 1;
		toggleNeeded = true;
		createAndShowGUI();
		loadImage();
	}
	private void loadImage() {
		try {
			oImage = ImageIO.read(new File("o.png"));	
			xImage = ImageIO.read(new File("x.png"));
		} catch (IOException x) {
			System.out.println(x);
		}
	}
	
	private void clearSmall(boolean tie) {
		//If there is a tie in the game, this will be run
		if (tie){
		Object[] options = {"Rock",
                "Paper",
                "Scissors"};
		int n = JOptionPane.showOptionDialog(frame,
			    "Let's play Rock, Paper, Scissors (Player who just went clicks)",
			    "The Tie Breaker!!!! (Beat the Computer!)",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.WARNING_MESSAGE,
			    null,
			    options,
			    options[2]);
		System.out.println(n);
		//keep running the option pane until an option is chosen
		while (n == -1) {
			n = JOptionPane.showOptionDialog(frame,
				    "Let's play Rock, Paper, Scissors (Player who just went clicks)",
				    "The Tie Breaker!!!! (Beat the Computer!)",
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.WARNING_MESSAGE,
				    null,
				    options,
				    options[2]);
			System.out.println(n);
		}
		Random rn = new Random();
		int comp = rn.nextInt(3);
		System.out.println(comp);
		//if the current player and computer both play the same hand
		if (n == comp) {
			game.clearArray(game.smallBoard);
			for(BoardTile x : mainButtons) {
				if(x.r/3 == currentSmallBoard[1] && x.c/3 == currentSmallBoard[0]) {
					x.getButton().setText(" ");
					if(!tie) {
						x.getButton().setVisible(false);
					}
				}
			}
			l.setText("Choose the same hand("+ options[n] +")? So Play again!");
		}
		//the current player wins the small board if he wins the game
		else if((n == 0 && comp == 2) || (n == 1 && comp == 0) | (n == 2 && comp == 1)){
			l.setText("P" +playerTurn + " wins this spot! " + options[n] + " > " + options[comp]);
			game.setBigBoard(currentSmallBoard[0], currentSmallBoard[1], playerTurn);
			clearSmall(false);
			currentSmallBoard[0] = -1;
			currentSmallBoard[1] = -1;
			toggleNeeded = false;
		}
		//the other player wins the small board if computer wins
		else if((n == 0 && comp == 1) || (n == 1 && comp == 2) || (n == 2 && comp == 0)){
			if (playerTurn == 2) {
				game.setBigBoard(currentSmallBoard[0], currentSmallBoard[1], (playerTurn-1));;
				//System.out.println("P" + (playerTurn-1) + " wins this spot!");
				l.setText("P" + (playerTurn-1) + " wins this spot! " + options[comp] + " > " + options[n]);
				clearSmall(false);
				}
			else if (playerTurn == 1){
				game.setBigBoard(currentSmallBoard[0], currentSmallBoard[1], (playerTurn+1));
				//System.out.println("P" + (playerTurn+1) + " wins this spot!");
				l.setText("P" + (playerTurn+1) + " wins this spot! " + options[comp] + " > " + options[n]);
				clearSmall(false);
				}
			currentSmallBoard[0] = -1;
			currentSmallBoard[1] = -1;
			}
		}
		else{
			game.clearArray(game.smallBoard);
			for(BoardTile x : mainButtons) {
				if(x.r/3 == currentSmallBoard[1] && x.c/3 == currentSmallBoard[0]) {
					x.getButton().setText(" ");
					if(!tie) {
						x.getButton().setVisible(false);
					}
				}
			}
		}
		
	}
	
	private void clearBig() {
		game.clearArray(game.smallBoard);
		game.clearArray(game.bigBoard);
		for(BoardTile x : mainButtons) {
			x.getButton().setText(" ");
		}			
		// Reset the array that keeps track of where the smallBoard is
		currentSmallBoard[0] = -1;
		currentSmallBoard[1] = -1;
		playerTurn = 1;
	}
	
	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private void createAndShowGUI() {
		// Set up the window.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, width, height);
		frame.getContentPane().setLayout(null);
		// --------------------------------------
		//Make buttons
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				JButton x = new JButton(" ");
				x.setBounds(25 + i * 50, 25 + j*50, 50, 50);
				x.addActionListener(new ClickAction(x, j%3, i%3, j/3, i/3));
				frame.getContentPane().add(x);
				x.setContentAreaFilled(false);
				x.setFocusPainted(false);
				x.setOpaque(true);
				Color light_Blue = new Color(102,178,255);
				x.setBackground(light_Blue);
				mainButtons.add(new BoardTile(x, i, j));
			}
		}
		
		//Make quit button
		JButton quitButton = new JButton("Exit");
		quitButton.setForeground(Color.blue);
		quitButton.setBounds(width/2 - 90, height - 100, 80, 50);
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(quitButton);
		
		//Make Restart button
		JButton restartButton = new JButton("Restart");
		restartButton.setForeground(Color.red);
		restartButton.setBounds(width/2, height - 100, 90, 50);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				clearBig();
				l.setText("");
				for(BoardTile x: mainButtons) {
					x.getButton().setVisible(true);
				}
				won = false;
				turnDisplay.setText("Player " + playerTurn + "'s turn");
			}
		});

		frame.getContentPane().add(restartButton);
		// graphics
		frame.setGlassPane(graph);
		graph.setVisible(true);
		l.setBounds(new Rectangle(10, 480, 300, 30));
		l.setText("");
		turnDisplay.setBounds(380, 10, 200, 10);
		turnDisplay.setText("Player " + playerTurn + "'s turn");
		frame.getContentPane().add(l);
		frame.getContentPane().add(turnDisplay);
		// Display the window
		frame.setLocationRelativeTo(null); // center it
		frame.setVisible(true);
		graph.repaint();
	}
	
	private void gameTick() {
		if(!l.getText().equals("")) {
			l.setText("");
		}
		if(game.checkWinner(game.smallBoard)) {
			game.setBigBoard(currentSmallBoard[0], currentSmallBoard[1], playerTurn);
			if(game.checkWinner(game.bigBoard)) {
				// Win entire game
				System.out.println("P" + playerTurn + " wins the whole game!");
				l.setText("P" + playerTurn + " wins the whole game!");
				clearSmall(false);
				currentSmallBoard[0] = -1;
				currentSmallBoard[1] = -1;
				won = true;
				return;
			}
			// Win current smallBoard
			System.out.println("P" +playerTurn + " wins this spot!");
			l.setText("P" +playerTurn + " wins this spot!");
			clearSmall(false);
			
			//Draw some visual for winning this square on the bigboard

			// Reset the array that keeps track of where the smallBoard is
			currentSmallBoard[0] = -1;
			currentSmallBoard[1] = -1;
			// We don't want to toggle players, because the player who won gets to go again
			toggleNeeded = false;
			
		}
		else if(game.isArrayFull(game.smallBoard)) {
			// Tie on smallBoard
			// We decided to make the players play a game of Rock-Paper-Scissors
			clearSmall(true);
		}
		if(game.isArrayFull(game.bigBoard)) {
			// The big board has tied
			System.out.println("The game ends in a tie.");
			l.setText("The game ends in a tie.");
		}
		if(toggleNeeded) {
			togglePlayers();
		}
		turnDisplay.setText("Player " + playerTurn + "'s turn");
		toggleNeeded = true;

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
	class BoardTile {
		JButton b;
		public int r;
		public int c;
		public BoardTile(JButton b, int r, int c) {
			this.b = b;
			this.r = r;
			this.c = c;
		}
		public JButton getButton() {
			return b;
		}
	}
	class TicTacGraphics extends JComponent {
		int[][]bigBoard;
		public TicTacGraphics(int[][]b) {
			super();
			bigBoard = b;
			setVisible(true);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
            //System.out.print("t");
			for (int i = 0; i < bigBoard.length; i ++) {
				for (int j = 0; j < bigBoard[0].length; j++) {
					if(bigBoard[i][j] == 1) {
						drawX(g, i, j);
					}
					if(bigBoard[i][j] == 2) {
						drawO(g, i, j);
					}
				}
			}

			
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g2.setStroke(new BasicStroke(3));
			g2.drawLine(175, 27, 175, 470);
			g2.drawLine(325, 27, 325, 470);
			g2.drawLine(24, 174, 475, 174);
			g2.drawLine(24, 324, 475, 324);
		}
		private void drawX(Graphics g, int c, int r) {
			g.drawImage(xImage, 25 + r * 150, 25 + c * 150, null);
		}
		private void drawO(Graphics g, int c, int r) {
			g.drawImage(oImage, 25 + r * 150, 25 + c * 150, null);
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
			//debugPrint();
			graph.repaint();
			if(game.bigBoard[bigX][bigY] == 0 && currentSmallBoard[0] == -1 && currentSmallBoard[1] == -1) {
				currentSmallBoard[0] = bigX;
				currentSmallBoard[1] = bigY;
			}
			if(won == false && bigX == currentSmallBoard[0] && bigY == currentSmallBoard[1] && b.getText().equals(" ")) {
				game.takeTurn(smallX, smallY, playerTurn);
				if (playerTurn == 1) {
					b.setText("X");
					b.setFont(b.getFont().deriveFont(18.0f));
					b.setForeground(Color.blue);
				}
				else {
					b.setText("O");
					b.setFont(b.getFont().deriveFont(18.0f));
					Color dark_Orange = new Color(255,128,0);
					b.setForeground(dark_Orange);
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
