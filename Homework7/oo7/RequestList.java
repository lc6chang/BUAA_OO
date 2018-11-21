package oo7;

public class RequestList {
	private Request[] requestlist;
	private int head;
	private int tail;
	public RequestList() {
		requestlist=new Request[300];
		head=0;
		tail=0;
	}
	public void searchadd(Request a) {
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
		if(tail==300) {
			System.out.println("Too many request!");
			return;
		}
		requestlist[tail++]=a;
	}
	public void remove(int i) {
		head=head+i;
	}
	public Request getrequest(int i) {
		return requestlist[i];
	}
	public int gethead() {
		return head;
	}
	public int getail() {
		return tail;
	}
}
