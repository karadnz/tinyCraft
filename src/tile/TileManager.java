package tile;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	
	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		
		tile = new Tile[10];
		
		getTileImage();
		
	}
	
	public void getTileImage()
	{
		System.out.println("HIT GETTILEIMAGE");
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error loading player images.");
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2) //bu videoyu yeniden izle
	{
		/*for(int x = 0; x < gp.screenWidth; x += gp.tileSize)
		{
			for(int y = 0; y < gp.screenHeight; y += gp.tileSize)
			{
				//g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
				//g2.drawImage(tile[gp.map[x][y]].image, x , y , gp.tileSize, gp.tileSize, null);
				System.out.println(gp.map[x][y]);
			}
		}*/
		
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
		{
			int tileNum = gp.map[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX + gp.player. screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) //adding a tile size to draw a bit more than screen
			{
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			
			g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			worldCol++;
			
			if (worldCol == gp.maxWorldCol)
			{
				worldCol = 0;
				worldRow++;
			}
		}
		/*
		for(int x = 0; x < gp.map.length; x++)
		{
            for(int y = 0; y < gp.map[0].length; y++)
            {
                g2.drawImage(tile[gp.map[x][y]].image, x * gp.tileSize, y * gp.tileSize, gp.tileSize, gp.tileSize, null);
            }
		}
		
	
		
		for(int x = 0; x < gp.map.length; x++)
		{
            for(int y = 0; y < gp.map[0].length; y++)
            {
                g2.drawImage(tile[gp.map[x][y]].image, x * gp.tileSize, y * gp.tileSize, gp.tileSize, gp.tileSize, null);
            }
		}*/
		
	}
	
	

}
