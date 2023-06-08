package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Chest extends SuperObject{
	
	public OBJ_Chest(GamePanel gp)
	{
		name = "Chest";
		this.gp = gp;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			image = UtilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
