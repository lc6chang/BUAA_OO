package oo5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get extends Thread{
	private RequestList requestlist;
	private ALL all;
	public Get(RequestList l,ALL a) {
		requestlist=l;
		all=a;
	}
	
	public boolean getstring(String readin,double nowtime) {
		String[] readlist=readin.split(";",-1);
		if(requestlist.outflag()==0) {
			all.setime(nowtime);
			requestlist.changeflag();
		}
		for(int i=0;i<readlist.length;i++) {
			if(i==10) {
				all.addstring(System.currentTimeMillis()+":INVALID\r\n");
				return false;
			}
			if(readlist[i].equals("END")) {
				return true;
			}
			//////////////////判断是否是FR指令/////////////////////////////////
			String t_fr="\\(FR,\\+?[0-9]{1,3},(UP|DOWN)\\)";
			Pattern p_fr=Pattern.compile(t_fr);
			Matcher mat_fr=p_fr.matcher(readlist[i]);
			boolean boo_fr=mat_fr.matches();
			if(boo_fr) {
				//提取数字//
				String t_floor="[0-9]+";
				Pattern pat_floor=Pattern.compile(t_floor);
				Matcher mat_floor=pat_floor.matcher(readlist[i]);
				mat_floor.find();
				int floor=Integer.parseInt(mat_floor.group(0));
				//提取方向//
				String t_dire="(UP|DOWN)";
				Pattern pat_dire=Pattern.compile(t_dire);
				Matcher mat_dire=pat_dire.matcher(readlist[i]);
				mat_dire.find();
				int dire=0;
				if(mat_dire.group(0).equals("UP")) {
					dire=1;
				}
				else {
					dire=2;
				}
				//其他判断//
				if(floor==0||floor>20) {
					all.addstring(System.currentTimeMillis()+":INVALID"+"["+readlist[i]+","+String.format("%.1f",(nowtime-all.outbasetime())/1000)+"]\r\n");
				}
				else if(floor==1&&dire==2) {
					all.addstring(System.currentTimeMillis()+":INVALID"+"["+readlist[i]+","+String.format("%.1f",(nowtime-all.outbasetime())/1000)+"]\r\n");
				}
				else if(floor==20&&dire==1) {
					all.addstring(System.currentTimeMillis()+":INVALID"+"["+readlist[i]+","+String.format("%.1f",(nowtime-all.outbasetime())/1000)+"]\r\n");
				}
				else {
					readlist[i]=readlist[i].replaceAll("\\(","");
					readlist[i]=readlist[i].replaceAll("\\)","");
					Request a=new Request(1,0,floor,dire,(nowtime-all.outbasetime()),readlist[i]);
					requestlist.add(a);
				}
				continue;
			}
			
			String t_er="\\(ER,#(1|2|3),\\+?[0-9]{1,3}\\)";
			Pattern p_er=Pattern.compile(t_er);
			Matcher mat_er=p_er.matcher(readlist[i]);
			boolean boo_er=mat_er.matches();
			if(boo_er) {
				//提取数字//
				String t_floor="[0-9]+";
				Pattern pat_floor=Pattern.compile(t_floor);
				Matcher mat_floor=pat_floor.matcher(readlist[i]);
				mat_floor.find();
				int id=Integer.parseInt(mat_floor.group(0));
				mat_floor.find();
				int floor=Integer.parseInt(mat_floor.group(0));
				//其他判断//
				if(floor==0||floor>20) {
					all.addstring(System.currentTimeMillis()+":INVALID"+"["+readlist[i]+","+String.format("%.1f",(nowtime-all.outbasetime())/1000)+"]\r\n");
				}
				else {
					readlist[i]=readlist[i].replaceAll("\\(","");
					readlist[i]=readlist[i].replaceAll("\\)","");
					Request a=new Request(0,id,floor,0,(nowtime-all.outbasetime()),readlist[i]);
					requestlist.add(a);
				}
				continue;
			}
			all.addstring(System.currentTimeMillis()+":INVALID"+"["+readlist[i]+","+String.format("%.1f",(nowtime-all.outbasetime())/1000)+"]\r\n");			
		}
		return false;
		
	}
	public void run() {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String readin=null;
		for(int i=0;i<50;i++) {
			try{
				readin=br.readLine();
			}catch(IOException e){
				System.out.println("INVALID");
			}
			readin=readin.replaceAll(" ","");
			if(getstring(readin,(double)System.currentTimeMillis())) {
				break;
			}
		}
		all.end();
	}
}
