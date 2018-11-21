package oo6;

public class h6main {

	public static void main(String[] args) {
		All all=new All();
		RequestList requestlist=new RequestList();
		Get get=new Get(requestlist);
		int number=get.read();
		Supervise[] supervise=new Supervise[10];
		for(int i=0;i<number;i++) {
			supervise[i]=new Supervise(all,i);
			supervise[i].set(requestlist.search(i));
		}
		for(int i=0;i<number;i++) {
			supervise[i].start();
			System.out.printf("监督器%d已启动\n",i);
		}
		Writethread writethread=new Writethread(all);
		writethread.start();
		System.out.println("写入日志线程已启动");
		Endcontrol endcontrol=new Endcontrol(all);
		endcontrol.start();
		System.out.println("结束控制线程已启动");
		////////////////////请在这个后面加入测试的线程/////////////////////////////////////
		
		//////////////这里只是我给的样例，可以供你参考，不需要我这个测试线程就直接删掉这段代码就行////////////////
		Testdemo testdemo=new Testdemo(all);
		testdemo.start();
	}
}
