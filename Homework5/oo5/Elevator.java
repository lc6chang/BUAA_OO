package oo5;

public class Elevator extends Thread{
	private int id;
	private int state;////0为Still,1为Up,2为Down,3为Wait////////
	private int now_floor;
	private int target_floor;
	private int total;
	private ALL all;
	private RequestList counter;
	private Floor floor_light;
	private int[] ele_light;
	
	public Elevator(int i,ALL a,RequestList c,Floor f) {
		id=i;
		state=3;
		now_floor=1;
		target_floor=1;
		total=0;
		all=a;
		counter=c;
		floor_light=f;
		ele_light=new int[21];
	}
	public int outid() {
		return id;
	}
	public int outstate() {
		return state;
	}
	public int outnow_floor() {
		return now_floor;
	}
	public int outarget_floor() {
		return target_floor;
	}
	public int outotal() {
		return total;
	}
	public int outlight(int i) {
		return ele_light[i];
	}
	public void move(int floor,int moveway) {
		target_floor=floor;
		state=moveway;
	}
	public void turnon(int i) {
		ele_light[i]=1;
	}
	public void turnoff(Request a) {
		if(a.outype()==1) {
			if(a.outdire()==1) {
				floor_light.off_up(a.outfloor());
			}
			else {
				floor_light.off_down(a.outfloor());
			}
		}
		else {
			ele_light[a.outfloor()]=0;
		}
	}
	
	public void run() {
		while(all.outshcflag()==0||state!=3) {
		if(state!=3) {
///////////////若主请求控制Still/////////////////////////////////////////////
		if(target_floor==now_floor) {
			long oldtime=System.currentTimeMillis();
			while(System.currentTimeMillis()-oldtime<6000) {
				;
			}
			Request a=counter.search(0);
			turnoff(a);
			long t=System.currentTimeMillis();
			all.addstring(t+":["+a.outword()+","+String.format("%.1f",a.outime()/1000)+"]/(#"+id+","+now_floor+",STILL,"+total+","+String.format("%.1f",(t-all.outbasetime())/1000)+")\r\n");
			counter.remove(0);
			state=3;
		}
/////////////////若控制上行////////////////////////////
		else if(target_floor>now_floor){
			for(int i=now_floor+1;i<=target_floor;i++) {
				total++;
				long oldtime=System.currentTimeMillis();
				while(System.currentTimeMillis()-oldtime<3000) {
					;
				}
				now_floor=i;
				long t=System.currentTimeMillis();
				int flag=0;
				Request[] li=new Request[3];
				int li_len=0;
				for(int j=0;j<counter.len();j++) {
					if(counter.search(j).outfloor()!=now_floor) {
						continue;
					}
					else {
						Request a=counter.search(j);
						all.addstring(t+":["+a.outword()+","+String.format("%.1f",a.outime()/1000)+"]/(#"+id+","+now_floor+",UP,"+total+","+String.format("%.1f",(t-all.outbasetime())/1000)+")\r\n");
						li[li_len++]=counter.remove(j);
						flag=1;
						j--;
					}
				}
				if(flag==1) {
					oldtime=System.currentTimeMillis();
					while(System.currentTimeMillis()-oldtime<6000) {
						;
					}
					for(int k=0;k<li_len;k++) {
						turnoff(li[k]);
					}
				}
				if(i==target_floor) {
					if(counter.len()!=0) {
						Request c=counter.search(0);
						int moveway=0;
						if(c.outfloor()>outnow_floor()) {
							moveway=1;
						}
						else if(c.outfloor()<outnow_floor()) {
							moveway=2;
						}
						move(c.outfloor(),moveway);
					}
					else {
						state=3;
					}
				}
			}
		}
/////////////////////若主请求控制下行///////////////////////////////////
		else if(target_floor<now_floor){
			for(int i=now_floor-1;i>=target_floor;i--) {
				total++;
				long oldtime=System.currentTimeMillis();
				while(System.currentTimeMillis()-oldtime<3000) {
					;
				}
				now_floor=i;
				long t=System.currentTimeMillis();
				int flag=0;
				Request[] li=new Request[3];
				int li_len=0;
				for(int j=0;j<counter.len();j++) {
					if(counter.search(j).outfloor()!=now_floor) {
						continue;
					}
					else {
						Request a=counter.search(j);
						all.addstring(t+":["+a.outword()+","+String.format("%.1f",a.outime()/1000)+"]/(#"+id+","+now_floor+",DOWN,"+total+","+String.format("%.1f",(t-all.outbasetime())/1000)+")\r\n");
						li[li_len++]=counter.remove(j);
						flag=1;
						j--;
					}
				}
				if(flag==1) {
					oldtime=System.currentTimeMillis();
					while(System.currentTimeMillis()-oldtime<6000) {
						;
					}
					for(int k=0;k<li_len;k++) {
						turnoff(li[k]);
					}
				}
				if(i==target_floor) {
					if(counter.len()!=0) {
						Request c=counter.search(0);
						int moveway=0;
						if(c.outfloor()>outnow_floor()) {
							moveway=1;
						}
						else if(c.outfloor()<outnow_floor()) {
							moveway=2;
						}
						move(c.outfloor(),moveway);
					}
					else {
						state=3;
					}
				}
			}
		}
		
		}
		try {
			sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		}
	}
}
