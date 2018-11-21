package oo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class h2main {

	public static void main(String[] args) {
		RequestList REQlist=new RequestList();
		Elevator ELE=new Elevator();
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			//开始读入一行输入//
			String readin=null;
			try{
				readin=br.readLine();
			}catch(IOException e){
				System.out.println("ERROR");
			}
			readin=readin.replaceAll(" ","");
			//开始对输入进行判断//
			if(readin.equals("RUN")) {
				break;
			}
			else {
				String ti1="\\(FR,\\+?[0-9]{1,2},(UP|DOWN),\\+?[0-9]{1,10}\\)";
				Pattern p1=Pattern.compile(ti1);
				Matcher mat1=p1.matcher(readin);
				boolean boo1=mat1.matches();
				if(boo1) {
					//提取数字//
					String shuzi1="[0-9]+";
					Pattern pat1=Pattern.compile(shuzi1);
					Matcher matc1=pat1.matcher(readin);
					matc1.find();
					int s1=Integer.parseInt(matc1.group(0));
					matc1.find();
					double s2=Double.parseDouble(matc1.group(0));
					//提取方向//
					String fang="(UP|DOWN)";
					Pattern pat2=Pattern.compile(fang);
					Matcher matc2=pat2.matcher(readin);
					String d=null;
					matc2.find();
					d=matc2.group(0);
					int m=0;
					if(d.equals("UP")) {
						m=1;
					}
					else if(d.equals("DOWN")) {
						m=2;
					}
					//其他判断//
					if(s1==0||s1>10) {
						System.out.println("ERROR");
						System.out.println("#error in type");
					}
					else if(s1==1&&m==2) {
						System.out.println("ERROR");
						System.out.println("#error in type");
					}
					else if(s1==10&&m==1) {
						System.out.println("ERROR");
						System.out.println("#error in type");
					}
					else if(s2>4294967295.0) {
						System.out.println("ERROR");
						System.out.println("#error in type");
					}
					else if(REQlist.len()==0&&s2!=0){
						System.out.println("ERROR");
						System.out.println("#time must be 0");
					}
					else if(REQlist.len()>0&&s2<REQlist.last().outTime()) {
						System.out.println("ERROR");
						System.out.println("#wrong time order");
					}
					else {
						Request REQ=new Request(1,s1,m,s2);
						REQlist.add(REQ);
					}
					continue;
				}
				String ti2="\\(ER,\\+?[0-9]{1,2},\\+?[0-9]{1,10}\\)";
				Pattern p2=Pattern.compile(ti2);
				Matcher mat2=p2.matcher(readin);
				boolean boo2=mat2.matches();
				if(boo2) {
					String shuzi3="[0-9]+";
					Pattern pat3=Pattern.compile(shuzi3);
					Matcher matc3=pat3.matcher(readin);
					matc3.find();
					int s1=Integer.parseInt(matc3.group(0));
					matc3.find();
					double s2=Double.parseDouble(matc3.group(0));
					if(s1==0||s1>10) {
						System.out.println("ERROR");
						System.out.println("#error in type");
					}
					else if(s2>4294967295.0) {
						System.out.println("ERROR");
						System.out.println("#error in type");
					}
					else if(REQlist.len()==0&&s2!=0){
						System.out.println("ERROR");
						System.out.println("#time must be 0");
					}
					else if(REQlist.len()>0&&s2<REQlist.last().outTime()) {
						System.out.println("ERROR");
						System.out.println("#wrong time order");
					}
					else {
						Request REQ=new Request(0,s1,0,s2);
						REQlist.add(REQ);
					}					
					continue;
				}
				System.out.println("ERROR");
				System.out.println("#error in type");
			}
		}
		if(REQlist.len()==0) {
			System.out.println("ERROR");
			System.out.println("#no request");
		}
		else {
			Schedule SCH=new Schedule();
			SCH.command(REQlist, ELE);		
		}
	}
}
