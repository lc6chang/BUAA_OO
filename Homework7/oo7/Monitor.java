package oo7;

public class Monitor extends Thread{
	private Global global;
	private ShortestPath path;
	private TaxiList taxilist;
	private RequestList requestlist;
	private TaxiGUI gui;
	public Monitor(Global g, ShortestPath p,TaxiList t, RequestList r,TaxiGUI gu) {
		global=g;
		path=p;
		taxilist=t;
		requestlist=r;
		gui=gu;
	}
	public void run() {
		System.out.println("Monitor is start!");
		while(global.getend()==0) {
			if(requestlist.gethead()!=requestlist.getail()) {
				for(int i=requestlist.gethead();i<requestlist.getail();i++) {
					Request temp=requestlist.getrequest(i);
					GrabWindow window=new GrabWindow(global,path,taxilist,temp);
					window.start();
					gui.RequestTaxi(temp.getstart(),temp.getend());
					requestlist.remove(1);
				}

			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Monitor is end!");
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		global.writend();
		System.out.println("Log has been wrote in!");
	}
}
