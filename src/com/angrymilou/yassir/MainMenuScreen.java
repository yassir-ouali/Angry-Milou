package com.angrymilou.yassir;

import java.util.List;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Input.TouchEvent;
import yassir.framework.Screen;
import yassir.framework.implementation.AndroidGame;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MainMenuScreen extends Screen {

	private int deltaWidth,deltaHeight;
	public static boolean volume;
	public MainMenuScreen(Game game,boolean v) {
		super(game);
		// TODO Auto-generated constructor stub
		volume=v;
		Graphics g =game.getGraphics();
		deltaWidth=g.getWidth()/15;
		deltaHeight=g.getHeight()/12;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> TouchEvents =game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_DOWN){
				if(inBounds(event,deltaWidth*5,deltaHeight*2,deltaWidth*6,deltaHeight*2)||inBounds(event,deltaWidth*5-deltaWidth,deltaHeight*2-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2)){
					game.setScreen(new ChooseLevel(game,volume)); 
				}else if(inBounds(event,deltaWidth*7,deltaHeight*8,deltaWidth*6,deltaHeight*2)||inBounds(event,deltaWidth*7-deltaWidth,deltaHeight*8-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2)){
					game.setScreen(new about(game,volume));
				}else if(inBounds(event,deltaWidth*6,deltaHeight*5,deltaWidth*6,deltaHeight*2) || inBounds(event,deltaWidth*6-deltaWidth,deltaHeight*5-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2)){
					game.setScreen(new help(game,volume));
				}else if(inBounds(event,deltaWidth/2,deltaHeight*10+deltaHeight/2,deltaWidth*3/2,deltaHeight*3/2)){
					if(volume){
						volume=false;
						Assets.menuMusic.pause();
					}else{
						if(!Assets.menuMusic.isPlaying()){
						volume=true;
						Assets.menuMusic.play();
						}
					}
				}
			}
		}
		
		//System.out.println("widthpixel :"+AndroidGame.metrics.widthPixels+" xdpi :"+AndroidGame.metrics.xdpi+" density dpi"+AndroidGame.metrics.densityDpi);

	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x>x && event.x<x+width && event.y>y && event.y<y+height){
			return true;
		}else{
		return false;
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g =game.getGraphics();
		g.drawScaledImage(Assets.menu,0,0,g.getWidth(),g.getHeight(),0,0,Assets.menu.getWidth(),Assets.menu.getHeight());
		
		
		g.drawScaledImage(Assets.button_text,deltaWidth*5,deltaHeight*2,deltaWidth*6,deltaHeight*2,0,0,804,212);
		g.drawScaledImage(Assets.button_green,deltaWidth*5-deltaWidth,deltaHeight*2-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2,0,0,264,268);
		g.drawScaledImage(Assets.start,deltaWidth*5-deltaWidth,deltaHeight*2-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2,0,0,264,268);
		g.drawScaledImage(Assets.text,deltaWidth*6,deltaHeight*2,deltaWidth*5,deltaHeight*2,0,0,400,200);
		
		g.drawScaledImage(Assets.button_text,deltaWidth*6,deltaHeight*5,deltaWidth*6,deltaHeight*2,0,0,804,212);
		g.drawScaledImage(Assets.button_orange,deltaWidth*6-deltaWidth,deltaHeight*5-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2,0,0,264,268);
		g.drawScaledImage(Assets.help,deltaWidth*6-deltaWidth,deltaHeight*5-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2,0,0,264,268);
		g.drawScaledImage(Assets.text,deltaWidth*7,deltaHeight*5,deltaWidth*5,deltaHeight*2,0,200,400,200);
		
		g.drawScaledImage(Assets.button_text,deltaWidth*7,deltaHeight*8,deltaWidth*6,deltaHeight*2,0,0,804,212);
		g.drawScaledImage(Assets.button_blue,deltaWidth*7-deltaWidth,deltaHeight*8-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2,0,0,264,268);
		g.drawScaledImage(Assets.about,deltaWidth*7-deltaWidth,deltaHeight*8-(deltaWidth*2-deltaHeight*2)/2,deltaWidth*2,deltaWidth*2,0,0,264,268);
		g.drawScaledImage(Assets.text,deltaWidth*8,deltaHeight*8,deltaWidth*5,deltaHeight*2,0,400,400,200);
		
		g.drawScaledImage(Assets.button_yellow,deltaWidth/2,deltaHeight*10+deltaHeight/2,deltaWidth*3/2,deltaHeight*3/2,238,0, 238,220);

			if(!volume){
				g.drawScaledImage(Assets.volumeOnOff,deltaWidth/2+deltaWidth/3,deltaHeight*10+deltaHeight/2,deltaWidth,deltaHeight*3/2,60,3,50,50);	
			}else{
				g.drawScaledImage(Assets.volumeOnOff,deltaWidth/2+deltaWidth/3,deltaHeight*10+deltaHeight/2,deltaWidth,deltaHeight*3/2,0,0,50,50);
			}
		
		
	}

	
	
	@Override
	public void pause() {
		Assets.menuMusic.pause();
	}

	@Override
	public void resume() {
		if(volume){
			Assets.menuMusic.play();
		}

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
	
	}

}
