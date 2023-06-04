package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame();
		
		window.setSize(400, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to exit when click on x
		window.setLocationRelativeTo(null); //display at center
		window.setResizable(false); //disallow resizing
		window.setTitle("DA GAME");
		
		GamePanel gamePanel = new GamePanel(); //jpanel with extra specs
		window.add(gamePanel);
		window.pack(); //uses the gamepanel

		window.setVisible(true); //set visibility to true after adding all components
		
		window.requestFocusInWindow(); // request focus on JFrame
		gamePanel.requestFocusInWindow(); // request focus on GamePanel
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
