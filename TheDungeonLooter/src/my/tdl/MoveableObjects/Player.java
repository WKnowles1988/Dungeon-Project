package my.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import my.project.gop.main.Vector2F;
import my.tdl.gameloop.GameLoop;
import my.tdl.generator.Block;
import my.tdl.main.Animator;
import my.tdl.main.Assets;
import my.tdl.main.Check;
import my.tdl.main.Main;
import my.tdl.managers.GUImanager;
import my.tdl.managers.HUDmanager;

public class Player implements KeyListener {
	
	Vector2F pos;
	private int width = (int) (Block.BlockSize - 16);
	private int height = (int)(Block.BlockSize - 16);
	private int scale = 2;
	private static boolean up, down, left, right;
	private float maxSpeed = 3*88F;
	private float slowdown = 4.093F;
	private float fixDt = 1f/60F;
	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	private boolean idle = false;
	//private boolean mapMove = true;
	
	
	private int renderDistanceh = 26;
	private int renderDistancew = 44;
	public static Rectangle render;
	
	//TODO
	private int animationState = 0;
	
	/*
	 * 0 = up
	 * 1 = down
	 * 2 = left
	 * 3 = right
	 * 4 = idle
	 * 5 = dance
	 * */
	private ArrayList<BufferedImage> listUp;
	Animator ani_up;
	private ArrayList<BufferedImage> listDown;
	Animator ani_down;
	private ArrayList<BufferedImage> listLeft;
	Animator ani_left;
	private ArrayList<BufferedImage> listRight;
	Animator ani_right;
	private ArrayList<BufferedImage> listIdle;
	Animator ani_idle;
	
	private HUDmanager hudm;
	private GUImanager guim;
	
	public Player() {
		guim = new GUImanager();
		hudm = new HUDmanager(this);
		pos = new Vector2F(Main.width / 2 - width -2, Main.height /2 - height /2);
	}
	
