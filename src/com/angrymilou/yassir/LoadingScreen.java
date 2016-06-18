package com.angrymilou.yassir;


import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Graphics.ImageFormat;
import yassir.framework.Screen;

public class LoadingScreen extends Screen {

	public static boolean start=true;
	public LoadingScreen(Game game) {
		super(game);
				
	}

	@Override
	public void update(float deltaTime) {
		
		Graphics g=game.getGraphics();
		Assets.ammo_box=g.newImage("ammo_box.png",ImageFormat.ARGB4444);
		Assets.bg=g.newImage("background.jpg",ImageFormat.RGB565);
		Assets.button_text=g.newImage("big_button_text_background.png",ImageFormat.ARGB4444);
		Assets.menu=g.newImage("menu.png",ImageFormat.RGB565);
		Assets.map=g.newImage("tileset_02.png", ImageFormat.ARGB4444);
		Assets.run1=g.newImage("sprite/run1.png",ImageFormat.ARGB4444);
		Assets.run2=g.newImage("sprite/run2.png",ImageFormat.ARGB4444);
		Assets.run3=g.newImage("sprite/run3.png",ImageFormat.ARGB4444);
		Assets.run4=g.newImage("sprite/run4.png",ImageFormat.ARGB4444);
		Assets.run5=g.newImage("sprite/run5.png",ImageFormat.ARGB4444);
		Assets.run6=g.newImage("sprite/run6.png",ImageFormat.ARGB4444);
		Assets.jump=g.newImage("jump.png",ImageFormat.ARGB4444);
		Assets.die=g.newImage("Dog_Faint.png",ImageFormat.ARGB4444);
		Assets.defeat=g.newImage("window_defeat.png",ImageFormat.ARGB4444);
		Assets.button_orange=g.newImage("big_button_orange.png",ImageFormat.ARGB4444,0,0,264,268);
		Assets.button_yellow=g.newImage("button_change_weapon.png",ImageFormat.ARGB4444);
		Assets.levelStatut=g.newImage("mission_selection.png",ImageFormat.ARGB4444);
		Assets.barrelBlack=g.newImage("barrel_black.png",ImageFormat.ARGB4444);
		Assets.mine1=g.newImage("mine.png",ImageFormat.ARGB4444,0,0,80,36);
		Assets.mine2=g.newImage("mine.png",ImageFormat.ARGB4444,80,0,80,36);
		Assets.explose1=g.newImage("explosion.png", ImageFormat.ARGB4444,0,0,140,140);
		Assets.explose2=g.newImage("explosion.png", ImageFormat.ARGB4444,140,0,140,140);
		Assets.explose3=g.newImage("explosion.png", ImageFormat.ARGB4444,140*2,0,140,140);
		Assets.explose4=g.newImage("explosion.png", ImageFormat.ARGB4444,140*3,0,140,140);
		Assets.explose5=g.newImage("explosion.png", ImageFormat.ARGB4444,140*4,0,140,140);
		Assets.explose6=g.newImage("explosion.png", ImageFormat.ARGB4444,140*5,0,140,140);
		Assets.explose7=g.newImage("explosion.png", ImageFormat.ARGB4444,140*6,0,140,140);
		Assets.explose8=g.newImage("explosion.png", ImageFormat.ARGB4444,140*7,0,140,140);
		Assets.explose9=g.newImage("explosion.png", ImageFormat.ARGB4444,140*8,0,140,140);
		Assets.bird=g.newImage("26923.png",ImageFormat.ARGB4444);
		Assets.iconsYellow=g.newImage("icons_on_buttons_yellow.png", ImageFormat.ARGB4444);
		Assets.numbers=g.newImage("font_number_of_bullets.png",ImageFormat.ARGB4444);
		Assets.button_green=g.newImage("big_button_green.png",ImageFormat.ARGB4444);
		Assets.button_blue=g.newImage("big_button_blue.png",ImageFormat.ARGB4444);
		Assets.start=g.newImage("icons_on_big_buttons_green.png",ImageFormat.ARGB4444);
		Assets.help=g.newImage("icons_on_big_buttons_orange.png",ImageFormat.ARGB4444);
		Assets.about=g.newImage("icons_on_big_buttons_blue.png",ImageFormat.ARGB4444);
		Assets.helpScreen=g.newImage("help.png",ImageFormat.ARGB4444);
		Assets.text=g.newImage("text.png",ImageFormat.ARGB4444);
		Assets.point= game.getAudio().createSound("sound/sfx_point.ogg");
		Assets.mineS= game.getAudio().createSound("sound/pl_fall.ogg");
		Assets.menuMusic=game.getAudio().createMusic("sound/boss.ogg");
		Assets.menuMusic.setLooping(true);
		Assets.numeroLevel=g.newImage("mission_selection_numbers.png", ImageFormat.ARGB4444);
		Assets.volumeOnOff=g.newImage("volume.png", ImageFormat.ARGB4444);
		Assets.victory=g.newImage("window_summary_victory.png",  ImageFormat.ARGB4444);
		Assets.arrowLeft=g.newImage("short_arrow_left.png", ImageFormat.ARGB4444);
		Assets.arrowRight=g.newImage("short_arrow_right.png", ImageFormat.ARGB4444);
		Assets.comingSoon=g.newImage("Coming-soon.png", ImageFormat.ARGB4444);
		Assets.universes=g.newImage("universe.png", ImageFormat.ARGB4444);
		Assets.ribbon=g.newImage("ribbon.png", ImageFormat.ARGB4444);
		
		g=null;
		System.gc();
		game.setScreen(new MainMenuScreen(game,true));
		
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g=game.getGraphics();
		g.drawScaledImage(Assets.splash,0,0,g.getWidth(),g.getHeight(),0,0,Assets.splash.getWidth(),Assets.splash.getHeight());
		g=null;
		System.gc();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
		

	}

}
