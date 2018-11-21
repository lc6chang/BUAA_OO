package oo11;

public class RequestList {
	/*Overview:这个类是请求队列类，对乘客请求进行管理，提供了添加、判断同质等方法
	 * 
	 */
	private Request[] requestlist;
	private int head;
	private int tail;
	public RequestList() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:this.requestlist==new Request[300];
		   			this.head==0;
		   			this.tail==0;
		 */
		requestlist=new Request[300];
		head=0;
		tail=0;
	}
    /*@repOk.
    check:1.this.requestlist!=null;
    	  2.0<=this.head<=this.tail<=300;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	if(requestlist==null) {
    		return false;
    	}
    	if(head<0||head>300) {
    		return false;
    	}
    	if(tail<0||tail>300) {
    		return false;
    	}
    	if(head>tail) {
    		return false;
    	}
    	return true;
	}
	public void searchadd(Request a) {
		/**@REQUIRES:Request a;a!=null;
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
		/**@REQUIRES:Request a;a!=null;
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
		   @EFFECTS:this.head==\old(this.head)+1;
		 */
		head=head+i;
	}
	public Request getrequest(int i) {
		/**@REQUIRES:int i;
		 			 0<=i<300;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.requestlist[i];
		 */
		return requestlist[i];
	}
	public int gethead() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.head;
		 */
		return head;
	}
	public int getail() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.tail;
		 */
		return tail;
	}
}
