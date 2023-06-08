package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.*;

public class UI {
	
	GamePanel gp;
	Font arial_20B, arial_40B;
	BufferedImage keyImage;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public boolean gameFinished = false;
	
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp)
	{
		this.gp = gp;
		arial_20B = new Font("Ariel", Font.BOLD, 20);
		arial_40B = new Font("Ariel", Font.BOLD, 40);//to avoid creating an instance each time draw called
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}
	
	public void showMessage(String text)
	{
		message = text;
		messageOn = true;
	}
	
	
	public void draw(Graphics2D g2)
	{
		
		
		if (gameFinished == true)
		{
			g2.setFont(arial_20B);
			g2.setColor(Color.white);
			
			String text = "You just found the treasure of Long John Silver!";
			int len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			int x = gp.screenWidth / 2 - len / 2;
			int y = gp.screenHeight / 2 - (gp.tileSize * 3);
			g2.drawString(text , x, y);
			
			
			double playTime = ((double)System.nanoTime() - (double)gp.startTime) / 1000000000D;
			text = "Time: "+ dFormat.format(playTime);
			len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth / 2 - len / 2;
			y = gp.screenHeight / 2 - (gp.tileSize * 2);
			g2.drawString(text , x, y);
			
			
			g2.setFont(arial_40B);
			g2.setColor(Color.yellow);
			
			text = "Congrats!";
			len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth / 2 - len / 2;
			y = gp.screenHeight / 2 - (gp.tileSize);
			g2.drawString(text , x, y);
			
			gp.gameThread = null; //
			
			return;
		}
		
		g2.setFont(arial_20B);
		g2.setColor(Color.white);
		g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
		g2.drawString("x  " + gp.player.keyCount, 74, 65); //y indicates baseline of the string
		
		double playTime = ((double)System.nanoTime() - (double)gp.startTime) / 1000000000D;
		g2.drawString("Time: "+ dFormat.format(playTime), gp.tileSize * 11,  65);
		
		
		
		if (messageOn == true)
		{
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.tileSize/2, gp.tileSize * 5);
			
			messageCounter++;
			//System.out.println(messageCounter);
			
			if (messageCounter > 120)
			{
				messageCounter = 0;
				messageOn = false;
			}
		}
		
		
		
	}

}
