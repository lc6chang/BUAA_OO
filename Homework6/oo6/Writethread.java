package oo6;

import java.io.IOException;

public class Writethread extends Thread {
	private All all;
	public Writethread(All a) {
		all=a;
	}
	public void run() {
		while(all.outendflag()==0) {
			try {
				sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				all.writesummary();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				all.writedetails();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("日志文件已更新");
		}
		System.out.println("写入日志线程已结束");
	}
}
