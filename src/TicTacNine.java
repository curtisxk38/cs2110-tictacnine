
public class TicTacNine {
	int[][] smallBoard;
	int[][] bigBoard;
	

	// x = 1
	// o = 2
	
	public TicTacNine () {
		smallBoard = new int[3][3];
		bigBoard = new int[3][3];
		
	}
	
	public void takeTurn(int r, int c, int player) {
		smallBoard[r][c] = player;
	}
	public void setBigBoard(int r, int c, int player) {
		bigBoard[r][c] = player;
	}
	
	public boolean checkWinner(int[][] myArray) {
		int firstSquare = 0;
		boolean winner = true;
		// Check rows
		for(int i = 0; i < myArray.length; i ++) {
			for(int j = 0; j < myArray[i].length; j++) {
				if(j == 0) {
					firstSquare = myArray[i][j];
				}
				else if (myArray[i][j] != firstSquare) {
					winner = false;
				}
			}
		}
		if (winner && firstSquare != 0) {
			return true;
		}
		// Check columns
		for (int j = 0; j< myArray[0].length; j++) {
			for(int i = 0; i<myArray.length; i ++) {
				if (i == 0) {
					firstSquare = myArray[i][j];
				}
				else if(myArray[i][j] != firstSquare) {
					winner = false;
				}
			}
		}
		if (winner && firstSquare != 0) {
			return true;
		}
		// Check diagonals
		winner = myArray[0][0] == myArray[1][1] && myArray[1][1] == myArray[2][2];
		if (winner && myArray[0][0] != 0) {
			return true;
		}
		winner = myArray[0][2] == myArray[1][1] && myArray[1][1] == myArray[0][2];
		if (winner && myArray[0][2] != 0) {
			return true;
		}
		
		return false;
	}
	
	public void clearArray(int[][] myArray) {
		for(int i = 0; i < myArray.length; i++) {
			for(int j = 0; j < myArray[i].length; j++) {
				myArray[i][j] = 0;
			}
		}
	}
	
}
