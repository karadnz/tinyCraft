package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.GamePanel.GameState;

public class KeyHandler extends KeyAdapter {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    GamePanel gp;
    
    //DEBUG
    boolean isDebug = false;
    
    public KeyHandler(GamePanel gp)
    {
    	this.gp = gp;
    }
    
    public void handlePressPlay(int code)
    {
    	        
        switch (code)
        {
        case KeyEvent.VK_W:
        	upPressed = true;
        	break;
        case KeyEvent.VK_S:
        	downPressed = true;
        	break;
        case KeyEvent.VK_A:
        	leftPressed = true;
        	break;
        case KeyEvent.VK_D:
        	rightPressed = true;
        	break;
        case KeyEvent.VK_T:
        	isDebug = !isDebug;
        	break;
        case KeyEvent.VK_SPACE:
        	gp.gameState = GameState.PAUSE;
        	break;
        case KeyEvent.VK_ENTER:
        	enterPressed = true;
        	break;
        }
    	
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("HITTED A KEY");

        int code = e.getKeyCode();
        
        switch(gp.gameState)
        {
		case DIALOGUE:
			gp.gameState = GameState.PLAY;
		 if (code == KeyEvent.VK_ENTER)
        	enterPressed = !enterPressed;
        	break;
		case PAUSE:
			gp.gameState = GameState.PLAY;
			break;
		case PLAY:
			handlePressPlay(code);
			break;
		case UNDEFINED:
			gp.gameState = GameState.PLAY;
			break;
		default:
			break;
        }
        
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //System.out.println("RELEASED A KEY");

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        
  
    }
}
