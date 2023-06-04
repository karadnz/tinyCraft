package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;
import object.SuperObject;

import java.util.Random;

public class GamePanel extends JPanel implements Runnable{
	
	//SCREEN SETTINGS
	
	final int originalTileSize = 16; //16x16 tiles
	final int scale = 3; //to scale
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixel
	public final int screenHeight = tileSize * maxScreenRow; //576 pixel
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //implemets Runnable
	
	//set player def positons
	public Player player;
	
	TileManager tileM;
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public AssetSetter aSetter= new AssetSetter(this);
	
	public SuperObject obj[]= new SuperObject[10]; //newlwei tasi
	
	public int[][] map;
	private OpenSimplexNoise noise;
	
	
	//WORLD SETTINGS
	
	public final int maxWorldCol = 160;
	public final int maxWorldRow = 120;
	
	public final int maxWorldWidth = tileSize * maxWorldCol;
	public final int maxWorldHeight = tileSize * maxWorldRow;
	
	
	
	int FPS = 60;
	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //if set to true all the drawing from this component will be done in an offscreen painting buffer //improves render performance
		this.addKeyListener(keyH);
		this.setFocusable(true); //so that gamepanel can be focused to receive key input
		
		
		player = new Player(this, keyH);
		tileM  = new TileManager(this);
		
		
		 //int mapWidth = (screenWidth / tileSize) * 20;
		 //int mapHeight = (screenHeight / tileSize) * 20;
		 this.map = new int[maxWorldCol][maxWorldRow];
		 Random rand = new Random();
		 this.noise = new OpenSimplexNoise(rand.nextLong());
		 generateMap();
		 //loadMap();
		
		
		//javax.swing.SwingUtilities.invokeLater(gameThread);
		
		this.requestFocusInWindow();
		this.requestFocus();
		//player.getPlayerImage();
	}
	
	public void setupGame()
	{
		aSetter.setObject();
	}
	
	public void loadMap()//ya burda ya da tilemanagaer.draw da sikinit var
	{
		
		try
		{
			
	        InputStream is = getClass().getResourceAsStream("/maps/world01.txt");
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        
	        int col = 0;
	        int row = 0;
	        
	        while (col < maxWorldCol && row < maxWorldRow)
	        {
	        	String line = br.readLine();
	        	
	        	while (col < maxWorldCol)
	        	{
	        		String nums[] = line.split(" ");
	        		
	        		int num = Integer.parseInt(nums[col]);
	        		
	        		map[col][row] = num;
	        		col++;
	        		
	        	}
	        	
	        	if (col == maxWorldCol)
	        	{
	        		col = 0;
	        		row++;
	        	}
	        	
	        }
	        br.close();
	        
		}
		catch(Exception e)
		{
			
		}
		
	        
	 
	    
	}
	
	public void generateMap() {
	    double scale = 0.1; // smaller values create smoother transitions
	    for(int x = 0; x < map.length; x++) {
	        for(int y = 0; y < map[0].length; y++) {
	            double value = noise.eval(x * scale, y * scale, 0);
	            if (value < -0.4) {
	                map[x][y] = 2;  // water
	            } else if (value < 0) {
	                map[x][y] = 5;  // sand
	            } else if (value < 0.3) {
	                map[x][y] = 0;  // grass
	            } else if (value < 0.5) {
	                map[x][y] = 3;  // earth
	            } else if (value < 0.7) {
	                map[x][y] = 4;  // trees
	            } else {
	                map[x][y] = 1;  // wall
	            }
	        }
	    }
	}
	
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start(); //automaticly calls run() method
	}

	@Override
	public void run() //func of interface Runnable
	{
		long currentTime = System.nanoTime();
		double drawInterval = 1000000000 / FPS; //0.016 seconds
		
		long fpsTimer = currentTime;
		int drawCount = 0;
		
		while(gameThread != null)
		{
			if (System.nanoTime() - currentTime > drawInterval)
			{
			//System.out.println("Game loop is running");
			currentTime = System.nanoTime();
			//UPDATE update info such as chracter position
			update();
			
			//DRAW  draw the screen with updated info
			repaint(); //to call paintCompnanet
			
			drawCount++;
			
			if (System.nanoTime() - fpsTimer > 1000000000)
			{
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				fpsTimer = System.nanoTime();
				
				
			}
			
			
			}
		}
		
		
	}
	
	public void update()
	{
		player.updateCameraOff();
		//player.update(); //collison problem
		
		//player.y += player.speed;
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); //JPanel class
		
		Graphics2D g2 = (Graphics2D)g; //g2 class extend g class to provide more control over geometry, coordinate transforms. color management and tex layout...
		
		tileM.draw(g2);
		
		for (int i = 0; i < obj.length; i++)
		{
			if (obj[i] != null)
			{
				obj[i].draw(g2, this);
			}
		}
		player.draw(g2);
		
		g2.dispose(); //dispose of this graphics context and release any system resources that it is using // to save memory
	}
	
}
