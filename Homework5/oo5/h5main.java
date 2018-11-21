package oo5;

import java.io.IOException;

public class h5main {

	public static void main(String[] args) throws InterruptedException {
		RequestList requestlist=new RequestList();
		ALL all=new ALL();
		Get g=new Get(requestlist,all);
		Many_Schedule sch=new Many_Schedule(all,requestlist);
		g.start();
		sch.start();
		g.join();
		sch.join();
		try {
			all.writein();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
