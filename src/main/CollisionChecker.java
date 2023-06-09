package main;

import java.util.List;

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
	
	
	public int checkEntity(Entity entity, List<Entity> target)
	{
		int index = -1;

	    int i = 0;
	    for (Entity obj : target) {
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
	            if (obj.collisionOn)
	            	index = i;
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
	
	public void checkPlayer(Entity entity)
	{
	
	        // get entity solid area position
	        int entityOldX = entity.solidArea.x;
	        int entityOldY = entity.solidArea.y;
	        entity.solidArea.x += entity.worldX;
	        entity.solidArea.y += entity.worldY;

	        // get object solid area position
	        int objOldX = gp.player.solidArea.x;
	        int objOldY = gp.player.solidArea.y;
	        gp.player.solidArea.x += gp.player.worldX;
	        gp.player.solidArea.y += gp.player.worldY;

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

	        if (entity.solidArea.intersects(gp.player.solidArea))
	            entity.collisionOn = true;


	        // reset the solid areas
	        entity.solidArea.x = entityOldX;
	        entity.solidArea.y = entityOldY;
	        gp.player.solidArea.x = objOldX;
	        gp.player.solidArea.y = objOldY;

		
	}

}
