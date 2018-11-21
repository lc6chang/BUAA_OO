package oo6;

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
	
}