package oo6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Endcontrol extends Thread{
	private All all;
	public Endcontrol(All a) {
		all=a;
	}
	public void run() {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String readin=null;
		try{
			readin=br.readLine();
		}catch(IOException e){
			System.out.println("IOerror");
		}
		if(readin.equals("stop")) {
			all.proend();
			System.out.println("程序准备结束啦！");
		}
	}
}
