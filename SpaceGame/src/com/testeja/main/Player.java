package com.testeja.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends GameObject {

	Handler handler;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velX = 0;
		velY = 0;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, 32, 32);
	}
	
	
	
	public void tick(){
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 32);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);
		
		collision();
	}
	
	private void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.BasicEnemy){ // tempobject on nyt perus jäbä
				if(getBounds().intersects(tempObject.getBounds())){
					//Collision code
					HUD.HEALTH -= 5;
					
				}
			}
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.GREEN);
		g.fillRect(x, y, 32, 32);
		
	}
	
	

}
