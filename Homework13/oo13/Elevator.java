package oo13;

public class Elevator implements ELE_MOVE{
	/*Overview：这是一个电梯类，储存了所在楼层，运动方向，时间等信息*/
	
	private int floor;
	private int move;
	private double time;
	
	public Elevator() {
		/**@REQUIRES:None;
		   @MODIFIES:this;
		   @EFFECTS:this.floor==1 && this.move==0 && this.time==0;
		 */
		floor=1;
		move=0;
		time=0;
	}
	
    /*@repOk.
    check:1<=this.floor<=10 && 0<=this.move<=2 && this.time>=0;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result == invariant(this)
        */
    	if(floor<1) {
    		return false;
    	}
    	if(floor>10) {
    		return false;
    	}
    	if(move<0) {
    		return false;
    	}
    	if(move>2) {
    		return false;
    	}
    	if(time<0) {
    		return false;
    	}
    	return true;
	}
    
	public double movetime(Request a) {
		/**@REQUIRES:Request a;a!=null;
		   @MODIFIES:None;
		   @EFFECTS:\result==Math.abs(a.floor-this.floor)*0.5;
		 */
		return Math.abs(a.outfloor()-floor)*0.5;
	}
	
	public int moveway(Request a) {
		/**@REQUIRES:Request a;a!=null
		   @MODIFIES:None;
		   @EFFECTS:a.floor>this.floor==>\result==1;
		   			a.floor==this.floor==>\result==0;
		   			a.floor<this.floor==>\result==2;
		 */
		if(a.outfloor()>floor) {
			return 1;
		}
		else if(a.outfloor()==floor) {
			return 0;
		}
		else {
			return 2;
		}
	}
	public double startime(Request a) {
		/**@REQUIRES:Request a;a!=null;
		   @MODIFIES:None;
		   @EFFECTS:a.Time<this.time==>\result==this.time;
		   			a.Time>=this.time==>\result==a.Time;
		 */
		if(a.outTime()<time) {
			return time;
		}
		else {
			return a.outTime();
		}
	}
	public void UpandDown(int f,int m,double t1,double t2) {
		/**@REQUIRES:int f;1<=f<=10;
		 * 			 int m;m==0||m==1||m==2;
		 * 			 double t1,t2;t1,t2>=0;
		   @MODIFIES:this;
		   @EFFECTS:this.floor==f && this.move==m && this.time==t1+t2;
		 */
		floor=f;
		move=m;
		time=t1+t2;
	}
	public String toString() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:this.move==0 ==> \result=="("+this.floor+",STILL,"+String.format("%.1f", this.time+1)+")";
		   			this.move==1 ==> \result=="("+this.floor+",UP,"+String.format("%.1f", this.time)+")";
		   			this.move!=0 && this.move!=1 ==> \result== "("+this.floor+",DOWN,"+String.format("%.1f", this.time)+")";
		 */
		if(move==0) {
			return "("+floor+",STILL,"+String.format("%.1f", time+1)+")";
		}
		else if(move==1) {
			return "("+floor+",UP,"+String.format("%.1f", time)+")";
		}
		else {
			return "("+floor+",DOWN,"+String.format("%.1f", time)+")";
		}
	}
	
	public void stop() {
		/**@REQUIRES:None;
		   @MODIFIES:this.time;
		   @EFFECTS:this.time==\old(this.time)+1;
		 */
		time=time+1;
	}
	
	public int outfloor() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.floor;
		 */
		return floor;
	}
	
	public int outmove() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.move;
		 */
		return move;
	}
	
	public double outtime() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.time;
		 */
		return time;
	}
	
}
