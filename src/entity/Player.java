package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.SuperObject;



public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public int boxscreenX;
	public int boxscreenY;
	
	public int keyCount = 0;
	
	public Player(GamePanel gp, KeyHandler keyH)
	{
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = (gp.screenWidth / 2)  - (gp.tileSize / 2); //we subctract half of a tile because these coordinates are indicates the top left corner of the image
		screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);
		
		boxscreenX = screenX;
		boxscreenY = screenY;
		
		solidArea = new Rectangle(); //change from fixed to dynamic
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 18; //32
		solidArea.height = 18;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		
		setDefValues();
		getPlayerImage();
		
		
	}
	
	public void setDefValues()
	{
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize  * 22;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() //hit as low as 500k at draw() after pre scaling player
	{
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
			
	}
	
	public BufferedImage setup(String imageName)
	{
		BufferedImage scaledImage = null;
		try
		{
			scaledImage = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
			scaledImage = UtilityTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return scaledImage;
	}
	
	public void updateCameraOff()
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
			
			int threshold = gp.tileSize * 3; // define a boundary threshold
			
			if(keyH.upPressed == true)
			{
				direction = "up";
			}
			
			else if(keyH.downPressed == true)
			{
				direction = "down";
			}
			
			else if(keyH.leftPressed == true)
			{
				direction = "left";
			}
			
			else if(keyH.rightPressed == true)
			{
				direction = "right";
			}
			
			//check tile colliuson
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//object coll
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			if (collisionOn == false)
			{
				switch (direction)
				{
				case "up":
					
						worldY -= speed;
					
					break;
				case "down":
					
						worldY += speed;
				
					break;
				case "left":
					
						worldX -= speed;
					
					break;
				case "right":
					
						worldX += speed;
					
					break;
				
				}
				
			}
		}
		
		
	}
	
	public void update() //collisonla beraber calismiyor
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
			
			int threshold = gp.tileSize * 3; // define a boundary threshold
			
			if(keyH.upPressed == true)
			{
				direction = "up";
			}
			
			else if(keyH.downPressed == true)
			{
				direction = "down";
			}
			
			else if(keyH.leftPressed == true)
			{
				direction = "left";
			}
			
			else if(keyH.rightPressed == true)
			{
				direction = "right";
			}
			
			//check tile colliuson
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			if (collisionOn == false)
			{
				if (collisionOn == false)
				{
					switch (direction)
					{
					case "up":
						
						if(boxscreenY > threshold) {
							boxscreenY -= speed;
						}
						else
						{
							worldY -= speed;
						}
						break;
					case "down":
						//worldY += speed;
						if(boxscreenY < gp.screenHeight - threshold) {
							boxscreenY += speed;
						}
						else
						{
							worldY += speed;
						}
						break;
					case "left":
						//worldX -= speed;
						if(boxscreenX > threshold) {
							boxscreenX -= speed;
						}
						else
						{
							worldX -= speed;
						}
						break;
					case "right":
						//worldX += speed;
						if(boxscreenX < gp.screenWidth - threshold) {
							boxscreenX += speed;
						}
						else
						{
							worldX += speed;
						}
						break;
					
					}
					
				}
				
			}
				
			}
	}
		

	public void updateOld()
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
			
			boxscreenY -= speed;
		}
		
		else if(keyH.downPressed == true)
		{
			direction = "down";
			//System.out.println(direction);
			worldY += speed; 
			
			boxscreenY += speed;
		}
		
		else if(keyH.leftPressed == true)
		{
			direction = "left";
			//System.out.println(direction);
			worldX -= speed; 
			
			boxscreenX -= speed;
		}
		
		else if(keyH.rightPressed == true)
		{
			direction = "right";
			//System.out.println(direction);
			worldX += speed; 
			
			boxscreenX += speed;
		}
		
		if (boxscreenX - screenX > gp.tileSize * 2)
		    boxscreenX -= speed;       
		else if(boxscreenX - screenX < -gp.tileSize * 2)
		    boxscreenX += speed;

		if (boxscreenY - screenY > gp.tileSize * 2)
		    boxscreenY -= speed;       
		else if(boxscreenY - screenY < -gp.tileSize * 2)
		    boxscreenY += speed;
		
		/*if (worldX > gp.screenWidth)
			worldX = 0;		
		else if(worldX < 0)
			worldX = gp.screenWidth;
		
		if (worldY > gp.screenHeight)
			worldY = 0;
		else if (worldY < 0)
			worldY = gp.screenHeight;*/
		
		
		
	}
	
	public void pickUpObject(int i) {
	    if (i == -1) {
	        return;
	    }

	    SuperObject obj = gp.objects.get(i);

	    switch (obj.name) {
	        case "Key":
	            gp.playSE(1);
	            gp.ui.showMessage("You got a key!");
	            gp.objects.remove(i);
	            keyCount++;
	            break;
	        case "Door":
	            if (keyCount > 0) {
	                gp.playSE(3);
	                keyCount--;
	                gp.objects.remove(i);
	            } else {
	                gp.ui.showMessage("You need a key to open it!");
	            }
	            break;
	        case "Chest":
	            if (keyCount > 0) {
	                gp.playSE(4);
	                keyCount--;
	                gp.objects.remove(i);
	                gp.ui.gameFinished = true;
	                gp.stopMusic();
	            } else {
	                gp.ui.showMessage("You need a key to open it!");
	            }
	            break;
	        case "Boots":
	            if (speed == 4) {
	                gp.playSE(2);
	                speed *= 2;
	                gp.objects.remove(i);
	            }
	            gp.ui.showMessage("Just the boost you need!");
	            break;
	    }
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
		g2.drawImage(image, boxscreenX, boxscreenY, null); //gp.tileSize, gp.tileSize, adding these to the arguments seems to have better draw() 
		//performance even though we pre scaling the images
		

		
	}
	
	
	public void pickUpObjectOG(int i)
	{
		if (i == -1)
			return;
		//gp.obj[i] = null;
		
		switch(gp.obj[i].name)
		{
		case "Key":
			gp.playSE(1);
			gp.ui.showMessage("You got a key!");
			//System.out.println("hit");
			
			gp.obj[i] = null;
			keyCount++;
			
			break;
		case "Door":
			if(keyCount > 0)
			{
				gp.playSE(3);
				keyCount--;
				gp.obj[i] = null;
			}
			else
			{
				gp.ui.showMessage("You need a key to open it!");
			}
			
			break;
		case "Chest":
			if(keyCount > 0)
			{
				gp.playSE(4);
				keyCount--;
				gp.obj[i] = null;
				gp.ui.gameFinished = true;
				gp.stopMusic();
				
			}
			else
			{
				gp.ui.showMessage("You need a key to open it!");
			}
	
			break;
		case "Boots":
			if(speed == 4)
			{
				gp.playSE(2);
				speed *= 2;
				gp.obj[i] = null;
			}
			gp.ui.showMessage("Just the boost you need!");
	
			break;

		
		}
		
		
	}

}
