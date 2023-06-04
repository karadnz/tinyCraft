package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
	
	public OBJ_Boots()
	{
		name = "Boots";
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}