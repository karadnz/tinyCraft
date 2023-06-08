package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    //DEBUG
    boolean isDebug = false;

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("HITTED A KEY");

        int code = e.getKeyCode();
        

        if (code == KeyEvent.VK_W) {
            //System.out.println("HIT");
            upPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            //System.out.println("HIT");
            downPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            //System.out.println("HIT");
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            //System.out.println("HIT");
            rightPressed = true;
        }
        
        if (code == KeyEvent.VK_T) {
            //System.out.println("HIT");
            isDebug = !isDebug;
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
