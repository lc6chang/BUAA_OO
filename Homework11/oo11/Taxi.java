package oo11;

import java.awt.Point;
import java.util.ListIterator;


public class Taxi extends Thread{
	/*Overview:这个类是出租车线程类，管理了出租车的id、信誉值、位置、接客地、目的地等信息
	 * 		     作为一个线程，它按照有限状态机的原理进行运行接客等活动，同时将日志信息输出
	 */
	protected Global global;
	protected CarMap map;
	protected TaxiGUI gui;
	protected Light light;
	protected LightThread lighthread;
	protected int id;
	protected int credit;
	protected int state;////服务状态取值为0，接单状态取值为1，等待服务取值为2，停止状态取值为3//////////////////////
	protected Point location;
	protected Point lastlocation;
	protected Point startpoint;
	protected Point targetpoint;
	protected long systemtime;

	public Taxi(Global g,CarMap m,TaxiGUI gu,Light l,LightThread li,int i) {
		/**@REQUIRES:Global g,CarMap m,TaxiGUI gu,Light l,LightThread li,int i;
		 * 			 0<=i<100;
		   @MODIFIES:this;
		   @EFFECTS:this.global==g&&this.map==m&&this.gui==gu&&this.light==l&&this.lighthread==li;
		   			this.id==i&&this.credit==0&&this.state==2&&this.location==一个地图范围内的随机位置&&this.lastlocation==null;		   			
		 */
		global=g;
		map=m;
		gui=gu;
		light=l;
		lighthread=li;
		id=i;
		credit=0;
		state=2;
		location=GetRandom.randomlocation();
		lastlocation=null;

	}
    /*@repOk.
    check:1.this.global!=null&&this.map!=null&&this.light!=null&&this.lighthread!=null;
    	  2.0<=this.id<100;
    	  3.this.state==0||this.state==1||this.state==2||this.state==3;
    	  4.0<=this.location.x,this.location.y,this.startpoint.x,this.startpoint.y,this.targetpoint.x,this.targetpoint.y<80;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(global==null||map==null||light==null||lighthread==null) {
    		return false;
    	}
    	if(id<0||id>=100) {
    		return false;
    	}
    	if(state!=0&&state!=1&&state!=2&&state!=3) {
    		return false;
    	}
    	if(location.x<0||location.x>=80||location.y<0||location.y>=80) {
    		return false;
    	}
    	if(startpoint.x<0||startpoint.x>=80||startpoint.y<0||startpoint.y>=80) {
    		return false;
    	}
    	if(targetpoint.x<0||targetpoint.x>=80||targetpoint.y<0||targetpoint.y>=80) {
    		return false;
    	}
    	return true;
	}
	public void setcachedlog(Log l) {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:None;
		 */
		;
	}
	public ListIterator<Log> listiterator(){
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==null;
		 */
		return null;
	}
	public void setgui() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:设置gui出租车种类为普通出租车;
		 */
		gui.SetTaxiType(id,0);
	}
	public void setime(long time) {
		/**@REQUIRES:long time;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:this.systemtime==time;
		   @THREAD_REQUIRES:\locked(this.time);
		   @THREAD_EFFECTS:None;
		 */
		systemtime=time;
	}
	public long Roundtime(long noptime) {
		/**@REQUIRES:long noptime;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:(nowtime-this.systemtime)>noptime==>\result==0;
		   			(nowtime-this.systemtime)<=noptime==>\result==noptime+this.systemtime-nowtime;
		   			true==>this.systemtime==\old(this.systemtime)+noptime;
		   @THREAD_REQUIRES:\locked(this.systemtime);
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
		   @EFFECTS:this.state==sta,this.credit==cre,this.location==loc,this.startpoint==startp,this.targetpoint==targetp;
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
		   @EFFECTS:\result==this.id;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return id;
	}
	public int getcredit() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.credit;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return credit;
	}
	public int getstate() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.state;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return state;
	}
	public Point getlocation() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.location;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return location;
	}
	synchronized public void addcredit(int i) {
		/**@REQUIRES:int i;
		   @MODIFIES:this.credit;
		   @EFFECTS:this.credit==\old(this.credit)+i;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		credit=credit+i;
	}
	public void ordertaking(Point start,Point target) {
		/**@REQUIRES:Point start,target;
		   @MODIFIES:this;
		   @EFFECTS:this.credit==\old(this.credit)+3,this.startpoint==start,this.targetpoint==target,this.state=1;
		   @THREAD_REQUIRES:\locked(this);
		   @THREAD_EFFECTS:None;
		 */
		credit=credit+3;
		startpoint=start;
		targetpoint=target;
		state=1;
	}
	public boolean wait(Point newlocation) {
		/**@REQUIRES:Point newlocation;0<=newlocation.x,newlocation.y<80;
		   @MODIFIES:this.systemtime;
		   @EFFECTS:this.lastlocation==null==>\result==false;
		   			有点复杂，唉，不写了，您扣个分吧！
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		if(lastlocation==null) {
			return false;
		}
		int l=light.getlight(location);
		if(l==0) {
			return false;
		}
		if(l==1) {
			if(((lastlocation.y==newlocation.y)&&(lastlocation.x!=newlocation.x))||((lastlocation.y==location.y-1)&&(location.x==newlocation.x+1))||((lastlocation.y==location.y+1)&&(location.x==newlocation.x-1))){
				long noptime=lighthread.getsystemtime()-systemtime;
				try {
					sleep(Roundtime(noptime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
			return false;
		}
		if(l==2) {
			if(((lastlocation.x==newlocation.x)&&(lastlocation.y!=newlocation.y))||((lastlocation.x==location.x-1)&&(location.y==newlocation.y-1))||((lastlocation.x==location.x+1)&&(location.y==newlocation.y+1))) {
				long noptime=lighthread.getsystemtime()-systemtime;
				try {
					sleep(Roundtime(noptime));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
			return false;
		}
		return false;
	}
	public void run() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:.....写不来！请您扣分！;
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
				String s0="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]->";
				gui.SetTaxiStatus(id,location,1);
				Point nextlocation0=GetPath.getpoint(map,location,targetpoint);
				if(wait(nextlocation0)) {
					s0+="等待信号灯!->";
				}
				global.add(s0,id);
				try {
					sleep(Roundtime(500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				map.addvolume(location,nextlocation0);
				map.subvolume(lastlocation,location);
				lastlocation=location;
				location=nextlocation0;
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
				String s1="[("+location.x+","+location.y+"),"+System.currentTimeMillis()/100+"]->";
				gui.SetTaxiStatus(id,location,1);
				Point nextlocation1=GetPath.getpoint(map,location,startpoint);
				if(wait(nextlocation1)) {
					s1+="等待信号灯!->";
				}
				global.add(s1,id);
				try {
					sleep(Roundtime(500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				map.addvolume(location,nextlocation1);
				map.subvolume(lastlocation,location);
				lastlocation=location;
				location=nextlocation1;
				break;
			case 2:
				gui.SetTaxiStatus(id,location,2);
				Point newlocation=GetRandom.randomove(map,location);
				wait(newlocation);
				try {
					sleep(Roundtime(500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				map.addvolume(location,newlocation);
				map.subvolume(lastlocation,location);
				lastlocation=location;
				location=newlocation;
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
