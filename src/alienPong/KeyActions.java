package alienPong;

import java.awt.event.KeyEvent;

public class KeyActions implements java.awt.event.KeyListener {
	
	int mode; // 1=in-game  2=after score
	GameGraphicsComponent component;
	AlienPongInstance gameInstance;
	
	//constructor for keys while playing (loadGameGUI; mode = 1)
	public KeyActions (int m, GameGraphicsComponent comp) {
		mode = m;
		component = comp;
	}


	@Override
	public void keyPressed(KeyEvent e) {
		//key down pressed
		if(e.getKeyCode() == 40){
			component.bar1Down();
		}
	
		//key up pressed
		if(e.getKeyCode() == 38){
			component.bar1Up();
		}
	
		//key right pressed
		if (e.getKeyCode() == 39) {
			component.bar1Smash();
		}
				
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
