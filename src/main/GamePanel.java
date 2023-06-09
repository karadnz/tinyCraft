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

import entity.*;
import tile.*;
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
	
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter= new AssetSetter(this);
	public UI ui = new UI(this);
	
	Thread gameThread; //implemets Runnable
	
	
	//ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public List<SuperObject> objects = new ArrayList<>();
	public List<Entity> npcs = new ArrayList<>();
	
	public SuperObject obj[]= new SuperObject[10]; //REMOVE
	
	
	//MAP (MOVE TO A CLASS)
	public int[][] map;
	private OpenSimplexNoise noise;
	
	public int maxWorldCol = 305; //50 50 //final int
	public int maxWorldRow = 305;
	
	public final int maxWorldWidth = tileSize * maxWorldCol;
	public final int maxWorldHeight = tileSize * maxWorldRow;
	
	
	//MISC
	
	public long startTime;
	Random rand = new Random();
	
	
	//GAME STATE
	//public int gameState;
	public final int playState = 1;
	public final int pauseStat = 2;
	
	
	enum GameState {
		  PLAY,
		  PAUSE,
		  UNDEFINED
		}
	
	public GameState gameState = GameState.PLAY;
	
	
	
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
		 this.noise = new OpenSimplexNoise(rand.nextLong());
		 //generateMap();
		 loadMap();
		 
		 startTime = System.nanoTime();
		
		
		//javax.swing.SwingUtilities.invokeLater(gameThread);
		
		this.requestFocusInWindow();
		this.requestFocus();
		//player.getPlayerImage();
	}
	
	public void setupGame()
	{
		aSetter.setObject();
		aSetter.setNpc();
		
		//playMusic(0);
		gameState = GameState.PLAY; //throws exception when i remove the initilazation on the top
	}
	
	public void loadMap() //add setupobj and setupnpc
	{	
		try
		{
	        InputStream is = getClass().getResourceAsStream("/maps/worldV2.txt");
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        
	        List<List<Integer>> mapList = new ArrayList<>();
	        String line;
	        
	        while ((line = br.readLine()) != null)
	        {
	        	String[] nums = line.trim().split(" ");
	        	
	        	List<Integer> row = new ArrayList<>();
	        	for(String numStr : nums){
	        		int num = Integer.parseInt(numStr);
	        		row.add(num);
	        	}
	        	
	        	mapList.add(row);
	        }
	        br.close();

	        // Now convert the list of lists to a 2D array
	        int rows = mapList.size();
	        int cols = mapList.get(0).size(); // Assuming all rows have the same length
	        
	        maxWorldRow = rows;
	        maxWorldCol = cols;

	        map = new int[rows][cols];
	        
	        for(int i = 0; i < rows; i++){
	        	for(int j = 0; j < cols; j++){
	        		map[i][j] = mapList.get(i).get(j);
	        	}
	        }
		}
		catch(Exception e)
		{
			// Handle the exception here
			e.printStackTrace();
		}
	}
	
	
	public void tryGenerateObject(String obj, int num, int x, int y) //change string to enum
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
	
	public void tryGenerateNpc(Entity.EntityType entityType, int num, int x, int y)
	{
		if (rand.nextInt(num) != 0)
			return ;
		switch(entityType)
		{
			case NPC_OLDMAN :
				NPC_Oldman entity = new NPC_Oldman(this); 
				entity.worldX = x * tileSize;
				entity.worldY = y * tileSize;
                npcs.add(entity);
				break;
		default:
			break;
				
			
		}
		
	}
	
	//FIX X Y NAMING ISSUE
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
	                
	                tryGenerateObject("Chest", 300, y, x);
	                tryGenerateNpc(Entity.EntityType.NPC_OLDMAN, 100, y, x);
	            
	            }
	            else if (value < 0.3)
	            {
	                map[x][y] = 0;  // grass
	                
	                tryGenerateObject("Key", 100, y, x);
	                tryGenerateObject("Door", 300, y, x);

	            }
	            else if (value < 0.5)
	            {
	                map[x][y] = 3;  // earth
	                
	                tryGenerateObject("Boots", 150, y, x);
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
				//System.out.println("FPS:" + drawCount);
				drawCount = 0;
				fpsTimer = System.nanoTime();
				
				
			}
				
			}
		}
		
		
	}
	
	public void update()
	{
		
		switch(gameState)
		{
		case PLAY:
			player.updateCameraOff();
			//player.update(); //collison problem
			for (Entity entity : npcs)
			{
				entity.update();
			}
			break;
		case PAUSE:
			
			break;
		case UNDEFINED:
			
			break;
		default:
			break;
		
		}
	
		
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
		
		//npc
		for (Entity entity : npcs)
	    {
	        entity.draw(g2, this);
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
