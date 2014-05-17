package alienPong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class BarComponent {
	public void paint (Graphics2D g2, double x, double y) {
		Rectangle2D.Double bar = new Rectangle2D.Double(x,y,20,100);
		g2.setColor(Color.DARK_GRAY);
		g2.draw(bar);
		g2.fill(bar);
	}
}
