
package com.angrymilou.yassir;


import java.util.ArrayList;
import android.graphics.Rect;


public class MyDog {

	private int centerX,centerY,speedX,speedY;
	private boolean jump,up,collide,over,complete,explose;
	
	

	private Rect head,hand,feet,body;
	public static ArrayList<Map> tilearray;
	public static int deltaWidth=GameScreen.deltaWidth;
	public static int deltaHeight=GameScreen.deltaHeight;
	public static boolean demo;

	
	public MyDog(){
		
	
		tilearray=GameScreen.tilearray;
		
		centerX=-200;
		centerY=deltaHeight*5;
		speedX=0;
		speedY=0;
		jump=false;
		collide=false;
		over=false;
		complete=false;
		
		head=new Rect(0,0,0,0);
		hand=new Rect(0,0,0,0);
		feet=new Rect(0,0,0,0);
		body=new Rect(0,0,0,0);
		
	}

	
	public void update(float deltaTime){
		
		moveRight(deltaTime);
		if(jump==true){
			//centerX+=speedX/2;
			//moveRight(deltaTime);
			//centerY+=speedY*deltaTime*2;
			centerY+=speedY/*deltaTime*2*/*13/2;
			//speedY+=deltaTime*deltaHeight*2/361;
			//speedY+=deltaTime*deltaTime*deltaHeight/600;
			speedY+=deltaHeight/40;
				if(centerY>=deltaHeight*5){
					speedY=0;
					centerY=deltaHeight*5;
					jump=false;
				}
		}else{
			//moveRight(deltaTime);
			
		}
		if(centerX<150 || isComplete())centerX+=deltaTime*speedX/2;
		if(speedY<0){
			up=true;
		}else{
			up=false;
		}
		
		int headX=centerX+(deltaWidth*4/3);
		int headY=centerY;
		int headW=deltaWidth*2/3;
		int headH=deltaHeight*2/3;
		
		head.set(headX,headY,headX+headW,headY+headH);
		
		int bodyX=centerX+deltaHeight/2;
		int bodyY=centerY+deltaHeight/3;
		int bodyW=deltaWidth;
		int bodyH=deltaHeight/2;
		
		body=new Rect(bodyX,bodyY,bodyX+bodyW,bodyY+bodyH);

		for(int i=0;i<tilearray.size();i++){
			if(tilearray.get(i).getType()==3 ||tilearray.get(i).getType()==4){ 
				
				if(Rect.intersects( tilearray.get(i).getR(),head) || Rect.intersects(body, tilearray.get(i).getR())){
					collide=true;
					break;
				}
			}else if(tilearray.get(i).getType()==5){
				if(Rect.intersects( tilearray.get(i).getR(),head) || Rect.intersects(body, tilearray.get(i).getR())){
					explose=true;
					tilearray.get(i).setType(7);
						if(GameScreen.volume){
						Assets.mineS.play(0.5f);
						}
					break;
				}
			}
			else if(tilearray.get(i).getType()==0){
				if(tilearray.get(i).getR().left<centerX){
				complete=true;
				}
			}else if(tilearray.get(i).getType()==6){
				if(Rect.intersects( tilearray.get(i).getR(),head) || Rect.intersects(body, tilearray.get(i).getR())){
					tilearray.remove(i);

						GameScreen.score+=10;
						if(GameScreen.volume){
						Assets.point.play(0.5f);
						}
					
				}
				
			}
		}
			
			}
	
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public void tomber(float deltaTime){
		if(centerY<deltaHeight*5){
		for(int i=0;i<tilearray.size();i++){
			tilearray.get(i).update(deltaTime);
		}
		setSpeedY(20);
		update(deltaTime);
		}else{
			over=true;
		}
	}
	
	public void nullify(){
		head=null;
		hand=null;
		feet=null;
		body=null;
		tilearray=null;
	}
	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}

	public boolean isCollide() {
		return collide;
	}

	public void setCollide(boolean collide) {
		this.collide = collide;
	}

	public Rect getHead() {
		return head;
	}

	public void setHead(Rect head) {
		this.head = head;
	}

	public Rect getHand() {
		return hand;
	}

	public void setHand(Rect hand) {
		this.hand = hand;
	}

	public Rect getFeet() {
		return feet;
	}

	public void setFeet(Rect feet) {
		this.feet = feet;
	}

	public Rect getBody() {
		return body;
	}

	public void setBody(Rect body) {
		this.body = body;
	}

	public static ArrayList<Map> getTilearray() {
		return tilearray;
	}

	public static void setTilearray(ArrayList<Map> tilearray) {
		MyDog.tilearray = tilearray;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void jump(int force){
		speedY+=force;
	}
	public void moveRight(float deltaTime){
		
		setSpeedX(deltaWidth/3);
		//setSpeedX((int)deltaWidth/11);
		//setSpeedX(g.getWidth()*6/21);
		//setSpeedX(deltaWidth*6/21);
	}
	/*
	public void jump(){
		jump=true;
		speedY=-15;
		
	}*/
	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	public boolean isExplose() {
		return explose;
	}

	public void setExplose(boolean explose) {
		this.explose = explose;
	}
}
