package com.angrymilou.yassir;

import java.util.List;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Input.TouchEvent;
import yassir.framework.Screen;

public class help extends Screen {

	private static int deltaWidth=Screen.deltaWidth;
	private static int deltaHeight=Screen.deltaHeight;
	private boolean volume;
	
	public help(Game game,boolean v) {
		super(game);
		volume=v;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> TouchEvents =game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				Graphics g =game.getGraphics();
				if(inBounds(event,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight)){
					game.setScreen(new MainMenuScreen(game,volume));
				}
			}
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g=game.getGraphics();
		g.drawScaledImage(Assets.helpScreen,0,0,g.getWidth(),g.getHeight(),0,0,Assets.helpScreen.getWidth(),Assets.helpScreen.getHeight());
		g.drawScaledImage(Assets.button_yellow, deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,238,220);
		g.drawScaledImage(Assets.iconsYellow,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,178,180);
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x>x && event.x<x+width && event.y>y && event.y<y+height){
			return true;
		}else{
		return false;
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
		game.setScreen(new MainMenuScreen(game,volume));
	}

}
