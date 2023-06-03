package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;



public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH)
	{
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = (gp.screenWidth / 2)  - (gp.tileSize / 2); //we subctract half of a tile because these coordinates are indicates the top left corner of the image
		screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);
		setDefValues();
		getPlayerImage();
		
		
	}
	
	public void setDefValues()
	{
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize  * 21;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage()
	{
		System.out.println("HIT GETPLAYERIMAGE");
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error loading player images.");
			e.printStackTrace();
		}
		
		
	
		
	}
	
	public void update()
	{
		//System.out.println("HIT");
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
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
			
		}
		if(keyH.upPressed == true)
		{
			direction = "up";
			//System.out.println(direction);
			worldY -= speed; //upper left is 0 0
		}
		
		else if(keyH.downPressed == true)
		{
			direction = "down";
			//System.out.println(direction);
			worldY += speed; 
		}
		
		else if(keyH.leftPressed == true)
		{
			direction = "left";
			//System.out.println(direction);
			worldX -= speed; 
		}
		
		else if(keyH.rightPressed == true)
		{
			direction = "right";
			//System.out.println(direction);
			worldX += speed; 
		}
		
		/*if (worldX > gp.screenWidth)
			worldX = 0;		
		else if(worldX < 0)
			worldX = gp.screenWidth;
		
		if (worldY > gp.screenHeight)
			worldY = 0;
		else if (worldY < 0)
			worldY = gp.screenHeight;*/
		
		
		
	}
	
	public void draw(Graphics2D g2)
	{
		
		
		BufferedImage image = null;
		
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
		//System.out.println(direction);
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		

		
	}

}
