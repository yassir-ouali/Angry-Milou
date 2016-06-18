package com.angrymilou.yassir;

import java.util.List;

import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Input.TouchEvent;
import yassir.framework.Screen;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class about extends Screen{

	private static int deltaWidth=Screen.deltaWidth;
	private static int deltaHeight=Screen.deltaHeight;
	private boolean volume;
	public about(Game game,boolean v) {
		super(game);
		volume=v;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> TouchEvents =game.getInput().getTouchEvents();
		for(int i=0;i<TouchEvents.size();i++){
			TouchEvent event=TouchEvents.get(i);
			if(event.type==TouchEvent.TOUCH_UP){
				if(inBounds(event,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight)){
					game.setScreen(new MainMenuScreen(game,volume));
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
		// TODO Auto-generated method stub
		Graphics g=game.getGraphics();
		
		g.drawScaledImage(Assets.menu,0,0,g.getWidth(),g.getHeight(),0,0,Assets.menu.getWidth(),Assets.menu.getHeight());
		
		g.drawScaledImage(Assets.defeat,deltaWidth,deltaHeight/2, g.getWidth()-2*deltaWidth,g.getHeight()-deltaHeight, 0, 0,Assets.defeat.getWidth(),Assets.defeat.getHeight());
		/*g.drawScaledImage(Assets.button_orange,g.getWidth()/2-deltaWidth-10,g.getHeight()-2*deltaHeight,deltaWidth,deltaHeight,0,0,Assets.button_orange.getWidth(),Assets.button_orange.getHeight());
		g.drawScaledImage(Assets.button_orange,g.getWidth()/2+10,g.getHeight()-2*deltaHeight,deltaWidth,deltaHeight,0,0,Assets.button_orange.getWidth(),Assets.button_orange.getHeight());
		*/
		Paint p=new Paint();
		p.setColor(Color.WHITE);
		setTextSizeForWidth(p,g.getWidth()-5*deltaWidth,"I'm Yassir Ouali, student engineer in ENSAO,");
		drawMultilineText("I'm Yassir Ouali, student engineer in ENSAO ,\nI study computer engineering," +
				"and i'm very \n passionate with new technologies and \ncomputer developement !\n" +
				"\nContact me at :\n yassir.oua@gmail.com \n+212607031162",deltaWidth*5/2,deltaHeight*2, p, g);
		g.drawScaledImage(Assets.button_yellow, deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,238,220);
		g.drawScaledImage(Assets.iconsYellow,deltaWidth/3,deltaHeight*6,deltaWidth,deltaHeight,0,0,178,180);
		g.drawScaledImage(Assets.text,g.getWidth()/2-deltaWidth*2,deltaHeight/4,deltaWidth*4,deltaHeight*2,0,400,400,200);
		
	}

	void drawMultilineText(String str, int x, int y, Paint paint,Graphics g) {
	    int      lineHeight = 0;
	    int      yoffset    = 0;
	    String[] lines      = str.split("\n");
	    Rect mBounds =new Rect();
	    
	    // set height of each line (height of text + 20%)
	    paint.getTextBounds("Ig", 0, 2, mBounds);
	    lineHeight = (int) (mBounds.height() * 1.2);
	    // draw each line
	    for (int i = 0; i < lines.length; ++i) {
	        g.drawString(lines[i], x, y + yoffset, paint);
	        yoffset = yoffset + lineHeight;
	    }
	}
	
	private static void setTextSizeForWidth(Paint paint, float desiredWidth,
	        String text) {

	    // Pick a reasonably large value for the test. Larger values produce
	    // more accurate results, but may cause problems with hardware
	    // acceleration. But there are workarounds for that, too; refer to
	    // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
	    final float testTextSize = 48f;

	    // Get the bounds of the text, using our testTextSize.
	    paint.setTextSize(testTextSize);
	    Rect bounds = new Rect();
	    paint.getTextBounds(text, 0, text.length(), bounds);

	    // Calculate the desired size as a proportion of our testTextSize.
	    float desiredTextSize = testTextSize * desiredWidth / bounds.width();

	    // Set the paint for that size.
	    paint.setTextSize(desiredTextSize);
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
