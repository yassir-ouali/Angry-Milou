package com.angrymilou.yassir;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Graphics.ImageFormat;
import yassir.framework.Screen;

public class SplashLoadingScreen extends Screen {

	public SplashLoadingScreen(Game game) {
		super(game);
		Graphics g=game.getGraphics();
		Assets.splash=g.newImage("loading.png",ImageFormat.RGB565);
		g=null;
		System.gc();
	}

	@Override
	public void update(float deltaTime) {
	
		game.setScreen(new LoadingScreen(game));
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
		// TODO Auto-generated method stub

	}

}
