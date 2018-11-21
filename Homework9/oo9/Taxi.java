package oo9;

import java.awt.Point;

public class Taxi extends Thread{
	private Global global;
	private CarMap map;
	private TaxiGUI gui;
	private int id;
	private int credit;
	private int state;////服务状态取值为0，接单状态取值为1，等待服务取值为2，停止状态取值为3，注意！和gui里面的不同//////////////////////
	private Point location;
	private Point lastlocation;
	private Point startpoint;
	private Point targetpoint;
	private long systemtime;
	public Taxi(Global g,CarMap m,TaxiGUI gu,int i) {
		/**@REQUIRES:Global g,CarMap m,TaxiGUI gu,int i;
		   @MODIFIES:this;
		   @EFFECTS:构造;
		 */
		global=g;
		map=m;
		gui=gu;
		id=i;
		credit=0;
		state=2;
		location=GetRandom.randomlocation();
		lastlocation=null;
	}
	public void setime(long time) {
		/**@REQUIRES:long time;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:systemtime=time;
		   @THREAD_REQUIRES:\locked(this.time);
		   @THREAD_EFFECTS:None;
		 */
		systemtime=time;
	}
	public long Roundtime(long noptime) {
		/**@REQUIRES:long noptime;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:(nowtime-systemtime)>noptime==>\result=0;
		   			(nowtime-systemtime)<=noptime==>\result=noptime+systemtime-nowtime;
		   			true==>systemtime+=noptime;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		long sleeptime;
		long nowtime=System.currentTimeMillis();
		if((nowtime-systemtime)>noptime) {
			sleeptime=0;
		}
		else {
			sleeptime=noptime+systemtime-nowtime;
		}
		systemtime+=noptime;
		return sleeptime;
	}
	public void setaxi(int sta,int cre,Point loc,Point startp,Point targetp) {
		/**@REQUIRES:int sta,int cre,Point loc,Point startp,Point targetp;
		   @MODIFIES:this;
		   @EFFECTS:state=sta,credit=cre,location=loc,startpoint=startp,targetpoint=targetp;
		   @THREAD_REQUIRES:\locked(this);
		   @THREAD_EFFECTS:None;
		 */
		state=sta;
		credit=cre;
		location=loc;
		startpoint=startp;
		targetpoint=targetp;
	}
	public int getid() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=id;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return id;
	}
	public int getcredit() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=credit;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return credit;
	}
	public int getstate() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=state;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return state;
	}
	public Point getlocation() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=location;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return location;
	}
	synchronized public void addcredit(int i) {
		/**@REQUIRES:int i;
		   @MODIFIES:this.credit;
		   @EFFECTS:credit+=i;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		credit=credit+i;
	}
	public void ordertaking(Point start,Point target) {
		/**@REQUIRES:Point start,target;
		   @MODIFIES:this;
		   @EFFECTS:credit+=3,startpoint=start,targetpoint=target,state=1;
		   @THREAD_REQUIRES:\locked(this);
		   @THREAD_EFFECTS:None;
		 */
		credit=credit+3;
		startpoint=start;
		targetpoint=target;
		state=1;
	}
	public void run() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:.....写不来！扣扣扣！;
		 */
		int timerecorder=0;
		while(global.getend()==0) {
			switch(state) {
			case 0:
				if(location.equals(targetpoint)) {
					String s0="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]\r\n到达目的地时刻：";
					s0=s0+System.currentTimeMillis()/100+"(100ms)\r\n到达目的地坐标："+"("+location.x+","+location.y+")\r\n";
					s0=s0+"------------------------------------------------------\r\n";
					global.add(s0,id);
					global.write(id);
					gui.SetTaxiStatus(id,location,0);
					state=3;
					try {
						sleep(Roundtime(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					state=2;
					timerecorder=0;
					break;
				}
				while(!global.startmove()) {
					;
				}
				String s0="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]->";
				global.add(s0,id);
				gui.SetTaxiStatus(id,location,1);
				Point nextlocation0=GetPath.getpoint(map,location,targetpoint);
				try {
					sleep(Roundtime(400));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				global.endmove();
				try {
					sleep(Roundtime(100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				map.addvolume(location,nextlocation0);
				map.subvolume(lastlocation,location);
				lastlocation=location;
				location=nextlocation0;
				global.subvolumeflag();
				break;
			case 1:
				if(location.equals(startpoint)) {
					String s1="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]\r\n到达乘客位置的时刻：";
					s1=s1+System.currentTimeMillis()/100+"(100ms)\r\n乘客位置坐标："+"("+location.x+","+location.y+")\r\n到达目的地经过的分支点及到达时刻(100ms)：";
					global.add(s1,id);
					gui.SetTaxiStatus(id,location,0);
					state=3;
					try {
						sleep(Roundtime(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					state=0;
					break;
				}
				while(!global.startmove()) {
					;
				}
				String s1="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]->";
				global.add(s1,id);
				gui.SetTaxiStatus(id,location,1);
				Point nextlocation1=GetPath.getpoint(map,location,startpoint);
				try {
					sleep(Roundtime(400));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				global.endmove();
				try {
					sleep(Roundtime(100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				map.addvolume(location,nextlocation1);
				map.subvolume(lastlocation,location);
				lastlocation=location;
				location=nextlocation1;
				global.subvolumeflag();
				break;
			case 2:
				while(!global.startmove()) {
					;
				}
				gui.SetTaxiStatus(id,location,2);
				Point newlocation=GetRandom.randomove(map,location);
				try {
					sleep(Roundtime(400));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				global.endmove();
				try {
					sleep(Roundtime(100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				map.addvolume(location,newlocation);
				map.subvolume(lastlocation,location);
				lastlocation=location;
				location=newlocation;
				global.subvolumeflag();
				timerecorder+=1;
				if(timerecorder==40&&state==2) {
					state=3;
					timerecorder=0;
				}
				break;
			case 3:
				gui.SetTaxiStatus(id,location,0);
				try {
					sleep(Roundtime(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(state==3) {
					state=2;
				}
				break;
			}				
		}
	}
}
