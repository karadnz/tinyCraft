package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Door extends SuperObject{
	
	public OBJ_Door(GamePanel gp)
	{
		name = "Door";
		collision = true;
		this.gp = gp;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			image = UtilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
