package oo5;

public class Floor {
	private int[] up_light;
	private int[] down_light;
	
	public Floor() {
		up_light=new int[21];
		down_light=new int[21];
	}
	
	public void on_up(int i) {
		up_light[i]=1;
	}
	public void off_up(int i) {
		up_light[i]=0;
	}
	public void on_down(int i) {
		down_light[i]=1;
	}
	public void off_down(int i) {
		down_light[i]=0;
	}
	public int out_up(int i) {
		return up_light[i];
	}
	public int out_down(int i) {
		return down_light[i];
	}
	
	
}
