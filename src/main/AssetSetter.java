package main;

import main.GamePanel;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Boots;
import object.OBJ_Chest;

import entity.*
;public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp)
	{
		this.gp = gp;
	}
	
	public void setObject()
	{
		
		/*OBJ_Chest key = new OBJ_Chest(gp); 
		key.worldX = 23 * gp.tileSize;
		key.worldY = 12 * gp.tileSize;
        gp.objects.add(key);
        
        OBJ_Chest chest = new OBJ_Chest(gp); 
		chest.worldX = 23 * gp.tileSize;
		chest.worldY = 12 * gp.tileSize;
        gp.objects.add(chest);
        
        OBJ_Chest chest = new OBJ_Chest(gp); 
		chest.worldX = 23 * gp.tileSize;
		chest.worldY = 12 * gp.tileSize;
        gp.objects.add(chest);*/
        
        OBJ_Chest chest = new OBJ_Chest(gp); 
		chest.worldX = 23 * gp.tileSize;
		chest.worldY = 12 * gp.tileSize;
        gp.objects.add(chest);
        
		gp.obj[0] = new OBJ_Key(gp);
		gp.obj[0].worldX = 23 * gp.tileSize;
		gp.obj[0].worldY = 12 * gp.tileSize;
		
		
		gp.obj[1] = new OBJ_Door(gp);
		gp.obj[1].worldX = 23 * gp.tileSize;
		gp.obj[1].worldY = 10 * gp.tileSize;
		
		
		gp.obj[2] = new OBJ_Door(gp);
		gp.obj[2].worldX = 23 * gp.tileSize;
		gp.obj[2].worldY = 17 * gp.tileSize;
		
		
		gp.obj[3] = new OBJ_Key(gp);
		gp.obj[3].worldX = 25 * gp.tileSize;
		gp.obj[3].worldY = 20 * gp.tileSize;
		
		
		gp.obj[4] = new OBJ_Chest(gp);
		gp.obj[4].worldX = 23 * gp.tileSize;
		gp.obj[4].worldY = 7 * gp.tileSize;
		
		
		gp.obj[5] = new OBJ_Key(gp);
		gp.obj[5].worldX = 26 * gp.tileSize;
		gp.obj[5].worldY = 7 * gp.tileSize;
		
		gp.obj[6] = new OBJ_Boots(gp);
		gp.obj[6].worldX = 22 * gp.tileSize;
		gp.obj[6].worldY = 25 * gp.tileSize;
		
	}
	
	public void setNpc()
	{
		NPC_Oldman oldman = new NPC_Oldman(gp); 
		oldman.worldX = 23 * gp.tileSize;
		oldman.worldY = 12 * gp.tileSize;
        gp.npcs.add(oldman);
	}
}
