package Animation;


import java.util.ArrayList;

import Animation.Animation.AnimeFrame;

import yassir.framework.Image;

public class Animation {

	private ArrayList frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;
	


	public Animation() {
		
		frames = new ArrayList();
		currentFrame=1;
		
		synchronized(this){
			animTime=0;
			totalDuration=0;
		}
	}
	
	public synchronized void addFrame(Image image,long duration){
		totalDuration+=duration;
		frames.add(new AnimeFrame(image,totalDuration));
	}
	
	public synchronized void update(long elapsedTime){
		if(frames.size()>1){
			animTime+=elapsedTime;
				if(animTime>=totalDuration){
					animTime=animTime%totalDuration;
					currentFrame=0;
				}
				while(animTime>getFrame(currentFrame).endTime){
					currentFrame++;
				}
		}
	}
	
	
	public synchronized Image getImage(){
		if(frames.size()==0){
			return null;
		}else{
			return getFrame(currentFrame).image;
		}
	}
	
	public AnimeFrame getFrame(int i){
		return (AnimeFrame) frames.get(i);
	}
	public class AnimeFrame{
		
		private Image image;
		private long endTime;
		
		public AnimeFrame(Image a,long b){
			
			this.image=a;
			this.endTime=b;
			
		}
		
	}
	
	
}
