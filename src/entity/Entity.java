package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.util.Random ;

public class Entity {
	
	
	GamePanel gp;
	
	public enum EntityType {
		  PLAYER,
		  NPC_OLDMAN,
		  UNDEFINED
		}
	EntityType entityType;
	
	Random rand = new Random();
	
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	
	
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea = new Rectangle(0, 0, 40, 40);
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	public boolean collisionOn = false;
	
	public int actionLockCounter = 0;
	
	public Entity(GamePanel gp)
	{
		this.gp = gp;
		
	
	}
	
	public void setAction() {}
	public void speak() {}
	public void update()
	{
	    spriteCounter++;
	    if (spriteCounter > 10)
	    {
	        if (spriteNum == 1)
	            spriteNum = 2;
	        else
	            spriteNum =1;
	        spriteCounter = 0;
	    }
	    
	    setAction();
	    
	    collisionOn = false;
	    gp.cChecker.checkTile(this);
	    gp.cChecker.checkObject(this, collisionOn); //you can interact with object using return value
	    gp.cChecker.checkPlayer(this);
	    
	    int threshold = gp.tileSize * 2;
	    
	    if (collisionOn == false)
	    {
	        switch (direction)
	        {
	            case "up":
	                if (worldY - speed >= threshold) {
	                    worldY -= speed;
	                }
	                break;
	            case "down":
	                if (worldY + speed <= gp.maxWorldHeight - threshold) {
	                    worldY += speed;
	                }
	                break;
	            case "left":
	                if (worldX - speed >= threshold) {
	                    worldX -= speed;
	                }
	                break;
	            case "right":
	                if (worldX + speed <= gp.maxWorldWidth - threshold) {
	                    worldX += speed;
	                }
	                break;
	        }
	    }
	}
	
	
	public BufferedImage setup(String path, String imageName)
	{
		BufferedImage scaledImage = null;
		try
		{
			scaledImage = ImageIO.read(getClass().getResourceAsStream(path + imageName + ".png"));
			scaledImage = UtilityTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return scaledImage;
	}
	
	public void draw(Graphics2D g2, GamePanel gp)
	{
		BufferedImage image = null;
		
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player. screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) //adding a tile size to draw a bit more than screen
		{
			
			switch (direction)
			{
			case "up":
				image = up1;
				if (spriteNum == 2)
					image = up2;
				break;
			case "down":
				image = down1;
				if (spriteNum == 2)
					image = down2;
				break;
			case "left":
				image = left1;
				if (spriteNum == 2)
					image = left2;
				break;
			case "right":
				image = right1;
				if (spriteNum == 2)
					image = right2;
				break;
			
			}
			
			g2.drawImage(image, screenX, screenY, null);
		}
		
	}

}
