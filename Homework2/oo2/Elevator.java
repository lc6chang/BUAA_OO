package oo2;

public class Elevator {
	private int floor;
	private int move;
	private double time;
	
	public Elevator() {
		floor=1;
		move=0;
		time=0;
	}
	
	public double movetime(Request a) {
		return Math.abs(a.outfloor()-floor)*0.5;
	}
	
	public int moveway(Request a) {
		if(a.outfloor()>floor) {
			return 1;
		}
		else if(a.outfloor()==floor) {
			return 0;
		}
		else {
			return 2;
		}
	}
	public double startime(Request a) {
		if(a.outTime()<time) {
			return time;
		}
		else {
			return a.outTime();
		}
	}
	public void UpandDown(int f,int m,double t1,double t2) {
		floor=f;
		move=m;
		time=t1+t2;
	}
	
	public void output() {
		if(move==0) {
			System.out.println("("+floor+",STILL,"+String.format("%.1f", time+1)+")");
		}
		else if(move==1) {
			System.out.println("("+floor+",UP,"+String.format("%.1f", time)+")");
		}
		else {
			System.out.println("("+floor+",DOWN,"+String.format("%.1f", time)+")");
		}
	}
	
	public void stop() {
		time=time+1;
	}
	
	public int outfloor() {
		return floor;
	}
	
	public int outmove() {
		return move;
	}
	
	public double outtime() {
		return time;
	}
	
}