	public void init() {
		
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistancew * 32 /2 + width /2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceh * 32 /2 + height /2),
				renderDistancew*32,
				renderDistanceh*32);
		
		listUp = new ArrayList<BufferedImage>();
		listDown = new ArrayList<BufferedImage>();
		listLeft = new ArrayList<BufferedImage>();
		listRight = new ArrayList<BufferedImage>();
		listIdle = new ArrayList<BufferedImage>();
		
		listUp.add(Assets.player.getTile(0,0,16,16));
		listUp.add(Assets.player.getTile(16,0,16,16));
		
		listDown.add(Assets.player.getTile(0,16,16,16));
		listDown.add(Assets.player.getTile(16,16,16,16));
		
		listLeft.add(Assets.player.getTile(32,0,16,16));
		listLeft.add(Assets.player.getTile(48,0,16,16));
		listLeft.add(Assets.player.getTile(64,0,16,16));
		listLeft.add(Assets.player.getTile(80,0,16,16));

		listRight.add(Assets.player.getTile(32,16,16,16));
		listRight.add(Assets.player.getTile(48,16,16,16));
		listRight.add(Assets.player.getTile(64,16,16,16));
		listRight.add(Assets.player.getTile(80,16,16,16));
		
		listIdle.add(Assets.player.getTile(0,32,16,16));
		listIdle.add(Assets.player.getTile(16,32,16,16));
		listIdle.add(Assets.player.getTile(32,32,16,16));
		listIdle.add(Assets.player.getTile(48,32,16,16));
		
		ani_up = new Animator(listUp);
		ani_up.setSpeed(180);
		ani_up.play();
		ani_down = new Animator(listDown);
		ani_down.setSpeed(180);
		ani_down.play();
		ani_left = new Animator(listLeft);
		ani_left.setSpeed(180);
		ani_left.play();
		ani_right = new Animator(listRight);
		ani_right.setSpeed(180);
		ani_right.play();
		ani_idle = new Animator(listIdle);
		ani_idle.setSpeed(1000);
		ani_idle.play();
	}
	
	public void tick(double deltaTime) {
		render = new Rectangle(
				(int) (pos.xpos - pos.getWorldLocation().xpos + pos.xpos - renderDistancew * 32 /2 + width /2),
				(int) (pos.ypos - pos.getWorldLocation().ypos + pos.ypos - renderDistanceh * 32 /2 + height /2),
				renderDistancew*32,
				renderDistanceh*32);
		
		float moveAmountu = (float) (speedUp * fixDt);
		float moveAmountd = (float) (speedDown * fixDt);
		float moveAmountl = (float) (speedLeft * fixDt);
		float moveAmountr = (float) (speedRight * fixDt);
		
		//Player MovemenMovement//
		if(up){
			moveMapUp(moveAmountu);
			animationState = 0;
		}else {	
			moveMapUpGlide(moveAmountu);
		}
		
		if(down){
			moveMapDown(moveAmountd);
			animationState = 1;
		}else {	
			moveMapDownGlide(moveAmountd);
		}
		
		if(left){
			moveMapLeft(moveAmountl);
			animationState = 2;
		}else {		
			moveMapLeftGlide(moveAmountl);
		}
		
		if(right){
			moveMapRight(moveAmountr);
			animationState = 3;
		}else {		
			moveMapRightGlide(moveAmountr);
		}
		
		if(!up && !down && !right && !left){
			animationState = 4;
		}
		
	}

	/*
	public void PlayerMoveCode() {
		if(!mapMove){
			//Actual player moving//
			if(up){
				if(!Check.CollisionPlayerBlock(
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu))  )){
				
					if(speedUp < maxSpeed){
						speedUp += slowdown;
					}else{
						speedUp = maxSpeed;
					}
					pos.ypos-=moveAmountu;
				}else{
					speedUp = 0;
				}
			}else{
				if(!Check.CollisionPlayerBlock(	
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos - moveAmountu))  )){
					
					if(speedUp != 0){
						speedUp -= slowdown;
						
						if(speedUp < 0){
							speedUp = 0;
						}
					}
					pos.ypos-=moveAmountu;
				}else{
					speedUp = 0;
				}
			}
		
			if(down){	
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd))  )){
					
					if(speedDown < maxSpeed){
						speedDown += slowdown;
					}else{
						speedDown = maxSpeed;
					}			
					pos.ypos+=moveAmountd;
				}else{
					speedDown = 0;
				}
			}else {
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + GameLoop.map.xpos) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height + moveAmountd))  )){
					
					if(speedDown != 0) {
						speedDown -= slowdown;
						
						if(speedDown < 0){
							speedDown = 0;
						}
					}			
					pos.ypos+=moveAmountd;
				}else{
					speedDown = 0;
				}
			}
			
			if(left){		
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos -  moveAmountl) , 
								  (int) (pos.ypos + GameLoop.map.ypos))  )){
					
					if(speedLeft < maxSpeed){
						speedLeft += slowdown;
					}else{
						speedLeft = maxSpeed;
					}			
					pos.xpos-=moveAmountl;
				}else{
					speedLeft = 0;
				}
			}else {
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + GameLoop.map.xpos - moveAmountl) ,
								  (int) (pos.ypos + GameLoop.map.ypos + height)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width -  moveAmountl) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
					
					if(speedLeft != 0) {
						speedLeft -= slowdown;
						
						if(speedLeft < 0){
							speedLeft = 0;
						}
					}			
					pos.xpos-=moveAmountl;
				}else{
					speedLeft = 0;
				}
			}
			
			if(right){
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								  (int) (pos.ypos + GameLoop.map.ypos)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width +  moveAmountr) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
					
					if(speedRight < maxSpeed){
						speedRight += slowdown;
					}else{
						speedRight = maxSpeed;
					}			
					pos.xpos+=moveAmountr;
				}else{
					speedRight = 0;
				}
			}else {	
				if(!Check.CollisionPlayerBlock(
						
						new Point((int) (pos.xpos + GameLoop.map.xpos + width + moveAmountr) ,
								  (int) (pos.ypos + GameLoop.map.ypos)),
								  
						new Point((int) (pos.xpos + GameLoop.map.xpos + width +  moveAmountr) , 
								  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
					
					if(speedRight != 0) {
						speedRight -= slowdown;
						
						if(speedRight < 0){
							speedRight = 0;
						}
					}			
					pos.xpos+=moveAmountr;
				}else{
					speedRight = 0;
				}
			}
		}
		// Map Move//
		else{

		}
		
		
		
		
		
	
	}
	*/
	
	public void moveMapUp(float speed) {
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos - speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos - speed))  )){
			if(speedUp < maxSpeed){
				speedUp += slowdown;
			}else{
				speedUp = maxSpeed;
			}
			GameLoop.map.ypos-=speed;
		}else{
			speedUp=0;
		}
	}
	public void moveMapUpGlide(float speed) {
		if(!Check.CollisionPlayerBlock(
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos - speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos - speed))  )){
			
			if(speedUp != 0) {
				speedUp -= slowdown;
				
				if(speedUp < 0){
					speedUp = 0;
				}
			}			
			GameLoop.map.ypos-=speed;
		}else{
			speedUp = 0;
		}
	}
	
	public void moveMapDown(float speed) {
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed))  )){
		
			if(speedDown < maxSpeed){
				speedDown += slowdown;
			}else{
				speedDown = maxSpeed;
			}			
			GameLoop.map.ypos+=speed;
		}else{
			speedDown = 0;
		}
	}
	public void moveMapDownGlide(float speed) {
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height + speed))  )){
			
			if(speedDown != 0) {
				speedDown -= slowdown;
				
				if(speedDown < 0){
					speedDown = 0;
				}
			}			
			GameLoop.map.ypos+=speed;
		}else{
			speedDown = 0;
		}
	}

	public void moveMapLeft(float speed) {
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos -  speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos))  )){
			
			if(speedLeft < maxSpeed){
				speedLeft += slowdown;
			}else{
				speedLeft = maxSpeed;
			}			
			GameLoop.map.xpos-=speed;
		}else{
			speedLeft = 0;
		}
	}	
	public void moveMapLeftGlide(float speed) {
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos - speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos + height)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos -  speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos))  )){
			
			if(speedLeft != 0) {
				speedLeft -= slowdown;
				
				if(speedLeft < 0){
					speedLeft = 0;
				}
			}			
			GameLoop.map.xpos-=speed;
		}else{
			speedLeft = 0;
		}
	}

	public void moveMapRight(float speed) {
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width +  speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
			
			if(speedRight < maxSpeed){
				speedRight += slowdown;
			}else{
				speedRight = maxSpeed;
			}			
			GameLoop.map.xpos+=speed;	
		}else{
			speedRight = 0;
		}
	}	
	public void moveMapRightGlide(float speed) {
		if(!Check.CollisionPlayerBlock(
				
				new Point((int) (pos.xpos + GameLoop.map.xpos + width + speed) ,
						  (int) (pos.ypos + GameLoop.map.ypos)),
						  
				new Point((int) (pos.xpos + GameLoop.map.xpos + width +  speed) , 
						  (int) (pos.ypos + GameLoop.map.ypos + height))  )){
			
			if(speedRight != 0) {
				speedRight -= slowdown;
				
				if(speedRight < 0){
					speedRight = 0;
				}
			}			
			GameLoop.map.xpos+=speed;
		}else{
			speedRight = 0;
		}
	}
	
	public void render(Graphics2D g) {
		g.fillRect((int)pos.xpos, (int)pos.ypos - height / scale, width, height);
		
		hudm.render(g);
		
		
		if(animationState == 0){
			g.drawImage(ani_up.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(up){
				ani_up.update(System.currentTimeMillis());
			}
		}
		if(animationState == 1){
			g.drawImage(ani_down.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(down){
				ani_down.update(System.currentTimeMillis());
			}
		}
		if(animationState == 2){
			g.drawImage(ani_left.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(left){
				ani_left.update(System.currentTimeMillis());
			}
		}
		if(animationState == 3){
			g.drawImage(ani_right.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			if(right){
				ani_right.update(System.currentTimeMillis());
			}
		}
		if(animationState == 4){
			g.drawImage(ani_idle.sprite, (int)pos.xpos - width / 2, (int)pos.ypos - height, width * scale, height * scale, null);
			ani_idle.update(System.currentTimeMillis());
			//System.out.println(ani_idle.isDoneAnimating());
			

			//	ani_idle.stop();

			//	System.out.println("Stopped");
			//	ani_idle.reset();
			
			//	ani_idle.running = true;
			//	g.drawImage(ani_idle.sprite, (int)pos.xpos, (int)pos.ypos, width , height , null);
			//	ani_idle.update(System.currentTimeMillis());
			
			//System.out.println("Running = "+ani_idle.running+":Frame = "+ani_idle.frames.size());
			//ani_idle.frames.get(index) / 2
			
			//ani_idle.update(System.currentTimeMillis());ani_idle.
			
			/*
				if(ani_idle.running){
					ani_idle.running = false;
					ani_idle.stop();

				}else{
					g.drawImage(ani_idle.sprite, (int)pos.xpos, (int)pos.ypos, width , height , null);
					ani_idle.update(System.currentTimeMillis());
				}
			*/
		}
		
		g.drawRect((int)pos.xpos - renderDistancew*32 / 2 + width /2, (int)pos.ypos - renderDistanceh*32 / 2 + height /2, 32 * renderDistancew, 32 * renderDistanceh);
		guim.render(g);
		hudm.render(g);
		
	}

	@Override
	public void keyPressed(KeyEvent i) {
		int key = i.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			up = true;
		}
		if(key == KeyEvent.VK_A){
			left = true;
		}
		if(key == KeyEvent.VK_S){
			down = true;
		}
		if(key == KeyEvent.VK_D){
			right = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent i) {
		int key = i.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			up = false;
		}
		if(key == KeyEvent.VK_A){
			left = false;
		}
		if(key == KeyEvent.VK_S){
			down = false;
		}
		if(key == KeyEvent.VK_D){
			right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent i) {

	}
	
	//////////////////////////////////
	//Getters
	///////////////////////////////////
	
	public Vector2F getpos() {
		return pos;
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}
	
	public float getSlowdown() {
		return slowdown;
	}
	

}
