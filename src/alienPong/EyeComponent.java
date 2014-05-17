package alienPong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class EyeComponent {

	private int x = (int)0.7*77;
	private int y = (int)0.7*80;
	private int w = (int)0.7*20;
	private int h = (int)0.7*30;
	
	
	public void paint (Graphics2D g2) {
		
		
		Ellipse2D.Float eyeOuter = new Ellipse2D.Float(x, y, w, h);
		Ellipse2D.Float eyeMiddle = new Ellipse2D.Float((int) (x+0.25*w),(int) (y+0.3*h),(int) 0.7*w,(int) 0.7*h);
		Ellipse2D.Float eyeInner = new Ellipse2D.Float ((int) (x+0.4*w),(int) (y+0.4*h),(int) (0.3*w),(int) (0.3*h));
		
		g2.setColor(Color.YELLOW);
		g2.draw(eyeOuter);
		g2.fill(eyeOuter);
		g2.setColor(Color.RED);
		g2.draw(eyeMiddle);
		g2.fill(eyeMiddle);
		g2.setColor(Color.BLACK);
		g2.draw(eyeInner);
		g2.fill(eyeInner);
		
		g2.setColor(Color.BLACK);
		g2.draw(eyeOuter);
		
		g2.translate(40, 2);
		
		g2.setColor(Color.YELLOW);
		g2.draw(eyeOuter);
		g2.fill(eyeOuter);
		g2.setColor(Color.RED);
		g2.draw(eyeMiddle);
		g2.fill(eyeMiddle);
		g2.setColor(Color.BLACK);
		g2.draw(eyeInner);
		g2.fill(eyeInner);
		
		g2.setColor(Color.BLACK);
		g2.draw(eyeOuter);
	}

}
