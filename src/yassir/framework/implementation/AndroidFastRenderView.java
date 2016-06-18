package yassir.framework.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class AndroidFastRenderView extends SurfaceView implements Runnable {
    AndroidGame game;
    Bitmap framebuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;
    //**********************************
    private boolean locker = true, initialised = false;
    private long now = SystemClock.elapsedRealtime(), lastRefresh=0, lastfps=0;
    private int fps = 0, frames = 0, runtime = 0, drawtime = 0;
    
    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();

    }

    public void resume() { 
        running = true;
        renderThread = new Thread(this);
        renderThread.start();   

    }      
    
    @Override
	public void run() {
    	
    	//int rand = (int) (Math.random() * 100);+
    			
    	synchronized (android.content.Context.ACCESSIBILITY_SERVICE) {
    	Rect dstRect=new Rect();
    				while (running) {
    					//System.out.println("start-");

    					now = SystemClock.elapsedRealtime();
    					if (now - lastRefresh > 50) {//limit 35fps - 28
    						lastRefresh = SystemClock.elapsedRealtime();
    						if (!holder.getSurface().isValid()) {
    							continue;
    						}

    						//fps
    						if (now - lastfps > 1000) {
    							fps = frames;
    							frames = 0;
    							lastfps = SystemClock.elapsedRealtime();
    						} else {
    							frames++;
    						}

    						//step
    						if (initialised)
    							game.getCurrentScreen().update(1);
    						//take run time
    						runtime = (int) (SystemClock.elapsedRealtime() - lastRefresh);

    						//draw screen
    						Canvas canvas = holder.lockCanvas();
							canvas.getClipBounds(dstRect);
    			            canvas.drawBitmap(framebuffer, null, dstRect, null);     
    						if (initialised)
    							//Draw(canvas);
    							game.getCurrentScreen().paint(1);
    						else {
    							//initialise game
    							//Start();
    							game.getCurrentScreen().paint(1);
    							initialised = true;
    						}
    						holder.unlockCanvasAndPost(canvas);
    						//take render time
    						drawtime = (int) (SystemClock.elapsedRealtime() - lastRefresh) - runtime;
    					}
    					//System.out.println("finish-----");
    					//try {
    					//	Thread.sleep(10);
    					//} catch (InterruptedException e) {
    					//	e.printStackTrace();
    					//}
    				}
    			}
    			
    	//-----------------------------------------------------------------------------------------
        /*Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running) {  
            if(!holder.getSurface().isValid())
                continue;           
            

            float deltaTime = (System.nanoTime() - startTime) / 10000000.000f;
            startTime =System.nanoTime();
            
            if (deltaTime > 3.15){
                deltaTime = (float) 3.15;
           }
     
            GameScreen.deltaTime=deltaTime;
            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().paint(deltaTime);
          
            
            
            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);                           
            holder.unlockCanvasAndPost(canvas);
            
            /*
            try {
				renderThread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
    }

    public void pause() {                        
        running = false;                        
        while(true) {
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e) {
                // retry
            }
            
        }
    }  
}
