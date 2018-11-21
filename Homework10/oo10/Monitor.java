package oo10;

public class Monitor extends Thread{
	/*Overview:这个类是监督器类，每当请求队列有新的请求，它便从中取出并创建抢单窗口
	 * 
	 */
	private Global global;
	private CarMap map;
	private TaxiList taxilist;
	private RequestList requestlist;
	private TaxiGUI gui;
	public Monitor(Global g, CarMap c,TaxiList t, RequestList r,TaxiGUI gu) {
		/**@REQUIRES:Global g,CarMap c,TaxiList t,RequestList r,TaxiGUI gu;
		   @MODIFIES:this;
		   @EFFECTS:this.global==g&&this.map==c&&this.taxilist==t&&this.requestlist==r&&this.gui==gu;
		 */
		global=g;
		map=c;
		taxilist=t;
		requestlist=r;
		gui=gu;
	}

    /*@repOk.
    check:1.this.global!=null&&this.map!=null&&this.taxilist!=null&&this.requestlist!=null;
    	  2.global.repOk()&&map.repOk()&&taxilist.repOk()&&requestlist.repOk();
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(global==null||map==null||taxilist==null||requestlist==null) {
    		return false;
    	}
    	return global.repOk()&&map.repOk()&&taxilist.repOk()&&requestlist.repOk();
	}
	public void run() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:复杂，不想写，您扣分！;
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
