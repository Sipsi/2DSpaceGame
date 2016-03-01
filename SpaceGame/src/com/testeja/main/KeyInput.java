package com.testeja.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private Handler  handler;
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Player1){
				if(key == KeyEvent.VK_W) tempObject.setvelY(-5);
				if(key == KeyEvent.VK_S) tempObject.setvelY(5);
				if(key == KeyEvent.VK_D) tempObject.setvelX(5);
				if(key == KeyEvent.VK_A) tempObject.setvelX(-5);
				
			}
		}
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() == ID.Player1){
				if(key == KeyEvent.VK_W) tempObject.setvelY(0);
				if(key == KeyEvent.VK_S) tempObject.setvelY(0);
				if(key == KeyEvent.VK_D) tempObject.setvelX(0);
				if(key == KeyEvent.VK_A) tempObject.setvelX(0);
				
			}
		}
	}
}