package oo5;
import java.util.ArrayList;

public class RequestList {
	private ArrayList<Request> Relist;
	int flag;
	
	public RequestList() {
		Relist=new ArrayList<Request>();
		flag=0;
	}
	public void changeflag() {
		flag=1;
	}
	public int outflag() {
		return flag;
	}
	public int len() {
		return Relist.size();
	}
	
	public void add(Request a) {
		Relist.add(a);
	}
	public Request remove(int i) {
		return Relist.remove(i);
	}
	
	public Request search(int i) {
		return Relist.get(i);
	}
	
}
