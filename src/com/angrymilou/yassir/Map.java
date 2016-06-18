package com.angrymilou.yassir;


import yassir.framework.Image;
import yassir.framework.Screen;
import yassir.framework.implementation.AndroidGame;
import android.graphics.Rect;

public class Map {

	private int mapX,mapY,speedX,type,width,height,delta=0;
	private Image asset;
	private Rect r;
	private static int deltaWidth=GameScreen.deltaWidth;
	private static int deltaHeight=GameScreen.deltaHeight;
	public static boolean demo;
	
	public Map(int x,int y,int t){

		demo=Screen.demo;
		type=t;
		if(type==1){
			//asset=GameScreen.tile1_1;
			width=deltaWidth;
			height=deltaHeight;
		}else if(type==2){
			//asset=GameScreen.tile1_2;
			width=deltaWidth;
			height=deltaHeight;
		}else if(type==3){
			//asset=GameScreen.ammo_box;
			width=deltaWidth;
			height=deltaHeight;
		}else if(type==4){
			//asset=Assets.barrelBlack;
			width=deltaWidth;
			height=deltaHeight*3/2;
			delta=2*deltaHeight-height;
		}else if(type==5){
			width=deltaWidth;
			height=deltaHeight/3;
			delta=deltaHeight-height;
		}else if(type==6){
			width=deltaWidth;
			height=deltaHeight;
			delta=deltaHeight-height;
		}
		mapX=x;
		mapY=y+delta;
		r=new Rect();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMapX() {
		return mapX;
	}

	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Image getAsset() {
		return asset;
	}

	public void setAsset(Image asset) {
		this.asset = asset;
	}
	
	public void update(float deltaTime){

		if(GameScreen.getDog().getCenterX()<150 || GameScreen.getDog().isComplete()){
			speedX=0;
		}else{
			speedX=deltaWidth/3;
			//speedX=deltaWidth*6/21;
		}
		mapX-=(int)deltaTime*speedX;
		
		r.set(getMapX(),getMapY(),getMapX()+getWidth(),getMapY()+getHeight());
		
	}
	public void nullify(){
		r=null;
	}
	public Rect getR() {
		return r;
	}

	public void setR(Rect r) {
		this.r = r;
	}
}
