package oo13;

public class Request {
	/*Overview:这是请求类，储存了请求的相关信息*/
	
	
	private int type;
	private int floor;
	private int move;
	private double Time;
	private int flag;
	private String word;
	
	
	public Request (int t,int f,int m,double Ti,String w) {
		/**@REQUIRES:int t;t==0||t==1;
		 * 			 int f;1<=f<=10;
		 * 			 int m;t==0==>m==0;t==1==>m==1||m==2;f==1==>m!=2 && f==10==>m!=1;
		 * 			 double Ti; Ti>=0;
		 * 			 String w;w!=null;
		 * 			 w的格式为"[FR,floor,UP/DOWN,time]"或"[ER,floor,time]",且内容必须要与前面输入的参数相符;
		 * 			 (w的内容会作为输出中的指令部分直接输出，请严格按照Readme中的规定输入，不然输出就会出错啦！使用自然语言描述请见谅....)
		   @MODIFIES:this;
		   @EFFECTS:this.type==t;this.floor=f;this.move==m;this.Time==Ti;this.flag==1;this.word==w;
		 */
		type=t;
		floor=f;
		move=m;
		Time=Ti;
		flag=1;
		word=w;
	}
	
    /*@repOk.
    check:1.this.type==0||this.type==1;
    	  2.1<=this.floor<=10;
    	  3.this.type==0 ==> this.move==0 && this.type==1 ==> this.move==1||this.move==2;
    	  4.this.floor==1 ==> this.move!=2 && this.floor==10 ==> this.move!=1;
    	  5.this.Time>=0;
    	  6.this.word!=null;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result == invariant(this)
        */
    	if(type!=0&&type!=1) {
    		return false;
    	}
    	if(floor<1||floor>10) {
    		return false;
    	}
    	if(type==0&&move!=0) {
    		return false;
    	}
    	if(type==1&&move!=1&&move!=2) {
    		return false;
    	}
    	if(floor==1&&move==2) {
    		return false;
    	}
    	if(floor==10&&move==1) {
    		return false;
    	}
    	if(Time<0) {
    		return false;
    	}
    	if(word==null) {
    		return false;
    	}
    	return true;
	}
    
	public void unflag() {
		/**@REQUIRES:None;
		   @MODIFIES:this.flag;
		   @EFFECTS:this.flag==0;
		 */
		flag=0;
	}
	public void flag2() {
		/**@REQUIRES:None;
		   @MODIFIES:this.flag;
		   @EFFECTS:this.flag==2;
		 */
		flag=2;
	}
	public int issame(Request a) {
		/**@REQUIRES:Request a;a!=null;
		   @MODIFIES:None;
		   @EFFECTS:this.type==a.type && this.floor==a.floor && this.move==a.move ==> \result==1;
		   			!(this.type==a.type && this.floor==a.floor && this.move==a.move)==> \result==0;
		 */
		 if((type==a.outtype())&&(floor==a.outfloor())&&(move==a.outmove())) {
			 return 1;
		 }
		 else {
			 return 0;
		 }
		
	}
	public int outtype() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.type;
		 */
		return type;
	}
	public int outfloor() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.floor;
		 */
		return floor;
	}
	public double outTime() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.Time;
		 */
		return Time;
	}
	public int outmove() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.move;
		 */
		return move;
	}
	public int outflag() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.flag;
		 */
		return flag;
	}
	public String outword() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.word;
		 */
		return word;
	}
}
