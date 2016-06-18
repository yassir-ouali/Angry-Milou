package com.angrymilou.yassir;

import java.util.List;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Input.TouchEvent;
import yassir.framework.Screen;
import LevelSys.LevelSystem;

public class comingSoon extends Screen{

	private Boolean[] l; 
	public static LevelSystem ls=MainGame.ls;
	private boolean volume;
	public comingSoon(Game game,boolean v) {
		super(game);
		l=new Boolean[12];
		volume=v;
		Graphics g=game.getGraphics();
		deltaHeight=g.getHeight()/9;
		deltaWidth=g.getWidth()/6;
		for(int i=0;i<12;i++){
			
				if(ls.getStatut(i*2+1).equals("unlocked")){
						l[i]=true;
				}else{
						l[i]=false;
				}
			
		}
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> TouchEvents=game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_DOWN){
				if(inBounds(event,deltaWidth,deltaHeight*3,deltaWidth,2*deltaHeight) && l[0]){
					game.setScreen(new GameScreen(game,7,volume));
				}else if(inBounds(event,deltaWidth*5/2,deltaHeight*3,deltaWidth,2*deltaHeight) && l[1]){
					game.setScreen(new GameScreen(game,8,volume));
				}else if(inBounds(event,deltaWidth*4,deltaHeight*3,deltaWidth,2*deltaHeight) && l[2]){
					game.setScreen(new GameScreen(game,9,volume));
				}else if(inBounds(event,deltaWidth,deltaHeight*6,deltaWidth,2*deltaHeight) && l[3]){
					game.setScreen(new GameScreen(game,10,volume));
				}else if(inBounds(event,deltaWidth*5/2,deltaHeight*6,deltaWidth,2*deltaHeight) && l[4]){
					game.setScreen(new GameScreen(game,11,volume));
				}else if(inBounds(event,deltaWidth*4,deltaHeight*6,deltaWidth,2*deltaHeight) && l[5]){
					game.setScreen(new GameScreen(game,12,volume));
				}else if(inBounds(event,deltaWidth/4,deltaHeight*15/2,deltaWidth*3/5,deltaHeight*5/4)){
					game.setScreen(new MainMenuScreen(game,volume));
				}else if(inBounds(event,deltaWidth/3,9*deltaHeight/2,deltaWidth/2,deltaHeight*3/2)){
					game.setScreen(new ChooseLevel(game,volume));
				}
			}
		}
		
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x>x && event.x<x+width-1 && event.y>y && event.y<y+height-1){
			return true;
		}else{
		return false;
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g =game.getGraphics();
		g.drawScaledImage(Assets.menu,0,0,g.getWidth(),g.getHeight(),0,0,Assets.menu.getWidth(),Assets.menu.getHeight());
		
		for(int i=0;i<6;i++){
			l[i]=l[i+6];
			}
		for(int i=0;i<6;i++){
			int x=(((i%3)*3)+2);
			if(l[i]){
			g.drawScaledImage(Assets.levelStatut,deltaWidth*x/2,deltaHeight*(i/3+1)*3,deltaWidth,2*deltaHeight,0,0,360,360);
			g.drawScaledImage(Assets.numeroLevel,deltaWidth*x/2+deltaWidth/6,deltaHeight*(i/3+1)*3+deltaHeight/4,deltaWidth*2/3,2*deltaHeight*2/3, (i%5)*140,((5+i)/5-1)*150, 140,150);
		}else{
			g.drawScaledImage(Assets.levelStatut,deltaWidth*x/2,deltaHeight*(i/3+1)*3,deltaWidth,2*deltaHeight,720,0,360,360);
		}
	}
		g.drawScaledImage(Assets.ribbon,deltaWidth,deltaHeight/2,deltaWidth*4,deltaHeight*2,0,0, 1882,244);
		g.drawScaledImage(Assets.universes,deltaWidth*2,deltaHeight/2,deltaWidth*2,deltaHeight*2,0,50, 200,50);
		
		g.drawScaledImage(Assets.comingSoon,0,0,g.getWidth(),g.getHeight(),0,0,784,588);
		
		g.drawScaledImage(Assets.arrowLeft,deltaWidth/5,9*deltaHeight/2,deltaWidth/2,deltaHeight*3/2,0,0, 158, 240);
		
		g.drawScaledImage(Assets.button_yellow, deltaWidth/4,deltaHeight*15/2,deltaWidth*3/5,deltaHeight*5/4,0,0,238,220);
		g.drawScaledImage(Assets.iconsYellow,deltaWidth/4,deltaHeight*15/2,deltaWidth*3/5,deltaHeight*5/4,0,0,178,180);
		
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
