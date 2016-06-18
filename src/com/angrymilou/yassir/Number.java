package com.angrymilou.yassir;

public class Number {

	private int w,x,y,z;
	
	public void setAll(int f){
		w=f/1000;
		x=(f-w*1000)/100;
		y=(f-w*1000-x*100)/10;
		z=f-w*1000-x*100-y*10;
	}
	
	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
}
