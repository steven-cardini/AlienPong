package alienPong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class AlienComponent {
	
	private double w,h,x0,x1,x2,x3,x4,y0,y1,y2,y3,y4,topLeftX,topLeftY,bottomRightX,bottomRightY;
	
	public AlienComponent (double width, double height) {
		w = width;
		h = height;
	}

	private EyeComponent eye = new EyeComponent();
	
	public void paint (Graphics2D g2, double x, double y) {
	
		x0 = x-0.5*w;
		y0 = y-0.5*h;
		x4 = x+0.5*w;
		y4 = y+0.5*h;
		x1 = x0+w/3;
		y1 = y0+h/5;
		x2 = x;
		y2 = y;
		x3 = x4-w/3;
		y3 = y4-h/4;
		topLeftX = x1-(x1-x0)/2;
		topLeftY = y1-(y1-y0)/2;
		bottomRightX = x4-(x4-x3)/2;
		bottomRightY = y4-(y4-y3)/2;

		
		Rectangle2D.Double headInner = new Rectangle2D.Double(x1,y1,x3-x1,y3-y1);
		QuadCurve2D.Double headTopCurve = new QuadCurve2D.Double (x1,y1,x2,y0,x3,y1);
		QuadCurve2D.Double headLeft = new QuadCurve2D.Double (x1,y1,x0,y2,x1,y3);
		QuadCurve2D.Double headRight = new QuadCurve2D.Double (x3,y1,x4,y2,x3,y3);
		QuadCurve2D.Double headBottomCurve = new QuadCurve2D.Double(x1,y3,x2,y4,x3,y3);
		
		//construct head
		//g2.translate(x-x0, y-y0);
		g2.setColor(Color.GREEN);
		g2.draw(headInner);
		g2.draw(headTopCurve);
		g2.draw(headLeft);
		g2.draw(headRight);
		g2.draw(headBottomCurve);
		g2.fill(headInner);
		g2.fill(headTopCurve);
		g2.fill(headLeft);
		g2.fill(headRight);
		g2.fill(headBottomCurve);
		
		g2.setColor(Color.BLACK);
		g2.draw(headTopCurve);
		g2.draw(headLeft);
		g2.draw(headRight);
		g2.draw(headBottomCurve);
		
		//draws first eye
		/*Ellipse2D.Double eyeOuter = new Ellipse2D.Double(0.7*11*x0, x1, 2*x0, 3*x0);
		g2.setColor(Color.YELLOW);
		g2.draw(eyeOuter);
		g2.fill(eyeOuter);
		g2.setColor(Color.BLACK);
		g2.draw(eyeOuter); */
		
		//draws second eye
		/* g2.translate(28, 1.4);
		g2.setColor(Color.YELLOW);
		g2.draw(eyeOuter);
		g2.fill(eyeOuter);
		g2.setColor(Color.BLACK);
		g2.draw(eyeOuter); */
		
		
		
	}
	
	public Ellipse2D.Double getOutline() {
		return (new Ellipse2D.Double(topLeftX,topLeftY,bottomRightX-topLeftX,bottomRightY-topLeftY));
	}
}
