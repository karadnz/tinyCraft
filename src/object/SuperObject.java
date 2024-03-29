package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

import java.awt.Rectangle;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	
	public Rectangle solidArea = new Rectangle(0,0,48,48); //whole tile are is solid in deault
	public int solidAreaDefaultX =0;
	public int solidAreaDefaultY = 0;
	
	GamePanel gp;
	
	public void draw(Graphics2D g2, GamePanel gp)
	{
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player. screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) //adding a tile size to draw a bit more than screen
		{
			g2.drawImage(image, screenX, screenY, null);
		}
		
	}

}
