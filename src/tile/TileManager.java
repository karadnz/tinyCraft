package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	
	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		
		tile = new Tile[50];
		
		getTileImage();
		
	}
	
	public void getTileImage() //
	{

		//old version
		setup(0, "grass00", false);
		setup(1, "wall", false);
		setup(2, "water00", true);
		setup(3, "earth", false);
		setup(4, "tree", true);
		setup(5, "road00", false); //sand
		
		setup(6, "road00", false);
		setup(7, "road00", false);
		setup(8, "road00", false);
		setup(9, "road00", false);
		
		
		setup(10, "grass00", false);
		setup(11, "grass01", false);
		
		setup(12, "water00", true);
		setup(13, "water01", true);
		setup(14, "water02", true);
		setup(15, "water03", true);
		setup(16, "water04", true);
		setup(17, "water05", true);
		setup(18, "water06", true);
		setup(19, "water07", true);
		setup(20, "water08", true);
		setup(21, "water09", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		
		setup(26, "road00", false);
		setup(27, "road01", false);
		setup(28, "road02", false);
		setup(29, "road03", false);
		setup(30, "road04", false);
		setup(31, "road05", false);
		setup(32, "road06", false);
		setup(33, "road07", false);
		setup(34, "road08", false);
		setup(35, "road09", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		
		setup(39, "earth", false);
		setup(40, "wall", false);
		setup(41, "tree", false);
		
	}
	
	public void setup(int index, String imageName, boolean collision)
	{
		try
		{
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = UtilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) 
	{
	    int rows = gp.map.length;
	    int cols = (rows > 0) ? gp.map[0].length : 0;

	    for(int worldRow = 0; worldRow < rows; worldRow++)
	    {
	        for(int worldCol = 0; worldCol < cols; worldCol++)
	        {
	            int tileNum = gp.map[worldRow][worldCol];

	            int worldX = worldCol * gp.tileSize;
	            int worldY = worldRow * gp.tileSize;

	            int screenX = worldX - gp.player.worldX + gp.player.screenX;
	            int screenY = worldY - gp.player.worldY + gp.player.screenY;

	            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
	                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
	                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
	                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) //adding a tile size to draw a bit more than screen
	            {
	                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
	            }

	            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	        }
	    }
	}


}
