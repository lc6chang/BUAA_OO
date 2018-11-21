package oo2;

public class Request {
	private int type;
	private int floor;
	private int move;
	private double Time;
	private int flag;
	private String word;
	
	
	public Request (int t,int f,int m,double Ti,String w) {
		type=t;
		floor=f;
		move=m;
		Time=Ti;
		flag=1;
		word=w;
	}
	public void unflag() {
		flag=0;
	}
	public void flag2() {
		flag=2;
	}
	public int issame(Request a) {
		 if((type==a.outtype())&&(floor==a.outfloor())&&(move==a.outmove())) {
			 return 1;
		 }
		 else {
			 return 0;
		 }
		
	}
	public int outtype() {
		return type;
	}
	public int outfloor() {
		return floor;
	}
	public double outTime() {
		return Time;
	}
	public int outmove() {
		return move;
	}
	public int outflag() {
		return flag;
	}
	public String outword() {
		return word;
	}
}
