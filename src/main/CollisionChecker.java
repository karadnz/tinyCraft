package main;

import entity.Entity;
import main.GamePanel;
import object.SuperObject;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void checkTile(Entity entity)
	{
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		
		int tileNum1, tileNum2;
		
		switch (entity.direction)
		{
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityTopRow][entityLeftCol];
			tileNum2 = gp.map[entityTopRow][entityRightCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityBottomRow][entityLeftCol];
			tileNum2 = gp.map[entityBottomRow][entityRightCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityTopRow][entityLeftCol];
			tileNum2 = gp.map[entityBottomRow][entityLeftCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}

			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityTopRow][entityRightCol];
			tileNum2 = gp.map[entityBottomRow][entityRightCol];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}

			break;
		
		}
		
	}
	
	public void checkTile2(Entity entity)
	{
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		
		
		int tileNum1, tileNum2;
		
		switch (entity.direction)
		{
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityLeftCol][entityTopRow];
			tileNum2 = gp.map[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityLeftCol][entityBottomRow];
			tileNum2 = gp.map[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityLeftCol][entityTopRow];
			tileNum2 = gp.map[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}

			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.map[entityRightCol][entityTopRow];
			tileNum2 = gp.map[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true)
			{
				entity.collisionOn = true;
			}

			break;
		
		}
		
	}
	
	public int checkObject(Entity entity, boolean player) {
	    int index = -1;

	    int i = 0;
	    for (SuperObject obj : gp.objects) {
	        // get entity solid area position
	        int entityOldX = entity.solidArea.x;
	        int entityOldY = entity.solidArea.y;
	        entity.solidArea.x += entity.worldX;
	        entity.solidArea.y += entity.worldY;

	        // get object solid area position
	        int objOldX = obj.solidArea.x;
	        int objOldY = obj.solidArea.y;
	        obj.solidArea.x += obj.worldX;
	        obj.solidArea.y += obj.worldY;

	        switch (entity.direction) {
	            case "up":
	                entity.solidArea.y -= entity.speed;
	                break;
	            case "down":
	                entity.solidArea.y += entity.speed;
	                break;
	            case "left":
	                entity.solidArea.x -= entity.speed;
	                break;
	            case "right":
	                entity.solidArea.x += entity.speed;
	                break;
	        }

	        if (entity.solidArea.intersects(obj.solidArea)) {
	            if (obj.collision) {
	                entity.collisionOn = true;
	            }
	            if (player) {
	                index = i;
	            }
	        }

	        // reset the solid areas
	        entity.solidArea.x = entityOldX;
	        entity.solidArea.y = entityOldY;
	        obj.solidArea.x = objOldX;
	        obj.solidArea.y = objOldY;

	        i++;
	    }

	    return index;
	}
	
	public int checkObjectOG(Entity entity, boolean player)//HORRIBLE IMPLEMENTETION FIX IT ASAP
	{
		int index = -1; //retunr the index of object
		
		for(int i = 0; i < gp.obj.length; i++) //find a beter way than checking every object
		{
			if (gp.obj[i] != null)
			{
				//get entity solid are pos
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//get obj solid area pos
				
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x; //0 at default
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(entity.direction)
				{
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
						
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
					break;
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX; //FIX THIS ABOMINATION
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				
			}
		}
		
		
		return index;
	}
	
	public int checkObjectOG2(Entity entity, boolean player)//HORRIBLE IMPLEMENTETION FIX IT ASAP
	{
		int index = -1; //retunr the index of object
		
		for(int i = 0; i < gp.obj.length; i++) //find a beter way than checking every object
		{
			if (gp.obj[i] != null)
			{
				//get entity solid are pos
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				//get obj solid area pos
				
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x; //0 at default
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(entity.direction)
				{
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
						
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea))
					{
						if (gp.obj[i].collision == true)
							entity.collisionOn = true;
						if (player == true)
						{
							index = i;
						}
					}
					break;
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX; //FIX THIS ABOMINATION
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				
			}
		}
		
		
		return index;
	}

}
