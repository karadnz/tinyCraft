package entity;

import main.GamePanel;

public class NPC_Oldman extends Entity{
	
	public NPC_Oldman(GamePanel gp)
	{
		
		super(gp);
		
		entityType = EntityType.NPC_OLDMAN;
		
		direction = "down";
		
		speed = 2;
		
		getOldmanImage();
		setDialogue();
		
	}
	
	public void getOldmanImage() //hit as low as 500k at draw() after pre scaling player
	{
		up1 = setup("/npc/", "oldman_up_1");
		up2 = setup("/npc/", "oldman_up_2");
		down1 = setup("/npc/", "oldman_down_1");
		down2 = setup("/npc/", "oldman_down_2");
		right1 = setup("/npc/", "oldman_right_1");
		right2 = setup("/npc/", "oldman_right_2");
		left1 = setup("/npc/", "oldman_left_1");
		left2 = setup("/npc/", "oldman_left_2");
			
	}
	
	public void setDialogue()
	{
		dialogues[0] = "Hello lad!";
		dialogues[1] = "Hi there!";
		dialogues[2] = "How are ya doin";
		
		dialogues[3] = "I used to be a great wizard \nNow im just and old man...";
		dialogues[4] = "sup";
		dialogues[5] = "sup";
		
		
		dialogues[8] = "";
		
	}
	
	public void speak() //FIX ENTER AND COLLISION
	{
		dialogueIndex = rand.nextInt(4);
		gp.ui.currentDialogue = dialogues[dialogueIndex]; //random greeting //rand.nextInt(3)
		
		switch(gp.player.direction)
		{
		case "up":
			direction = "down";
		case "down":
			direction = "up";
		case "right":
			direction = "left";
		case "left":
			direction = "right";
			
		}
		
	}
	
	//changes direction randomly every 120 calls
	public void setAction()
	{
		actionLockCounter++;
		
		if (actionLockCounter != 120)//2 seconds
			return;
		
		int i = rand.nextInt(4); //1-100;
		
		switch(i)
		{
		case 0:
			direction = "up";
			break;
		case 1:
			direction = "down";
			break;
		case 2:
			direction = "left";
			break;
		case 3:
			direction = "right";
			break;
		}
		
		actionLockCounter = 0;
		
	}

}
