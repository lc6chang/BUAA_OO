package oo5;

public class Request {
	private int type;
	private int id;
	private int floor;
	private int dire;
	private double time;
	private String word;
	
	
	public Request (int t,int i,int f,int m,double ti,String w) {
		type=t;
		id=i;
		floor=f;
		dire=m;
		time=ti;
		word=w;
	}
	public int outype() {
		return type;
	}
	public int outid() {
		return id;
	}
	public int outfloor() {
		return floor;
	}
	public double outime() {
		return time;
	}
	public int outdire() {
		return dire;
	}
	public String outword() {
		return word;
	}
}
