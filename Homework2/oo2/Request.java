package oo2;

public class Request {
	private int type;
	private int floor;
	private int move;
	private double Time;
	private int flag;
	
	
	public Request (int t,int f,int m,double Ti) {
		type=t;
		floor=f;
		move=m;
		Time=Ti;
		flag=1;
	}
	public void unflag() {
		flag=0;
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
}
