package com.angrymilou.yassir;

import android.graphics.Color;



public class Force {

	private int forceX,forceY,force;
	private boolean pressed;
	private static MyDog dog;
	private static Map map;
	private static int screenWidth=GameScreen.screenWidth;
	private static int screenHeight=GameScreen.screenHeight;
	public static int deltaWidth=GameScreen.deltaWidth;
	public static int deltaHeight=GameScreen.deltaHeight;

	public Force(){
		

		dog=GameScreen.dog;
		map=GameScreen.maMap;
		
		force=0;
		forceX=0;
		forceY=0;
		pressed=false;
	}

	public void update(){

		if(force<=screenWidth/3){
			GameScreen.fbar=Color.rgb(255, 255, 44);
		}else if(screenWidth/3<force && force<=(screenWidth*2)/3){
			GameScreen.fbar=Color.rgb(255,156, 44);
		}else if((screenWidth*2)/3<force && force<=screenWidth){
			GameScreen.fbar=Color.rgb(255,44, 44);
		}
		
		if(pressed==true){
			if(force>screenWidth){
				force=screenWidth;
			}
			force+=deltaWidth*4/3;
		}
			
	}
	
	public void nullify(){
		dog=null;
		map=null;
	}


	public void setForce(float deltaTime){
	
		if(force<=screenWidth/3){
			forceY=(deltaHeight*2)/5;
			forceX=18;
			GameScreen.fbar=Color.rgb(255, 255, 44);
		}else if(screenWidth/3<force && force<=(screenWidth*2)/3){
			forceY=deltaHeight/2;
			forceX=10;
			GameScreen.fbar=Color.rgb(255,176, 44);
		}else if((screenWidth*2)/3<force){
			forceY=(deltaHeight*4)/7;
			forceX=12;
			GameScreen.fbar=Color.rgb(255,106, 44);
		}
		dog.setSpeedY(-forceY/4);
		//dog.setSpeedY((int) (-forceY*5/(6*deltaTime)));
		//dog.setSpeedX((int) (deltaWidth*10000000/15));
		dog.setJump(true);
		force=0;
	}

	private void setSpeedX(int i) {
		if(dog.getCenterX()<400){
			dog.setSpeedX(i);
		}else{
			map.setSpeedX(i);
		}
		
	}

	public int getForceX() {
		return forceX;
	}

	public void setForceX(int forceX) {
		this.forceX = forceX;
	}

	public int getForceY() {
		return forceY;
	}

	public void setForceY(int forceY) {
		this.forceY = forceY;
	}

	public int getForce() {
		return force;
	}

	public void setForce(int force) {
		this.force = force;
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public static MyDog getDog() {
		return dog;
	}

	public static void setDog(MyDog dog) {
		Force.dog = dog;
	}

}
