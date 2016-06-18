package com.angrymilou.yassir;

import com.angrymilou.yassir.R;
import java.util.List;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Input.TouchEvent;
import yassir.framework.Screen;
import LevelSys.LevelSystem;

public class WinScreen extends Screen {

	private static int deltaWidth;
	private static int deltaHeight;
	public static LevelSystem ls=MainGame.ls;
	private int numLevel;
	private boolean volume;
	public WinScreen(Game game,int numlevel,boolean v) {
		super(game);
		Graphics g=game.getGraphics();
		deltaWidth=g.getWidth()/6;
		deltaHeight=g.getHeight()/7;
		volume=v;
		this.numLevel=numlevel;
		if(numLevel<6){
		ls.setStatut(numLevel*2+1,"unlocked");
		}
	}

	@Override
	public void update(float deltaTime) {
		System.out.println("level6 id"+R.raw.level6);
		List<TouchEvent> TouchEvents =game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_DOWN){
				if(inBounds(event,deltaWidth*3-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight)){
					game.setScreen(new GameScreen(game,numLevel,volume));
				}else if(inBounds(event,deltaWidth*2-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight)){
					game.setScreen(new ChooseLevel(game, volume));
				}else if(inBounds(event,deltaWidth*4-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight)){
					if(numLevel<6){
						game.setScreen(new GameScreen(game,numLevel+1,volume));
					}else{
						//coming soon
						game.setScreen(new comingSoon(game,volume));
					}
				}
			}
		}
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
		g.drawScaledImage(Assets.victory, deltaWidth, deltaHeight,deltaWidth*4,deltaHeight*2, 0,0,Assets.victory.getWidth(),Assets.victory.getHeight());
		
		g.drawScaledImage(Assets.button_orange,deltaWidth*2-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight,0,0,Assets.button_orange.getWidth(),Assets.button_orange.getHeight());
		g.drawScaledImage(Assets.iconsYellow, deltaWidth*2-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight,3*178,180,178,180);
		
		g.drawScaledImage(Assets.button_orange,deltaWidth*3-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight,0,0,Assets.button_orange.getWidth(),Assets.button_orange.getHeight());
		g.drawScaledImage(Assets.iconsYellow,deltaWidth*3-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight,2*178,180,178,180);
		
		g.drawScaledImage(Assets.button_orange,deltaWidth*4-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight,0,0,Assets.button_orange.getWidth(),Assets.button_orange.getHeight());
		g.drawScaledImage(Assets.iconsYellow,deltaWidth*4-deltaHeight/2,deltaHeight*4,deltaHeight,deltaHeight,0,180,178,180);
		
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
