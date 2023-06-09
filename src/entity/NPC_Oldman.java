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
