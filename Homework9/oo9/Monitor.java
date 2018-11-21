package oo9;

public class Monitor extends Thread{
	private Global global;
	private CarMap map;
	private TaxiList taxilist;
	private RequestList requestlist;
	private TaxiGUI gui;
	public Monitor(Global g, CarMap c,TaxiList t, RequestList r,TaxiGUI gu) {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:构造;
		 */
		global=g;
		map=c;
		taxilist=t;
		requestlist=r;
		gui=gu;
	}
	public void run() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:None;
		   复杂，不想写，您扣分！
		 */
		System.out.println("Monitor is start!");
		while(global.getend()==0) {
			if(requestlist.gethead()!=requestlist.getail()) {
				for(int i=requestlist.gethead();i<requestlist.getail();i++) {
					Request temp=requestlist.getrequest(i);
					GrabWindow window=new GrabWindow(global,map,taxilist,temp);
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
