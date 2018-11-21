package oo9;

public class RequestList {
	private Request[] requestlist;
	private int head;
	private int tail;
	public RequestList() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:构造;
		 */
		requestlist=new Request[300];
		head=0;
		tail=0;
	}
	public void searchadd(Request a) {
		/**@REQUIRES:Request a;
		   @MODIFIES:this.requestlist;
		   @EFFECTS:同质请求==>System.out&&return;
		   			!同质请求==>调用add(a);
		 */
		for(int i=tail-1;i>=0;i--) {
			if(requestlist[i].getime()<a.getime()) {
				break;
			}
			if(requestlist[i].getstart().equals(a.getstart())&&requestlist[i].getend().equals(a.getend())) {
				System.out.println("Same Request!");
				return;
			}
		}
		add(a);
	}
	public void add(Request a) {
		/**@REQUIRES:Request a;
		   @MODIFIES:this.requestlist;
		   @EFFECTS:已经输入过300条正常乘客请求==>System.out&&return;
		   			已经输入过小于300条正常乘客请求==>requestlist.add(a);
		 */
		if(tail==300) {
			System.out.println("Too many request!");
			return;
		}
		requestlist[tail++]=a;
	}
	public void remove(int i) {
		/**@REQUIRES:int i;
		   @MODIFIES:this.head;
		   @EFFECTS:head++;
		 */
		head=head+i;
	}
	public Request getrequest(int i) {
		/**@REQUIRES:int i;
		 			 0<=i<300;
		   @MODIFIES:None;
		   @EFFECTS:\result=requestlist[i];
		 */
		return requestlist[i];
	}
	public int gethead() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=head;
		 */
		return head;
	}
	public int getail() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=tail;
		 */
		return tail;
	}
}
