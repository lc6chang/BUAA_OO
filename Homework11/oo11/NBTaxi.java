package oo11;

import java.awt.Point;
import java.util.ListIterator;
import java.util.Vector;

public class NBTaxi extends Taxi{
	/*Overview:这个是可追踪出租车类，继承了普通的Taxi类，并且重写了run方法*/
	private Log cachedlog;
	private Vector<Log> logs;
	public NBTaxi(Global g,CarMap m,TaxiGUI gu,Light l,LightThread li,int i) {
		/**@REQUIRES:Global g,CarMap m,TaxiGUI gu,Light l,LightThread li,int i;
		 * 			 0<=i<100;
		   @MODIFIES:this,super;
		   @EFFECTS:super.global==g&&super.map==m&&super.gui==gu&&super.light==l&&super.lighthread==li;
		   			super.id==i&&super.credit==0&&super.state==2&&super.location==一个地图范围内的随机位置&&super.lastlocation==null;	
		   			this.cachedlog==new Log();
		   			this.logs==new Vector<Log>();	   			
		 */
		super(g,m,gu,l,li,i);
		cachedlog=new Log();
		logs=new Vector<Log>();
	}
    /*@repOk.
    check:1.super.repOk();
    	  2.this.cachedlog!=null&&this.cachedlog.repOk();
    	  3.this.logs!=null;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(cachedlog==null||logs==null) {
    		return false;
    	}
    	return super.repOk()&&cachedlog.repOk();
	}
	public void setcachedlog(Log l) {
		/**@REQUIRES:Log l;
		   @MODIFIES:this.cachedlog;
		   @EFFECTS:this.cachedlog==l;
		 */
		cachedlog=l;
	}
	public ListIterator<Log> listiterator(){
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==logs.listIterator();
		 */
		return logs.listIterator();
	}
	public void setgui() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:设置gui，将出租车种类设置为1;
		 */
		gui.SetTaxiType(id,1);
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
					///////////////设置taxilog///////////////
					cachedlog.addmoveto(location);
					logs.addElement(cachedlog);
					//////////////////////////////
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
				///////////////设置taxilog////////////////////////
				cachedlog.addmoveto(location);
				//////////////////////////////
				gui.SetTaxiStatus(id,location,1);
				Point nextlocation0=GetPath.NBgetpoint(map,location,targetpoint);
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
					///////////////设置taxilog////////////////////////
					cachedlog.addpickup(location);
					//////////////////////////////
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
				///////////////设置taxilog////////////////////////
				cachedlog.addpickup(location);
				//////////////////////////////
				gui.SetTaxiStatus(id,location,1);
				Point nextlocation1=GetPath.NBgetpoint(map,location,startpoint);
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
				Point newlocation=GetRandom.NBrandomove(map,location);
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
