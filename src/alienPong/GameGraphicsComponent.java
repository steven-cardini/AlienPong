package alienPong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Date;
import java.util.Random;

import javax.swing.JPanel;

public class GameGraphicsComponent extends JPanel {
	
	private AlienComponent alienBall;
	private BarComponent bar1 = new BarComponent();
	private BarComponent bar2 = new BarComponent();
	private double xAlien, yAlien, xBar1, yBar1, xBar2, yBar2;
	private int smashBalance;
	private final int POINTS_PLAYER, POINTS_COMPUTER;
	private final double SPEED_BAR2 = 0.25;  //speed of the computer's bar in Pixel/ms
	private final  Dimension DIM_WINDOW, DIM_BALL;
	private boolean dirUp,dirDown,dirLeft,dirRight,bar1Smashing,bar2Smashing,playerScored,computerScored;
	private long timeLastSmashAttempt, timeLastSmashActual;	

	//constructor
	public GameGraphicsComponent (Dimension dimWindow, Dimension dimBall, int pPlayer, int pComputer) {
		setBackground(Color.LIGHT_GRAY);
		this.setDoubleBuffered(true);
		
		DIM_WINDOW = dimWindow;
		DIM_BALL = dimBall;
		alienBall = new AlienComponent(DIM_BALL.getWidth(),DIM_BALL.getHeight());
		
		POINTS_PLAYER = pPlayer;
		POINTS_COMPUTER = pComputer;
		
		//x and y pos of the ball are initialized randomly
		Random randomNumber = new Random();
		double r1 = randomNumber.nextDouble();
		double xAlienMin = 0.8*0.5*(DIM_WINDOW.getWidth()+DIM_BALL.getWidth());
		double xAlienMax = 1.2*0.5*(DIM_WINDOW.getWidth()+DIM_BALL.getWidth());
		xAlien = xAlienMin + r1 * (xAlienMax-xAlienMin);
		double r2 = randomNumber.nextDouble();
		double yAlienMin = 0.8*0.5*(DIM_WINDOW.getHeight()+DIM_BALL.getHeight());
		double yAlienMax = 1.2*0.5*(DIM_WINDOW.getHeight()+DIM_BALL.getHeight());
		yAlien = yAlienMin + r2 * (yAlienMax-yAlienMin);
				
		xBar1 = 20;
		xBar2= DIM_WINDOW.getWidth()-40;
		yBar1=0.5*DIM_WINDOW.getHeight()-100;
		yBar2=0.5*DIM_WINDOW.getHeight()-100;
		
		//the initial direction of the ball is determined
		if (xAlien>(xAlienMax+xAlienMin)*0.5) {
			dirLeft=true;
		} else {
			dirRight=true;
		}
		
		if (yAlien>(yAlienMax+yAlienMin)*0.5) {
			dirUp = true;
		} else {
			dirDown = true;
		}
		
		smashBalance = 0;
		timeLastSmashActual = 0;
		timeLastSmashAttempt = 0;
		
	}
		
	//method to determine if the player's bar hit the ball
	private boolean hitBar1 () {
		Rectangle2D.Double bar1Rectangle = new Rectangle2D.Double(xBar1,yBar1+10,20,100-20);
		Rectangle2D bar1Bounds = bar1Rectangle.getBounds2D();
		Ellipse2D.Double alienBallEllipse = alienBall.getOutline();
		return (alienBallEllipse.intersects(bar1Bounds) && xAlien>=xBar1 );
	}
	
	//method to determine if the player's bar hit the ball while smashing
	private boolean hitBar1Smashed () {
		Rectangle2D.Double bar1SmashedRectangle = new Rectangle2D.Double(xBar1+10,yBar1+10,20+40,100-20);
		Rectangle2D bar1SmashedBounds = bar1SmashedRectangle.getBounds2D();
		Ellipse2D.Double alienBallEllipse = alienBall.getOutline();
		return (alienBallEllipse.intersects(bar1SmashedBounds) && xAlien>=xBar1 );
	}
		
	//method to determine if the computer's bar hit the ball
	private boolean hitBar2 () {
		Rectangle2D.Double bar2Rectangle = new Rectangle2D.Double(xBar2,yBar2+10,20,100-20);
		Rectangle2D bar2Bounds = bar2Rectangle.getBounds2D();
		Ellipse2D.Double alienBallEllipse = alienBall.getOutline();
		return alienBallEllipse.intersects(bar2Bounds);		
	}
		
		
	//method to determine if the ball has collided with a border of the frame
	private int hitBorder () {
		Ellipse2D.Double alienBallEllipse = alienBall.getOutline();
		
		if (alienBallEllipse.intersects(0.01, 0.01, DIM_WINDOW.getWidth(), 0.01)) {		//hit top border
			//System.out.println("hit top");
			return 1;
		}
			
		else if (alienBallEllipse.intersects(0, DIM_WINDOW.getHeight()-23, DIM_WINDOW.getWidth(), DIM_WINDOW.getHeight()-23)) {	//hit bottom border
			//System.out.println("hit bottom");
			return 3;
		}
			
		else if (alienBallEllipse.intersects(0.01, 0.01, 0.01, DIM_WINDOW.getHeight())) {	//hit left border
			//System.out.println("hit left");
			return 4;
		}
			
		else if (alienBallEllipse.intersects(DIM_WINDOW.getWidth(), 0.01, DIM_WINDOW.getWidth(), DIM_WINDOW.getHeight())) {	//hit right border
			//System.out.println("hit right");
			return 2;
		}
		else {
			return 0;
		}
			
	}
	
