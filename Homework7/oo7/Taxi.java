package oo7;

import java.awt.Point;
import java.util.ArrayList;

public class Taxi extends Thread{
	private Global global;
	private CarMap map;
	private ShortestPath path;
	private TaxiGUI gui;
	private int id;
	private int credit;
	private int state;////0为停止服务，1为等待接单，2为接单状态，3为服务状态，注意！和gui里面的不同//////////////////////
	private Point location;
	private Point startpoint;
	private Point targetpoint;
	public Taxi(Global g,CarMap m,ShortestPath p,TaxiGUI gu,int i) {
		global=g;
		map=m;
		path=p;
		gui=gu;
		id=i;
		credit=0;
		state=1;
		location=GetRandom.randomlocation();
	}
	public int getid() {
		return id;
	}
	public int getcredit() {
		return credit;
	}
	public int getstate() {
		return state;
	}
	public Point getlocation() {
		return location;
	}
	synchronized public void addcredit(int i) {
		credit=credit+i;
	}
	public void ordertaking(Point start,Point target) {
		credit=credit+3;
		startpoint=start;
		targetpoint=target;
		state=2;
	}
	public void run() {
		int timerecorder=0;
		while(global.getend()==0) {
			switch(state) {
			case 0:
				gui.SetTaxiStatus(id,location,0);
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(state==0) {
					state=1;
				}
				break;
			case 1:
				gui.SetTaxiStatus(id,location,2);
				Point newlocation=GetRandom.randomove(map,location);
				try {
					sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				location=newlocation;
				timerecorder+=1;
				if(timerecorder==100&&state==1) {
					state=0;
					timerecorder=0;
				}
				break;
			case 2:
				ArrayList<Point> startpath=path.get(location,startpoint);
				for(int i=startpath.size()-1;i>=0;i--) {
					String s1="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]->";
					global.add(s1,id);
					gui.SetTaxiStatus(id,location,1);
					Point nextlocation=startpath.get(i);
					try {
						sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					location=nextlocation;
				}
				String s1="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]\r\n到达乘客位置的时刻：";
				s1=s1+System.currentTimeMillis()/100+"(100ms)\r\n乘客位置坐标："+"("+location.x+","+location.y+")\r\n到达目的地经过的分支点及到达时刻(100ms)：";
				global.add(s1,id);
				gui.SetTaxiStatus(id,location,0);
				state=0;
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				state=3;
				break;
			case 3:
				ArrayList<Point> targetpath=path.get(location,targetpoint);
				for(int i=targetpath.size()-1;i>=0;i--) {
					String s2="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]->";
					global.add(s2,id);
					gui.SetTaxiStatus(id,location,1);
					Point nextlocation=targetpath.get(i);
					try {
						sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					location=nextlocation;
				}
				String s2="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]\r\n到达目的地时刻：";
				s2=s2+System.currentTimeMillis()/100+"(100ms)\r\n到达目的地坐标："+"("+location.x+","+location.y+")\r\n";
				global.add(s2,id);
				global.write(id);
				gui.SetTaxiStatus(id,location,0);
				state=0;
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				state=1;
				timerecorder=0;
				break;
			}				
		}
	}
}
