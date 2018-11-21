package oo6;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get {
	private RequestList requestlist;
	private int number;
	public Get(RequestList r) {
		requestlist=r;
		number=0;
	}
	public int outnumber() {
		return number;
	}
	public int getrigger(String a) {
		if(a.equals("renamed")) {
			return 1;
		}
		if(a.equals("Modified")) {
			return 2;
		}
		if(a.equals("path-changed")) {
			return 3;
		}
		if(a.equals("size-changed")) {
			return 4;
		}
		return 0;
	}
	public int getjob(String a) {
		if(a.equals("record-summary")) {
			return 1;
		}
		if(a.equals("record-detail")) {
			return 2;
		}
		if(a.equals("recover")) {
			return 3;
		}
		return 0;
	}
	public boolean getString(String readin) {
		if(readin.equals("END")) {
			return true;
		}
		String t_judge="IF<[^>]+><(renamed|Modified|path-changed|size-changed)>THEN<(record-summary|record-detail|recover)>";
		Pattern pat_judge=Pattern.compile(t_judge);
		Matcher mat_judge=pat_judge.matcher(readin);
		boolean boo_judge=mat_judge.matches();
		if(!boo_judge) {
			System.out.println("Error in type!");
			return false;
		}
		String t_get="<[^>]+>";
		Pattern pat_get=Pattern.compile(t_get);
		Matcher mat_get=pat_get.matcher(readin);
		/////////////////提取路径////////////////////
		mat_get.find();
		String path=mat_get.group(0);
		path=path.replaceAll("<","");
		path=path.replaceAll(">","");
		File file=new File(path);
		if(!file.exists()) {
			System.out.println("No exists!");
			return false;
		}
		String Apath=file.getAbsolutePath();
		////////////////提取触发器//////////////////
		mat_get.find();
		String trigger=mat_get.group(0);
		trigger=trigger.replaceAll("<","");
		trigger=trigger.replaceAll(">","");
		int Atrigger=getrigger(trigger);
		///////////////提取任务////////////////////
		mat_get.find();
		String job=mat_get.group(0);
		job=job.replaceAll("<","");
		job=job.replaceAll(">","");
		int Ajob=getjob(job);
		///////////////判断是否有误////////////////
		if(Ajob==3&&(Atrigger!=1&&Atrigger!=3)){
			System.out.println("Wrong Request!");
			return false;
		}
		//////////////判断是否为重复指令///////////
		for(int i=0;i<requestlist.len();i++) {
			Request a=requestlist.search(i);
			if(Apath.equals(a.outpath())&&Atrigger==a.outrigger()&&Ajob==a.outjob()) {
				System.out.println("Same Request!");
				return false;
			}
		}
		Request b=new Request(Apath,Atrigger,Ajob);
		requestlist.add(b);
		number++;
		return false;
	}
	public int read() {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String readin=null;
		for(int i=0;i<10;i++) {
			try{
				readin=br.readLine();
			}catch(IOException e){
				System.out.println("IOerror");
			}
			if(getString(readin)) {
				break;
			}
		}
		return number;
	}
}
