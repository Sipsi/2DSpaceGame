package com.testeja.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -7757723569303096099L;
	
	public static final int WIDTH = 1200, HEIGHT = WIDTH/ 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	public enum State{
		Menu,
		Help,
		game;
	}
	
	public State gameState = State.Menu;
	
	
	public Game(){
		handler = new Handler();
		menu = new Menu(this, handler);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		
		new Window(WIDTH, HEIGHT, "Eben Space Game", this);	//Creates a window which starts thread
		hud = new HUD();
		spawner = new Spawn(handler, hud);
		r = new Random();
		
		if(gameState == State.game){
			handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player1, handler));
			handler.addObject(new BasicEnemy(r.nextInt(WIDTH),r.nextInt(HEIGHT), ID.BasicEnemy, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
		}	
	}

	public synchronized void start(){
		thread = new Thread(this);
		thread.start();						//calls the run method.
		running = true;
		
		
	}
	public synchronized void stop(){ 		
		try{
			thread.join();
			running = false;
			
		}catch(Exception e){  				// Errorit jos ei toimi
			e.printStackTrace();
			
		}
		
	}
	
	private void tick(){
		handler.tick();
		
		if(gameState == State.game)
		{
			hud.tick();
			spawner.tick();
		}else if (gameState == State.Menu){
			menu.tick();
		}
	}	
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(gameState == State.game)
		{
			hud.render(g);
		}else if(gameState == State.Menu || gameState == State.Help){
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public void run(){  					//Gameloop
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();			//calls tick
				delta --;
			}
			if(running)
				render();		// calls handler
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	
		
	}
	
	public static int clamp(int var, int min, int max){
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
		
	}
	
	public static void main(String args[]){
		
		new Game();
		
	}

}
