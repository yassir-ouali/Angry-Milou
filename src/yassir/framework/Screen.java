package yassir.framework;

import com.angrymilou.yassir.GameScreen;

public abstract class Screen {

	public final Game game;
	public static int deltaWidth=GameScreen.deltaWidth;
	public static int deltaHeight=GameScreen.deltaHeight;
	public static boolean demo;
	public Screen(Game game){
		this.game=game;
		Graphics g=game.getGraphics();
		deltaHeight=g.getHeight()/7;
		deltaWidth=deltaHeight;
	}
	
	 public abstract void update(float deltaTime);

    public abstract void paint(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
    
    public abstract void backButton();
}
