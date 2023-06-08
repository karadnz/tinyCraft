package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class OBJ_Key extends SuperObject{
	
	public OBJ_Key(GamePanel gp)
	{
		name = "Key";
		this.gp = gp;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
			image = UtilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
