package com.angrymilou.yassir;

import java.util.List;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Input.TouchEvent;
import yassir.framework.Screen;

public class LoseScreen extends Screen {

	private static int deltaWidth=Screen.deltaWidth;
	private static int deltaHeight=Screen.deltaHeight;
	private int numLevel;
	private boolean volume,help=false;
	public LoseScreen(Game game,int numlevel,boolean v) {
		super(game);
		this.numLevel=numlevel;
		volume=v;
	}

	public void updateHelp(){
		List<TouchEvent> TouchEvents =game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(inBounds(event,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight)){
					help=false;
					game.setScreen(new ChooseLevel(game, volume));
				}
			}
		}
	}
	public void updateNonHelp(){
		List<TouchEvent> TouchEvents =game.getInput().getTouchEvents();
		Graphics g=game.getGraphics();
		int w=g.getWidth()/11;
		int h=g.getHeight()/2;
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(inBounds(event,3*w,h-w/2,w,w)){
					game.setScreen(new ChooseLevel(game,volume));
				}else if(inBounds(event, 5*w,h-w/2,w,w)){
					game.setScreen(new GameScreen(game, numLevel, volume));
				}else if(inBounds(event, 7*w,h-w/2,w,w)){
					help=true;
				}
			}
		}
	
	}
	@Override
	public void update(float deltaTime) {
	
		if(help){
			updateHelp();
		}else{
			updateNonHelp();
		}
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x>x && event.x<x+width && event.y>y && event.y<y+height){
			return true;
		}else{
		return false;
		}
	}
	public void paintHelp(){
		Graphics g=game.getGraphics();
		g.drawScaledImage(Assets.helpScreen,0,0,g.getWidth(),g.getHeight(),0,0,Assets.helpScreen.getWidth(),Assets.helpScreen.getHeight());
		g.drawScaledImage(Assets.button_yellow, deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,238,220);
		g.drawScaledImage(Assets.iconsYellow,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,178,180);
	}
	
	public void paintNonHelp(){
		Graphics g=game.getGraphics();
		int w=g.getWidth()/11;
		int h=g.getHeight()/2;
		
		g.drawScaledImage(Assets.button_orange,3*w,h-w/2,w,w,0,0,264,268);
		g.drawScaledImage(Assets.iconsYellow,3*w,h-w/2,w,w,3*178,180,178,180);
		
		g.drawScaledImage(Assets.button_orange,5*w,h-w/2,w,w,0,0,264,268);
		g.drawScaledImage(Assets.iconsYellow,5*w,h-w/2,w,w,2*178,180,178,180);
		
		g.drawScaledImage(Assets.button_orange,7*w,h-w/2,w,w,0,0,264,268);
		g.drawScaledImage(Assets.iconsYellow,7*w,h-w/2,w,w,2*178,0*180,178,180);

	}
	@Override
	public void paint(float deltaTime) {
		if(help){
			paintHelp();
		}else{
			paintNonHelp();
		}
	}

	@Override
	public void pause() {
		if(volume)
			Assets.gameS.pause();
	}

	@Override
	public void resume() {
		if(volume){
			Assets.gameS.play();
		}

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backButton() {
		game.setScreen(new ChooseLevel(game,volume));
	}

}
