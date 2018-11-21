package oo13;
import java.util.ArrayList;

public class RequestList {
	/*Overview:这是请求队列类，储存了请求队列，提供了增加请求、查找请求等方法*/
	
	private ArrayList<Request> Relist;
   
	public RequestList() {
		/**@REQUIRES:None;
		   @MODIFIES:this.Relist;
		   @EFFECTS:this.Relist==new ArrayList<Request>();
		 */
		Relist=new ArrayList<Request>();
	}
	
    /*@repOk.
    check:None;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result == invariant(this)
        */
    	return true;
	}
	
	public int len() {
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.Relist.size();
		 */
		return Relist.size();
	}
	
	public void add(Request a) {
		/**@REQUIRES:Request a;a!=null;
		   @MODIFIES:this.Relist;
		   @EFFECTS:this.Relist.size()==\old(this.Relist.size())+1;
		   			this.Relist.contains(a);
		 */
		Relist.add(a);
	}
	
	public Request search(int i) {
		/**@REQUIRES:int i;0<=i<this.Relist.size();
		   @MODIFIES:None;
		   @EFFECTS:\result==this.Relist[i];
		 */
		return Relist.get(i);
	}
	
	public Request last() {
		/**@REQUIRES:this.Relist.size()>0;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.Relist[this.Relist.size()-1];
		 */
		return Relist.get(Relist.size()-1);
	}
	public void moveto(int i,int j) {
		/**@REQUIRES:int i,j;0<=i,j<this.Relist.size();
		   @MODIFIES:this.Relist;
		   @EFFECTS:this.Relist[j]==\old(this.Relist[i]);
		   			\all int k;j<i && j<k<=i==>this.Relist[k]==\old(this.Relist[k-1]);
		   			\all int k;i<j && i<=k<j==>this.Relist[k]==\old(this.Relist[k+1]);
		 */
		Request a=Relist.get(i);
		Relist.remove(i);
		Relist.add(j, a);
	}
	
}
