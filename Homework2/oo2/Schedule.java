package oo2;

public class Schedule {
	
	public void command(RequestList list,Elevator ele) {
		this.shagua(list,ele);
	}
	
	public void shagua(RequestList list,Elevator ele) {
		for(int i=0;i<list.len();i++) {
			if(list.search(i).outflag()==0) {
				System.out.println("#the request was ignored");
				continue;
			}
			else {
				double timend=ele.movetime(list.search(i))+ele.startime(list.search(i))+1;
				for(int j=i+1;j<list.len();j++){
					if(list.search(j).outTime()>timend) {
						break;
					}
					else {
						int p=list.search(i).issame(list.search(j));
						if(p==1) {
							list.search(j).unflag();
						}
					}
				}
				int f=list.search(i).outfloor();
				int m=ele.moveway(list.search(i));
				double t1=ele.startime(list.search(i));
				double t2=ele.movetime(list.search(i));
				ele.UpandDown(f,m,t1,t2);
				ele.output();
				ele.stop();				
			}
		}
	}
}
