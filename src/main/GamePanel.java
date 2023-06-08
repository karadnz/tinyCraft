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
import object.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

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
	
	Sound music = new Sound();
	Sound se = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public AssetSetter aSetter= new AssetSetter(this);
	
	public UI ui = new UI(this);
	
	public SuperObject obj[]= new SuperObject[10]; //newlwei tasi
	public List<SuperObject> objects = new ArrayList<>();
	
	public int[][] map;
	private OpenSimplexNoise noise;
	
	
	//WORLD SETTINGS
	
	public final int maxWorldCol = 160; //50 50
	public final int maxWorldRow = 120;
	
	public final int maxWorldWidth = tileSize * maxWorldCol;
	public final int maxWorldHeight = tileSize * maxWorldRow;
	
	public long startTime;
	
	
	Random rand = new Random();
	
	
	
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
		 
		 startTime = System.nanoTime();
		
		
		//javax.swing.SwingUtilities.invokeLater(gameThread);
		
		this.requestFocusInWindow();
		this.requestFocus();
		//player.getPlayerImage();
	}
	
	public void setupGame()
	{
		aSetter.setObject();
		
		playMusic(0);
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
	        	
	        	//System.out.println(line);
	        	
	        	while (col < maxWorldCol)
	        	{
	        		
	        		String nums[] = line.split(" ");
	        		
	        		int num = Integer.parseInt(nums[col]);
	        		//System.out.print(num + " ");
	        		
	        		
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
	
	public void tryGenerateObject(String obj, int num, int x, int y)
	{
		if (rand.nextInt(num) != 0)
			return ;
		switch(obj)
		{
			case "Chest":
				OBJ_Chest chest = new OBJ_Chest(this); 
				chest.worldX = x * tileSize;
				chest.worldY = y * tileSize;
                objects.add(chest);
				break;
				
			case "Key":
				OBJ_Key key = new OBJ_Key(this); 
                key.worldX = x * tileSize;
                key.worldY = y * tileSize;
                objects.add(key);
				break;
			
			case "Door":
				OBJ_Door door = new OBJ_Door(this); 
				door.worldX = x * tileSize;
				door.worldY = y * tileSize;
                objects.add(door);
				break;
				
			case "Boots":
				OBJ_Boots boots = new OBJ_Boots(this); 
				boots.worldX = x * tileSize;
				boots.worldY = y * tileSize;
                objects.add(boots);
				break;
		}
		
	}
	
	public void generateMap() {
	    double scale = 0.1; // smaller values create smoother transitions
	    for(int x = 0; x < map.length; x++)
	    {
	        for(int y = 0; y < map[0].length; y++)
	        {
	            double value = noise.eval(x * scale, y * scale, 0);
	            if (value < -0.4)
	            {
	                map[x][y] = 2;  // water
	            }
	            else if (value < 0)
	            {
	                map[x][y] = 5;  // sand
	                
	                tryGenerateObject("Chest", 300, x, y);
	            
	            }
	            else if (value < 0.3)
	            {
	                map[x][y] = 0;  // grass
	                
	                tryGenerateObject("Key", 100, x, y);
	                tryGenerateObject("Door", 300, x, y);

	            }
	            else if (value < 0.5)
	            {
	                map[x][y] = 3;  // earth
	                
	                tryGenerateObject("Boots", 150, x, y);
	            }
	            else if (value < 0.7)
	            {
	                map[x][y] = 4;  // trees
	         
	            } else
	            {
	                map[x][y] = 1;  // wall
	    
	            }
	        }
	    }
	}
	
	public void generateMapOG() {
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
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); //JPanel class
		
		Graphics2D g2 = (Graphics2D)g; //g2 class extend g class to provide more control over geometry, coordinate transforms. color management and tex layout...
		
		//DEBUG
		long drawStart = 0;
		if (keyH.isDebug)
		{
			drawStart = System.nanoTime();
		}
		
		
		
		//TILE
		tileM.draw(g2);
		
		//OBJ
		for (SuperObject object : objects)
	    {
	        object.draw(g2, this);
	    }
		
		//PLAYER
		player.draw(g2);
		
		//UI
		ui.draw(g2);
		
		
		
		long passed =  0 ;
		if (keyH.isDebug)
		{
			passed = System.nanoTime() - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed); //my average is 16,982,583 in vid 600,000 - 900,000 it depends computer to computer
		}
		
		g2.dispose(); //dispose of this graphics context and release any system resources that it is using // to save memory
	}
	
	public void playMusic(int i)
	{
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic()
	{
		music.stop();
	}
	
	public void playSE(int i) //sound effect
	{
		se.setFile(i);
		se.play();
	}
	
}
