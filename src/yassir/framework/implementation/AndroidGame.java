package yassir.framework.implementation;


import yassir.framework.Audio;
import yassir.framework.FileIO;
import yassir.framework.Game;
import yassir.framework.Graphics;
import yassir.framework.Input;
import yassir.framework.Screen;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;




@SuppressLint("NewApi") public abstract class AndroidGame extends Activity implements Game {

	private static final String AD_INTERSTITIAL_UNIT_ID = "ca-app-pub-2720116830971921/6447668493";
	private static final String AD_UNIT_ID = "ca-app-pub-2720116830971921/4970935297";
	AndroidFastRenderView renderView;
	Graphics graphics;
	Audio audio;
	Input input;
	FileIO fileIO;
	Screen screen;
	WakeLock wakeLock;
	 static Point size;
		/** The Admob ad. */
		private InterstitialAd interstitialAd = null;
		private java.lang.Thread thread;
		public static boolean isGameScreen=false;
		public static  AdView adView = null;
		public static AdRequest adRequest ;
		public static AndroidGame game;
	public static DisplayMetrics metrics;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		boolean isPortrait =getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT;
		int frameBufferWidth = isPortrait?800:1280;
		int frameBufferHeight = isPortrait?1280:800;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
		float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        metrics =getResources().getDisplayMetrics();
        
        //renderView = new AndroidFastRenderView(this, frameBuffer);
        renderView = new AndroidFastRenderView(this,frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        
        game=this;
        
        RelativeLayout mainLayout = new RelativeLayout(this);
        mainLayout.addView(renderView);
        
     
     		
     // Create and load the AdView.
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        
       // Add the adView to it
 		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
 				LayoutParams.WRAP_CONTENT,
 				LayoutParams.WRAP_CONTENT);
 		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
 		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
 		
        mainLayout.addView(adView, params);

        // Request for Ads
 		adRequest = new AdRequest.Builder()
  
 		// Add a test device to show Test Ads
 		// .addTestDevice("5279F6FFF702565CA7DB31D15D9F549D")
 		// .addTestDevice("sss")
 				.build();
 		// Load ads into Banner Ads
		adView.loadAd(adRequest);
     	
		setContentView(mainLayout);
		 
		// Prepare the Interstitial Ad
 		interstitialAd = new InterstitialAd(this);
 		// Insert the Ad Unit ID
 		interstitialAd.setAdUnitId(AD_INTERSTITIAL_UNIT_ID);
 		
		// Load ads into Interstitial Ads
 		//interstitial.loadAd(adRequest);
 		
			// Prepare an Interstitial Ad Listener
			interstitialAd.setAdListener(new AdListener() {
				public void onAdLoaded() {
					// Call displayInterstitial() function
					interstitialAd.show();
				}
				 public void onAdFailedToLoad(int errorCode) {
	 		    	  //Toast.makeText(getApplicationContext(), "Interstitial Ads loading failed", Toast.LENGTH_SHORT).show();
	 		      }
			});
			
 		 //----------------------------------
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");
        screen = getInitScreen();
    }

	public static boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) game.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
        if (adView != null) {
	        adView.resume();
	      }
    }

    @Override
    public void onPause() {
    	if (adView != null) {
		      adView.pause();
		    }
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
        
        
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

       
        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    @Override
	public Screen getCurrentScreen() {

        return screen;
    }
    
    public void showInterstitialAds()
	{
		runOnUiThread(new Runnable() {
		    public void run() {
		    	 AdRequest interstitialAdRequest = new AdRequest.Builder()
		    	  
		  		// Add a test device to show Test Ads
		  		// .addTestDevice("5279F6FFF702565CA7DB31D15D9F549D")
		  		// .addTestDevice("sss")
		  				.build();
		    	 interstitialAd.loadAd(interstitialAdRequest);
		    }
		});
	}
}
