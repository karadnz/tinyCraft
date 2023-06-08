package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Boots extends SuperObject{
	
	public OBJ_Boots(GamePanel gp)
	{
		name = "Boots";
		collision = true;
		this.gp = gp;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			image = UtilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
