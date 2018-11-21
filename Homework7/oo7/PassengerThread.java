package oo7;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassengerThread extends Thread{
	private Global global;
	private RequestList requestlist;
	public PassengerThread(Global g,RequestList r) {
		global=g;
		requestlist=r;
	}
	public boolean getString(String readin,long nowtime) {
		if(readin.equals("STOP")) {
			return true;
		}
		String judge_string="\\[CR,\\([0-9]{1,2},[0-9]{1,2}\\),\\([0-9]{1,2},[0-9]{1,2}\\)\\]";
		Pattern pat=Pattern.compile(judge_string);
		Matcher mat=pat.matcher(readin);
		boolean bool=mat.matches();
		if(!bool) {
			System.out.println("Error in type!");
			return false;
		}
		String judge_number="[0-9]+";
		Pattern pat_number=Pattern.compile(judge_number);
		Matcher mat_number=pat_number.matcher(readin);
		int[] number=new int[4];
		for(int i=0;i<4;i++) {
			mat_number.find();
			number[i]=Integer.parseInt(mat_number.group(0));
			if(number[i]>=80) {
				System.out.println("Error in type!");
				return false;
			}
		}
		Point start=new Point(number[0],number[1]);
		Point end=new Point(number[2],number[3]);
		if(start.equals(end)) {
			System.out.println("Same start point and target point!");
			return false;
		}
		Request request=new Request(start,end,nowtime/100,readin);
		requestlist.searchadd(request);
		return false;
	}
	public void run() {
		System.out.println("Passenger thread is start!");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String readin=null;
		while(true){
			try{
				readin=br.readLine();
			}catch(IOException e){
				System.out.println("IOerror");
			}
			if(getString(readin,System.currentTimeMillis())) {
				break;
			}
		}
		global.end();
		System.out.println("Passenger thread is end!");
	}
}
