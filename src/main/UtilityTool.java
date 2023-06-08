package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	
	public static BufferedImage scaleImage(BufferedImage original, int width, int height) //pre scaling speeds up draw() about 15-20x
	{
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics(); //creates a graphics2d to draw into this buffered image
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}

}
