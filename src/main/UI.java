package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.*;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_20B, arial_40B, arial_32P,maruMonica;
	BufferedImage keyImage;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public boolean gameFinished = false;
	
	public String currentDialogue = "";
	
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp)
	{
		this.gp = gp;
		arial_20B = new Font("Ariel", Font.BOLD, 20);
		arial_32P = new Font("Ariel", Font.PLAIN, 32);
		arial_40B = new Font("Ariel", Font.BOLD, 40);//to avoid creating an instance each time draw called
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/pixelNice.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text)
	{
		message = text;
		messageOn = true;
	}
	
	public int getXforCenteredText(String text) //do we really need it?
	{
		int len = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		
		return (gp.screenWidth / 2 - len / 2);
		
	}
	
	public void drawPauseScreen()
	{
		String text = "PAUSED";
		
		
		int x = getXforCenteredText(text); 
		int y = gp.screenHeight / 2 - (gp.tileSize * 3);
		g2.drawString(text , x, y);
	}
	
	public void drawDialogueScreen()
	{
		String text = "PAUSED";
		
		
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 5;
		
		drawSubWindow(x, y, width, height);
		
		x += gp.tileSize;
		y += gp.tileSize;
		setText(g2, arial_32P, new Color(255, 255, 255));
		for (String line : currentDialogue.split("\n")) //top split lines
		{
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height)
	{
		setText(g2, arial_40B, new Color(0, 0, 0, 200));
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		setText(g2, arial_40B, new Color(255, 255, 255)); //draw a white rect to make it outline
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height- 10, 25, 25);
		
	}
	
	
	public void draw(Graphics2D g2)
	{
		this.g2 = g2;
		setText(g2, arial_40B, Color.white);
		
		switch(gp.gameState)
		{
		case PLAY:
			break;
		case PAUSE:
			drawPauseScreen();
			break;
		case DIALOGUE:
			drawDialogueScreen();
			break;
		case UNDEFINED:
			break;
		default:
			break;
		
		}
	}
	
	public void drawOLD(Graphics2D g2)
	{
		this.g2 = g2;
		
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

	
	public static void setText(Graphics2D g2, Font font, Color color)
	{
		g2.setFont(font);
		g2.setColor(color);
		
	}
}