	//method to determine what action needs to be carried out when a collision occurred
	private void determineDirection () {
		if (dirLeft && this.hitBar1()) {
			this.directionChange('r');
			//System.out.println("hit bar 1");
		}
		
		if (dirRight && this.hitBar2()) {
			this.directionChange('l');
			//System.out.println("hit bar 2!");
		}
		
		int borderHit = this.hitBorder();
		
		if (borderHit==1) {
			this.directionChange('d');
			//System.out.println("down!");
		}
		
		//player made a point
		if (borderHit==2) {
			playerScored = true;
		}
		
		if (borderHit==3) {
			this.directionChange('u');
			//System.out.println("up!");
		}
		
		//computer made a point
		if (borderHit==4) {
			computerScored=true;
		}
		
	}
	
	//method to initiate a change of the ball's movement direction
	private void directionChange (char dir) {
		switch (dir) {
		case 'u':
			dirDown = false;
			dirUp = true;
			break;
		case 'd':
			dirDown = true;
			dirUp = false;
			break;
		case 'r':
			dirRight = true;
			dirLeft = false;
			break;
		case 'l':
			dirRight = false;
			dirLeft = true;
			break;
		}
	}
	
	//move computer's bar automatically (AI)
	private void moveComputerBar (long lastPaintTimepoint) {
		long currentTimePoint = System.currentTimeMillis();
		if (currentTimePoint-lastPaintTimepoint<0 || currentTimePoint-lastPaintTimepoint>100) {
			yBar2=yBar2;
		} else if (dirLeft && yBar2<(0.5*DIM_WINDOW.getHeight())-103) {
   			yBar2=yBar2+(currentTimePoint-lastPaintTimepoint)*SPEED_BAR2;
   		} else if (dirLeft && yBar2>(0.5*DIM_WINDOW.getHeight())-97) {
   			yBar2=yBar2-(currentTimePoint-lastPaintTimepoint)*SPEED_BAR2;
   		} else if (dirRight && yBar2<yAlien-40 && yBar2 <= DIM_WINDOW.getHeight()-142) {
   			yBar2=yBar2+(currentTimePoint-lastPaintTimepoint)*SPEED_BAR2;
   		} else if (dirRight && yBar2>yAlien-40 && yBar2 >= 30) {
   			yBar2=yBar2-(currentTimePoint-lastPaintTimepoint)*SPEED_BAR2;
   		}
	}
	
	
	//move player's bar up
	public void bar1Up () {
		if (!bar1Smashing && yBar1 >= 30) {
			yBar1 -= 30;
		}
	}
	
	//move player's bar down
	public void bar1Down () {
		if (!bar1Smashing && yBar1 <= DIM_WINDOW.getHeight()-142) {
			yBar1 += 30;
		}
	}
	
	//smash ball on pressing arrow right button
	public void bar1Smash () {	
		//only execute smash if last smash was 2s or more ago
		if ( (System.currentTimeMillis()-timeLastSmashAttempt) >= 2000) {
			timeLastSmashAttempt = System.currentTimeMillis();
			bar1Smashing = true;
			xBar1 = 40;
		}
		
		//execute smash
		if (this.hitBar1Smashed()) {
			timeLastSmashActual = System.currentTimeMillis();
			smashBalance++;
		}
			
	}
	
	//paint graphic components
	public void paint (Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		bar1.paint(g2, xBar1, yBar1);
		bar2.paint(g2, DIM_WINDOW.getWidth()-40, yBar2);

		g2.setColor(Color.BLUE);
		g2.drawString("Player: " +POINTS_PLAYER,20, 20);
		g2.drawString("Computer: " +POINTS_COMPUTER, (int)(DIM_WINDOW.getWidth())-100, 20);
		if (System.currentTimeMillis()-timeLastSmashActual<=2500) {
			g2.setColor(Color.RED);
			g2.drawString("Smash!",(int)(0.5*DIM_WINDOW.getWidth()-10), (int)(DIM_WINDOW.getHeight()-100));
		}
			
		alienBall.paint(g2,xAlien,yAlien);
		g2.dispose();		
	}
	
	
	public void doRepaint (double dx, double dy, long lastPaintTimepoint) {
		      	
       	//check if smash needs to be terminated (when smash start initiated 0.8s ago)
   		if (bar1Smashing && (System.currentTimeMillis()-timeLastSmashAttempt)>=800 ) {
   			bar1Smashing = false; 
   			xBar1 = 20;
   		}
   		
   		/*
   		//set ballAccelerator to zero 8s after last Smash
   		if (smashBalance>0 && System.currentTimeMillis()-timeLastSmashActual>=8000) {
   			//ballAccelerator=1;
   			smashBalance=0;
   			//System.out.println("speed set to 0!");
   		}
   		*/
   		
   		this.determineDirection();
   		
   		this.moveComputerBar(lastPaintTimepoint);
   		      
   		if (dirLeft) {
   			dx = 0-dx;
   		}
   		
   		if (dirUp) {
   			dy = 0-dy;
   		}
   		
   		xAlien = xAlien+dx%10;
   		yAlien = yAlien+dy%10;
   		
            
        this.repaint();
            
	}
	
	//get the amount of smashes that are currently relevant
	public int getSmashBalance () {
		return smashBalance;
	}
	
	//method is invoked by AlienPong after each repainting
	public boolean playerScored () {
		return playerScored;
	}
	
	//method is invoked by AlienPong after each repainting
	public boolean computerScored () {
		return computerScored;
	}
		
}
