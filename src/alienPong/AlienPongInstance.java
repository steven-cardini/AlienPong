package alienPong;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AlienPongInstance {

	private GameGraphicsComponent component;
	private JFrame gameFrame; //messageFrame;
	private final static Dimension DIM_WINDOW = new Dimension(1000,800);
	private final static double BALL_WIDTH_0 = 130;
	private final static double BALL_HEIGTH_0 = 120;
	//private final static Dimension DIM_MESSAGE = new Dimension (200,60);
	private static double speed_x; // horizontal speed in pixel / ms when not smashing
	private static double speed_y; // vertical speed in pixel / ms when not smashing
	private final static double ACCELERATION_X = 0.4; // acceleration of horiontal speed while smashing
	private final static double ACCELERATION_Y = 0.23; // acceleration of vertical speed while smashing
	private int pointsPlayer, pointsComputer;
	private long lastPaintTimepoint;
	private KeyActions keyControls;
	
	public AlienPongInstance () {
		this.pointsPlayer = 0;
		this.pointsComputer = 0;
		this.lastPaintTimepoint=System.currentTimeMillis();
	}
	
	public void loadGameGUI (int pointsPlayer, int pointsComputer) {
		
		this.pointsPlayer = pointsPlayer;
		this.pointsComputer = pointsComputer;
		
		Random randomNumber = new Random();
		double r1 = randomNumber.nextDouble();
		speed_x = 0.23 + r1 * (0.28-0.23);
		double r2 = randomNumber.nextDouble();
		speed_y = 0.20 + r2 * (0.25-0.20);
		
		double resizeFactor = (10-pointsPlayer)*0.1;
		component = new GameGraphicsComponent(DIM_WINDOW, new Dimension((int) (resizeFactor*BALL_WIDTH_0),(int) (resizeFactor*BALL_HEIGTH_0)), pointsPlayer, pointsComputer);
		keyControls = new KeyActions(1,component);
		
		gameFrame = new JFrame();
		gameFrame.setSize(DIM_WINDOW);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setTitle("AlienPong 0.3");
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setResizable(false);
		gameFrame.add(component);
		gameFrame.addKeyListener(keyControls);
		gameFrame.setVisible(true);
	}
	
	// 1 = player scored, 2 = computer scored
	public void loadScoreMessage (int whoScored) {
				
		if (whoScored==1) {
			JOptionPane.showMessageDialog(gameFrame, "You scored! Press Enter to resume game.");
		} else {
			JOptionPane.showMessageDialog(gameFrame, "Your enemy scored! Press Enter to resume game.");
		}
		
		component.removeAll();
		gameFrame.dispose();
		
	}
		
	// Method calculates the speed of the ball in Px/ms; index 0 = x; index 1 = y
	private double[] getSpeed () {
		double[] speed = new double[2];
		speed[0] = (1+component.getSmashBalance()*ACCELERATION_X*speed_x)*speed_x;
		speed[1] = (1+component.getSmashBalance()*ACCELERATION_Y*speed_y)*speed_y;
		return speed;
	}
	
	//returns 0: no score, 1: player scored, 2: computer scored
	public int animateGame () {
		
		if (System.currentTimeMillis()-lastPaintTimepoint>=1) {
			double[] speed = getSpeed();
			double dx = (System.currentTimeMillis()-lastPaintTimepoint)*speed[0];
			double dy = (System.currentTimeMillis()-lastPaintTimepoint)*speed[1];
			component.doRepaint(dx,dy,lastPaintTimepoint);
			lastPaintTimepoint=System.currentTimeMillis();
		}
				
		if (component.playerScored()) {
			return 1;
		} else if (component.computerScored()) {
			return 2;
		} else {
			return 0;
		}
		
	}

}
