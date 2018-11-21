package oo2;
import java.util.ArrayList;

public class RequestList {
	private ArrayList<Request> Relist;
	
	public RequestList() {
		Relist=new ArrayList<Request>();
	}
	
	public int len() {
		return Relist.size();
	}
	
	public void add(Request a) {
		Relist.add(a);
	}
	
	public Request search(int i) {
		return Relist.get(i);
	}
	
	public Request last() {
		return Relist.get(Relist.size()-1);
	}
	public void moveto(int i,int j) {
		Request a=Relist.get(i);
		Relist.remove(i);
		Relist.add(j, a);
	}
	
}
