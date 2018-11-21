package oo5;

public class Many_Schedule extends Thread{
	private ALL all;
	private RequestList requestlist;
	private Floor floor_light;
	private RequestList[] counter;
	private Elevator[] ele;
	public Many_Schedule(ALL a,RequestList r) {
		all=a;
		requestlist=r;
		floor_light=new Floor();
		counter=new RequestList[3];
		ele=new Elevator[3];
		counter[0]=new RequestList();
		ele[0]=new Elevator(1,all,counter[0],floor_light);
		counter[1]=new RequestList();
		ele[1]=new Elevator(2,all,counter[1],floor_light);
		counter[2]=new RequestList();
		ele[2]=new Elevator(3,all,counter[2],floor_light);
	}
	
	public boolean issame(Request a) {
		if(a.outype()==1) {
			if(a.outdire()==1) {
				if(floor_light.out_up(a.outfloor())==1) {
					return true;
				}
				else {
					return false;
				}
			}
			if(floor_light.out_down(a.outfloor())==1) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(ele[a.outid()-1].outlight(a.outfloor())==1) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	public boolean override_fr(Request a) {
		///////////首先查看是否可以捎带//////////////////
		int min_i=3;
		int min_total=9999999;
		for(int i=0;i<3;i++) {
			if(a.outdire()!=ele[i].outstate()) {
				continue;
			}
			if(a.outdire()==1&&a.outfloor()>ele[i].outnow_floor()&&a.outfloor()<=ele[i].outarget_floor()) {
				if(ele[i].outotal()<min_total) {
					min_total=ele[i].outotal();
					min_i=i;
				}
			}
			else if(a.outdire()==2&&a.outfloor()<ele[i].outnow_floor()&&a.outfloor()>=ele[i].outarget_floor()) {
				if(ele[i].outotal()<min_total) {
					min_total=ele[i].outotal();
					min_i=i;
				}
			}
		}
		if(min_i==3) {
			return false;
		}
		else {
			counter[min_i].add(a);
			if(a.outdire()==1) {
				floor_light.on_up(a.outfloor());
			}
			else {
				floor_light.on_down(a.outfloor());
			}
			return true;
		}
	}
	public boolean override_er(Request a) {
		int min_i=3;
		for(int i=0;i<3;i++) {
			if(a.outid()!=i+1) {
				continue;
			}
			if(ele[i].outstate()==1&&a.outfloor()>ele[i].outnow_floor()&&ele[i].outnow_floor()!=ele[i].outarget_floor()) {
				min_i=i;
			}
			else if(ele[i].outstate()==2&&a.outfloor()<ele[i].outnow_floor()&&ele[i].outnow_floor()!=ele[i].outarget_floor()) {
				min_i=i;
			}
		}
		if(min_i==3) {
			return false;
		}
		else {
			counter[min_i].add(a);
			ele[min_i].turnon(a.outfloor());
			return true;
		}
	}
	public boolean use_wait(int k) {
		int min_i=3;
		int min_total=999999;
		Request a=requestlist.search(k);
		for(int i=0;i<3;i++) {
			if(ele[i].outstate()!=3) {
				continue;
			}
			if(a.outype()==0&&a.outid()!=i+1) {
				continue;
			}
			if(ele[i].outotal()<min_total) {
				min_i=i;
				min_total=ele[i].outotal();
			}
		}
		if(min_i!=3) {
			counter[min_i].add(a);
			requestlist.remove(k);
			if(a.outype()==1) {
				if(a.outdire()==1) {
					floor_light.on_up(a.outfloor());
				}
				else {
					floor_light.on_down(a.outfloor());
				}
			}
			else if(a.outype()==0) {
				ele[min_i].turnon(a.outfloor());
			}
			int moveway=0;
			if(a.outfloor()>ele[min_i].outnow_floor()) {
				moveway=1;
			}
			else if(a.outfloor()<ele[min_i].outnow_floor()) {
				moveway=2;
			}
			ele[min_i].move(a.outfloor(),moveway);
			return true;
		}
		return false;
	}
	public void override() {
		for(int i=0;i<requestlist.len();i++) {
			Request a=requestlist.search(i);
			if(issame(a)) {
				all.addstring("#"+System.currentTimeMillis()+":SAME"+"["+a.outword()+","+String.format("%.1f",a.outime()/1000)+"]\r\n");
				requestlist.remove(i);
				i--;
				continue;
			}
			if(a.outype()==1) {
				if(override_fr(a)) {
					requestlist.remove(i);
					i--;
					continue;
				}
				if(use_wait(i)) {
					i--;
				}
			}
			else {
				if(override_er(a)) {
					requestlist.remove(i);
					i--;
					continue;
				}
				if(use_wait(i)) {
					i--;
				}
			}
		}
	}
	public void run() {
		ele[0].start();
		ele[1].start();
		ele[2].start();
		while(all.outendflag()==0||requestlist.len()!=0) {
			if(requestlist.len()!=0) {
				override();
			}
			try {
				sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		all.shc_end();
		if(ele[0].isAlive()) {
			try {
				ele[0].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(ele[1].isAlive()) {
			try {
				ele[1].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(ele[2].isAlive()) {
			try {
				ele[2].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
