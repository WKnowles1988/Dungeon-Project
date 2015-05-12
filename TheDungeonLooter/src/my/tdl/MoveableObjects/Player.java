package my.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import my.project.gop.main.Vector2F;
import my.tdl.gameloop.GameLoop;
import my.tdl.generator.Block;
import my.tdl.main.Check;
import my.tdl.main.Main;

public class Player implements KeyListener {
	
	Vector2F pos;
	private int width = (int) (Block.BlockSize / 1.14);
	private int height = (int) (Block.BlockSize / 1.14);
	private static boolean up, down, left, right;
	private float maxSpeed = 3*32F;
	private float slowdown = 4.093F;
	private float fixDt = 1f/60F;
	private float speedUp = 0;
	private float speedDown = 0;
	private float speedLeft = 0;
	private float speedRight = 0;
	private boolean mapMove = true;
	
	public Player() {
		pos = new Vector2F(Main.width / 2 - width -2, Main.height /2 - height /2);
	}
	
	public void init() {
	
	}
	
	public void tick(double deltaTime) {
	
		float moveAmountu = (float) (speedUp * fixDt);
		float moveAmountd = (float) (speedDown * fixDt);
		float moveAmountl = (float) (speedLeft * fixDt);
		float moveAmountr = (float) (speedRight * fixDt);
		
		//Player MovemenMovement//
		if(up){
			moveMapUp(moveAmountu);
		}else {	
			moveMapUpGlide(moveAmountu);
		}
		
		if(down){
			moveMapDown(moveAmountd);
		}else {	
			moveMapDownGlide(moveAmountd);
		}
		
		if(left){
			moveMapLeft(moveAmountl);
		}else {		
			moveMapLeftGlide(moveAmountl);
		}
		
		if(right){
			moveMapRight(moveAmountr);
		}else {		
			moveMapRightGlide(moveAmountr);
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
		g.fillRect((int)pos.xpos, (int)pos.ypos +1, width, height);
		
		/*
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.width, Main.height / 6);
		g.fillRect(0, 600, Main.width, Main.height / 3);
		g.setColor(Color.WHITE);
		g.clipRect(0,0,Main.width, Main.height);
		*/
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

}
