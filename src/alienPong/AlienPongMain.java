package alienPong;

import javax.swing.JOptionPane;

public class AlienPongMain {
	
	private static int pointsPlayer = 0;
	private static int pointsComputer = 0;
	private static int scored = 0;
	private static boolean gameRunning = true;
	private static AlienPongInstance gameInstance = new AlienPongInstance();
	
	//control routine for the whole game
	public static void main (String[] args) {
		
		while (pointsPlayer<10 && pointsComputer<10) {
			play();
		}
		
		if (pointsPlayer>=10) {
			JOptionPane.showMessageDialog(null, "Congratulation, you win!");
		} else {
			JOptionPane.showMessageDialog(null, "Oh no, you lost!");
		}
		
	}
	
	public static void play () {
		
		if (gameRunning) {			
			gameInstance.loadGameGUI(pointsPlayer, pointsComputer);
			
			while (scored==0) {
				scored=gameInstance.animateGame();
			}
			
			if (scored==1) {
				pointsPlayer++;
			}
			
			if (scored==2) {
				pointsComputer++;
			}
			
			gameRunning = false;
		}
		
		if (!gameRunning) {
			gameInstance.loadScoreMessage(scored);
			
			//code continues here after Enter was pressed
			scored = 0;
			gameRunning = true;
		}
	}
	
}
