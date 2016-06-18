package com.angrymilou.yassir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import yassir.framework.Screen;
import yassir.framework.implementation.AndroidGame;
import LevelSys.LevelSystem;
import android.os.Environment;
import com.angrymilou.yassir.R;


public class MainGame extends AndroidGame{

	private boolean success;
	public static LevelSystem ls;
	public static File levelDest;
	public static MainGame mainGame;

	@Override
	public Screen getInitScreen(){
		
		mainGame=this;
		
		levelDest=new File(Environment.getExternalStorageDirectory()+File.separator+"nomDir","level.xml");
		
		File levelDir=new File(Environment.getExternalStorageDirectory()+File.separator+"nomDir");
		
		success=true;
		if(!levelDir.exists()){
			success=levelDir.mkdir();
			
		}
		if(!levelDest.exists()){
		
			if(success){
				try {
					
					InputStream is=getResources().openRawResource(R.raw.level);
					String levels=isToString(is);
					FileOutputStream output=new FileOutputStream(levelDest);
					output.write(levels.getBytes());
					
					is=null;
					levels=null;
					output=null;
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		ls =new LevelSystem(levelDest);
		
		levelDir=null;
		System.gc();

		return (new SplashLoadingScreen(this));
	}
	
	public String getMap(int numLevel){
		return isToString(getResources().openRawResource(numLevel));
	}
	public static String isToString(InputStream is) {
		BufferedReader bf =new BufferedReader(new InputStreamReader(is));
		StringBuilder sb=new StringBuilder();
		String line=null;
		try {
			while((line=bf.readLine())!=null){
				sb.append(line+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		line=null;
		bf=null;
		System.gc();
		return sb.toString();
		
	}

	@Override
	public void onBackPressed(){
		getCurrentScreen().backButton();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}