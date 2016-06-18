package com.angrymilou.yassir;

import com.angrymilou.yassir.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Image;
import yassir.framework.Input.TouchEvent;
import yassir.framework.Screen;
import yassir.framework.implementation.AndroidGame;
import Animation.Animation;

public class GameScreen extends Screen {


	public static ArrayList<Map> tilearray;
	public static Image current,mine_expose;
	public static MyDog dog;
	public Animation anim_dog,anim_mine,explose;
	public static Map maMap,t;
	public static int deltaHeight,deltaWidth,screenHeight,screenWidth,score=0;
	public Force force;
	private int numLevel,burn,idlevel;
	public static int fbar;
	private static boolean firstTimeCollide;
	private boolean paused=false,help=false;
	private Number scoreN;
	public static boolean volume;
	private Scanner sc;
	public static float deltaTime;
	public GameScreen(Game game,int numlevel,boolean v) {
		
		super(game);
		this.numLevel=numlevel;

		demo=false;
		
		volume=v;
		
		Graphics g=game.getGraphics();
		
		deltaHeight=g.getHeight()/7;
		deltaWidth=deltaHeight;
		
		screenWidth=g.getWidth();
		screenHeight=g.getHeight();
		
		tilearray=new ArrayList<Map>();
		
		switch(numlevel){
		case 1:idlevel=R.raw.level_tuto;break;
		case 2:idlevel=R.raw.level2;break;
		case 3:idlevel=R.raw.level3;break;
		case 4:idlevel=R.raw.level4;break;
		case 5:idlevel=R.raw.level5;break;
		case 6:idlevel=R.raw.level6;break;
		}
		
		try {
			loadMap(MainGame.mainGame.getMap(idlevel));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dog=new MyDog();
		
		force=new Force();
		
		deltaTime=0;
		
		anim_dog=new Animation();
		anim_dog.addFrame(Assets.run1,100);
		anim_dog.addFrame(Assets.run2,100);
		anim_dog.addFrame(Assets.run3,100);
		anim_dog.addFrame(Assets.run4,100);
		anim_dog.addFrame(Assets.run5,100);
		anim_dog.addFrame(Assets.run6,100);
		
		anim_mine=new Animation();
		anim_mine.addFrame(Assets.mine1,200);
		anim_mine.addFrame(Assets.mine2,200);
		
		explose=new Animation();
		explose.addFrame(Assets.explose1,200);
		explose.addFrame(Assets.explose2,200);
		explose.addFrame(Assets.explose3,200);
		explose.addFrame(Assets.explose4,200);
		explose.addFrame(Assets.explose5,200);
		explose.addFrame(Assets.explose6,200);
		explose.addFrame(Assets.explose7,200);
		explose.addFrame(Assets.explose8,200);
		explose.addFrame(Assets.explose9,200);
		
		current=Assets.run1;
		if(Assets.menuMusic.isPlaying()){
			Assets.menuMusic.pause();	
		}
		
		if(volume){
			if(Assets.gameS==null){
				Assets.loadGame(game);
			}else{
				if(!Assets.gameS.isPlaying()){
					Assets.gameS.play();
				}
			}
		}
		
		firstTimeCollide=true;
		
		scoreN=new Number();
		
		
 		AndroidGame.isGameScreen=true;
		
		
	}

	
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x>x && event.x<x+width-1 && event.y>y && event.y<y+height-1){
			return true;
		}else{
		return false;
		}
	}
	private void loadMap(String map) throws IOException{
		
		sc = new Scanner(map);
		int width=0;
		int height=0;
		ArrayList<String> Lines =new ArrayList<String>();
		String line=null;
		while(sc.hasNextLine()){
				line=sc.nextLine();
				if(line==null){
					break;
				}
				if(!line.startsWith("!")){
				Lines.add(line);
				width=Math.max(width, line.length());
			}
		}
			height=Lines.size();
			
				for(int i=0;i<height;i++){
					line=Lines.get(i);
					for(int j=0;j<width;j++){
						if(j<line.length()){
						t=new Map(j*deltaWidth,i*deltaHeight,Character.getNumericValue(line.charAt(j)));
						tilearray.add(t);
						
						}
					}
				}
				
		}

	public boolean burn(){
		burn++;
		if(burn<100){
			return true;
		}else{
			return false;
		}
	}
	
	public void updatePaused(){
		//traitement
		checkPausedInput();
	}
	public void updateHelp(){
		List<TouchEvent> TouchEvents =game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(inBounds(event,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight)){
					help=false;
				}
			}
		}
	}
	public void updateNonPaused(float deltaTime){
		if(dog.isCollide()){
			current=Assets.die;
			if(firstTimeCollide && volume){
			Assets.collideS(game);
			firstTimeCollide=false;
			}
				nullify();
				AndroidGame.game.showInterstitialAds();
				game.setScreen(new LoseScreen(game,numLevel,volume));
			//}
		}else if(dog.isExplose()){
			explose.update(60);
			current=Assets.die;
			burn();
			if(!burn()){
				nullify();
				AndroidGame.game.showInterstitialAds();
				game.setScreen(new LoseScreen(game,numLevel,volume));
			}
		}else{
			scoreN.setAll(score);
			anim_mine.update(60);
			mine_expose=anim_mine.getImage();
			anim_dog.update(60);
			checkInput(deltaTime);
			force.update();
			for(int i=0;i<tilearray.size();i++){
				tilearray.get(i).update(deltaTime);
			}
			dog.update(deltaTime);
			if(dog.isJump()){
			current=Assets.jump;	
			}else{
			current=anim_dog.getImage();
			}
			Graphics g=game.getGraphics();
			if(dog.getCenterX()>g.getWidth()
					){
				nullify();
				AndroidGame.game.showInterstitialAds();
				game.setScreen(new WinScreen(game,numLevel,volume));
			
			}
			
		}
	}
	
	@Override
	public void update(float deltaTime) {
		
		if(paused){
			if(help){
				updateHelp();
			}else{
				updatePaused();
			}
			
		}else{
			updateNonPaused(deltaTime);
		}
		
	}

	private void nullify() {
		
		t.nullify();
		dog.nullify();
		force.nullify();
		score=0;
		tilearray=null;
		dog=null;
		force=null;
		anim_dog=null;
		current=null;
		t=null;
		maMap=null;
	
		System.gc();
		
	}

	private void checkInput(float deltaTime) {
		Graphics g=game.getGraphics();
		List<TouchEvent> TouchEvents=game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent e=TouchEvents.get(i);
			if(e.type==TouchEvent.TOUCH_DOWN && inBounds(e,g.getWidth()-deltaWidth,deltaHeight/4, deltaWidth,deltaHeight)){
				paused=true;
			}else if(e.type==TouchEvent.TOUCH_DOWN){
				//if(!dog.isJump()){
				force.setPressed(true);
				//}
			}else if(e.type==TouchEvent.TOUCH_UP){
				if(!dog.isJump()){
				force.setForce(deltaTime);
				}
				force.setForce(0);
				force.setPressed(false);
			}
		}
	}
	
	private void checkPausedInput() {
		Graphics g=game.getGraphics();
		int w=g.getWidth()/11;
		int h=g.getHeight()/2;
		List<TouchEvent> TouchEvents=game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent e=TouchEvents.get(i);
			if(e.type==TouchEvent.TOUCH_DOWN && inBounds(e,6*w,h-w/2,w,w)){
				//resume
				paused=false;
			}else if(e.type==TouchEvent.TOUCH_DOWN && inBounds(e,2*w,h-w/2,w,w)){
				//choose level
				nullify();
				AndroidGame.isGameScreen=false;
				game.setScreen(new ChooseLevel(game,volume));
			}else if(e.type==TouchEvent.TOUCH_DOWN && inBounds(e,4*w,h-w/2,w,w)){
				//reply
				nullify();
				game.setScreen(new GameScreen(game,numLevel,volume));
			}else if(e.type==TouchEvent.TOUCH_DOWN && inBounds(e,8*w,h-w/2,w,w)){
				help=true;
			}
		}
	}

	public void paintPaused(){
		//traitement
		Graphics g=game.getGraphics();
		int w=g.getWidth()/11;
		int h=g.getHeight()/2;
		//g.drawRect(0,0,600,400,Color.argb(20,0,0,0));
		//g.drawScaledImage(Assets.transparent, 0,0, g.getWidth(),g.getHeight(),0,0,Assets.transparent.getWidth(),Assets.transparent.getHeight());
		g.drawScaledImage(Assets.button_orange,2*w,h-w/2,w,w,0,0,264,268);
		g.drawScaledImage(Assets.iconsYellow,2*w,h-w/2,w,w,3*178,180,178,180);
		
		g.drawScaledImage(Assets.button_orange,4*w,h-w/2,w,w,0,0,264,268);
		g.drawScaledImage(Assets.iconsYellow,4*w,h-w/2,w,w,2*178,180,178,180);
		
		g.drawScaledImage(Assets.button_orange,6*w,h-w/2,w,w,0,0,264,268);
		g.drawScaledImage(Assets.iconsYellow,6*w,h-w/2,w,w,0,2*180,178,180);
		
		g.drawScaledImage(Assets.button_orange,8*w,h-w/2,w,w,0,0,264,268);
		g.drawScaledImage(Assets.iconsYellow,8*w,h-w/2,w,w,2*178,0*180,178,180);
	}
	
	public void paintHelp(){
		Graphics g=game.getGraphics();
		g.drawScaledImage(Assets.helpScreen,0,0,g.getWidth(),g.getHeight(),0,0,Assets.helpScreen.getWidth(),Assets.helpScreen.getHeight());
		g.drawScaledImage(Assets.button_yellow, deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,238,220);
		g.drawScaledImage(Assets.iconsYellow,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,178,180);
	}
	public void paintNonPaused(){
		
		Graphics g=game.getGraphics();
		
		g.drawScaledImage(Assets.bg,0,0,g.getWidth(),g.getHeight(),0,0,Assets.bg.getWidth(),Assets.bg.getHeight());
		
		
		for(int i=0;i<tilearray.size();i++){
			
			if(tilearray.get(i).getType()==1){
				g.drawScaledImage(Assets.map,tilearray.get(i).getMapX(),tilearray.get(i).getMapY(),tilearray.get(i).getWidth(),tilearray.get(i).getHeight(),0,0,256,256);
			}else if(tilearray.get(i).getType()==2){
				g.drawScaledImage(Assets.map,tilearray.get(i).getMapX(),tilearray.get(i).getMapY(),tilearray.get(i).getWidth(),tilearray.get(i).getHeight(),256,0,256,256);
			}
		}
		
		g.drawScaledImage(current, dog.getCenterX(),dog.getCenterY(),deltaWidth*2,deltaHeight+30,0,0,current.getWidth(),current.getHeight());
		//g.drawRect(dog.getBody().left,dog.getBody().top,dog.getBody().width(),dog.getBody().height(), Color.RED);
		
		for(int i=0;i<tilearray.size();i++){
			 if(tilearray.get(i).getType()==3){
				g.drawScaledImage(Assets.ammo_box,tilearray.get(i).getMapX(),tilearray.get(i).getMapY(),tilearray.get(i).getWidth(),tilearray.get(i).getHeight(),0,0,98,98);
				//g.drawRect(tilearray.get(i).getR().centerX(),tilearray.get(i).getR().centerY(),deltaWidth,deltaHeight, Color.RED);
				//g.drawRect(tilearray.get(i).getMapX(),tilearray.get(i).getMapY(),deltaWidth,deltaHeight,color.black);
			}else if(tilearray.get(i).getType()==4){
				g.drawScaledImage(Assets.barrelBlack,tilearray.get(i).getMapX(),tilearray.get(i).getMapY(),tilearray.get(i).getWidth(),tilearray.get(i).getHeight(),0,0,70,96);
			}else if(tilearray.get(i).getType()==5){
				g.drawScaledImage(mine_expose,tilearray.get(i).getMapX(),tilearray.get(i).getMapY(),tilearray.get(i).getWidth(),tilearray.get(i).getHeight(),0,0,80,36);
			}else if(tilearray.get(i).getType()==6){
				g.drawScaledImage(Assets.bird,tilearray.get(i).getMapX(),tilearray.get(i).getMapY(),tilearray.get(i).getWidth(),tilearray.get(i).getHeight(),0,0,320,320);
			}else if(tilearray.get(i).getType()==7){
				g.drawScaledImage(explose.getImage(),tilearray.get(i).getMapX(),tilearray.get(i).getMapY()-deltaHeight/2,deltaWidth,deltaHeight,0,0,140,140);
			}
			
			
		}
		g.drawRect(0,0,force.getForce(),deltaHeight/4, fbar);
		
		g.drawScaledImage(Assets.bird,deltaWidth/4,deltaHeight/4,deltaWidth,deltaHeight,0,0,320,320);
		
		int x3=deltaWidth*5/4;
		int y3=deltaHeight/2;
		switch(scoreN.getX()){
			case 0 :g.drawScaledImage(Assets.numbers,x3,y3,50,50,11*54,0, 54,46);break;
			case 1:g.drawScaledImage(Assets.numbers,x3,y3,50,50,2*54,0, 54,46);break;
			case 2:g.drawScaledImage(Assets.numbers,x3,y3,50,50,3*54,0, 54,46);break;
			case 3:g.drawScaledImage(Assets.numbers,x3,y3,50,50,4*54,0, 54,46);break;
			case 4:g.drawScaledImage(Assets.numbers,x3,y3,50,50,5*54,0, 54,46);break;
			case 5:g.drawScaledImage(Assets.numbers,x3,y3,50,50,6*54,0, 54,46);break;
			case 6:g.drawScaledImage(Assets.numbers,x3,y3,50,50,7*54,0, 54,46);break;
			case 7:g.drawScaledImage(Assets.numbers,x3,y3,50,50,8*54,0, 54,46);break;
			case 8:g.drawScaledImage(Assets.numbers,x3,y3,50,50,9*54,0, 54,46);break;
			case 9:g.drawScaledImage(Assets.numbers,x3,y3,50,50,10*54,0, 54,46);break;
		}
		
		int x2=deltaWidth*5/3;
		int y2=deltaHeight/2;
		switch(scoreN.getY()){
			case 0 :g.drawScaledImage(Assets.numbers,x2,y2,50,50,11*54,0, 54,46);break;
			case 1:g.drawScaledImage(Assets.numbers,x2,y2,50,50,2*54,0, 54,46);break;
			case 2:g.drawScaledImage(Assets.numbers,x2,y2,50,50,3*54,0, 54,46);break;
			case 3:g.drawScaledImage(Assets.numbers,x2,y2,50,50,4*54,0, 54,46);break;
			case 4:g.drawScaledImage(Assets.numbers,x2,y2,50,50,5*54,0, 54,46);break;
			case 5:g.drawScaledImage(Assets.numbers,x2,y2,50,50,6*54,0, 54,46);break;
			case 6:g.drawScaledImage(Assets.numbers,x2,y2,50,50,7*54,0, 54,46);break;
			case 7:g.drawScaledImage(Assets.numbers,x2,y2,50,50,8*54,0, 54,46);break;
			case 8:g.drawScaledImage(Assets.numbers,x2,y2,50,50,9*54,0, 54,46);break;
			case 9:g.drawScaledImage(Assets.numbers,x2,y2,50,50,10*54,0, 54,46);break;
		}
		
		int x=deltaWidth*5/4+(x2-x3)*2;
		int y=deltaHeight/2;
		switch(scoreN.getZ()){
			case 0 :g.drawScaledImage(Assets.numbers,x,y,50,50,11*54,0, 54,46);break;
			case 1:g.drawScaledImage(Assets.numbers,x,y,50,50,2*54,0, 54,46);break;
			case 2:g.drawScaledImage(Assets.numbers,x,y,50,50,3*54,0, 54,46);break;
			case 3:g.drawScaledImage(Assets.numbers,x,y,50,50,4*54,0, 54,46);break;
			case 4:g.drawScaledImage(Assets.numbers,x,y,50,50,5*54,0, 54,46);break;
			case 5:g.drawScaledImage(Assets.numbers,x,y,50,50,6*54,0, 54,46);break;
			case 6:g.drawScaledImage(Assets.numbers,x,y,50,50,7*54,0, 54,46);break;
			case 7:g.drawScaledImage(Assets.numbers,x,y,50,50,8*54,0, 54,46);break;
			case 8:g.drawScaledImage(Assets.numbers,x,y,50,50,9*54,0, 54,46);break;
			case 9:g.drawScaledImage(Assets.numbers,x,y,50,50,10*54,0, 54,46);break;
		}
	
		g.drawScaledImage(Assets.iconsYellow,g.getWidth()-deltaWidth,deltaHeight/4, deltaWidth,deltaHeight,4*178,0,178,180);
	}
	@Override
	public void paint(float deltaTime){
		
		if(paused){
			if(help){
				paintHelp();
			}else{
				paintNonPaused();
				paintPaused();
			}
		}else{
			paintNonPaused();
		}
			
	}
	
	@Override
	public void pause() {
		paused=true;
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
		paused=true;
	}

	public static MyDog getDog() {
		// TODO Auto-generated method stub
		return dog;
	}

}
