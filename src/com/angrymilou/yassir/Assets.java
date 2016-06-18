package com.angrymilou.yassir;

import yassir.framework.Game;
import yassir.framework.Image;
import yassir.framework.Music;
import yassir.framework.Sound;

public class Assets {

	public static Image splash,bg,map,ammo_box,dog_dizzy,menu,mission,
	button_text,run1,run2,run3,run4,run5,run6,jump,die,defeat,button_orange,button_green,button_blue,button_yellow
	,levelStatut,barrelBlack,mine1,mine2,explose1,explose2,explose3,explose4,explose5,explose6,explose7,explose8,
	explose9,blue_weapon,red_weapon,boxing1,boxing2,boxing3,boxing4,bird,iconsYellow,score,numbers,start,help,about
	,text,helpScreen,numeroLevel,volumeOnOff,victory,arrowLeft,arrowRight,comingSoon,universes,ribbon;
	
	public static Music menuMusic=null,gameS=null,barrelS;
	public static Sound point,mineS;
	
	
	public static void loadGame(Game game){
		gameS=game.getAudio().createMusic("sound/game1.ogg");
		gameS.setLooping(true);
		gameS.setVolume(0.85f);
		gameS.play();
	}
	
	public static void collideS(Game game){
		barrelS=game.getAudio().createMusic("sound/ninja_kill.ogg");
		barrelS.setVolume(0.85f);
		barrelS.play();
	}
}
