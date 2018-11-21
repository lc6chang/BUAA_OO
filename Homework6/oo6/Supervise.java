package oo6;

import java.io.File;

public class Supervise extends Thread{
	private int id;
	private All all;
	private FileList suplist;
	private FileList snapshot;
	private String searchpath;
	private int trigger;
	private int job;
	private int suptype;
	
	public Supervise (All a,int i) {
		id=i;
		all=a;
		suplist=new FileList();
		snapshot=new FileList();
		searchpath=null;
		trigger=0;
		job=0;
		suptype=0;
	}
	public void set(Request a) {
		trigger=a.outrigger();
		job=a.outjob();
		File file=new File(a.outpath());
		////////判断一下监控的是一个文件还是目录/////////////////
		if(file.isFile()) {
			suptype=1;
			searchpath=file.getParent();
			suplist.add(Changtofile.change(file));
			Catchfile cat=new Catchfile();
			cat.snapshot(searchpath);
			snapshot=cat.outfilelist();
		}
		else {
			suptype=2;
			searchpath=file.getAbsolutePath();
			Catchfile cat=new Catchfile();
			cat.snapshot(searchpath);
			suplist=cat.outfilelist();
			snapshot=cat.outfilelist();
		}	
	}
	public void recover(int trigg,Filenode old,Filenode now) {
		while(!all.fileask()) {
			;
		}
		File oldfile=new File(old.outpath());
		File newfile=new File(now.outpath());
		newfile.renameTo(oldfile);
		all.filend();
	}
	public void action(Filenode old,Filenode now) {
		if(job==1) {
			all.recordsummary(trigger);
			return;
		}
		if(job==2) {
			all.recorddetail(trigger, old, now);
			return;
		}
		if(job==3) {
			recover(trigger,old,now);
		}
	}
	public void renamed() {
		///////////首先进行新的监视器的文件捕捉，获得新增文件///////////////
		Catchfile cat=new Catchfile();
		while(!all.fileask()) {
			;
		}
		cat.snapshot(searchpath);
		FileList now=cat.outfilelist();
		all.filend();
		FileList diff=Hashsearch.diff(snapshot, now);
		///////////遍历监督区文件/////////////////////////////
		for(int i=0;i<suplist.len();i++) {
			Filenode file=suplist.search(i);
			Filenode file_change=file.exists(now);
			if(file_change!=null) {
				suplist.replace(i, file_change);
				continue;
			}
			int flag=0;
			for(int j=0;j<diff.len();j++) {
				Filenode file_diff=diff.search(j);
				if(file_diff.outfatherpath().equals(file.outfatherpath())&&file_diff.outmodified()==file.outmodified()&&file_diff.outsize()==file.outsize()) {
					action(file,file_diff);
					System.out.println(file_diff.outpath()+":renamed");
					if(job!=3) {
						suplist.replace(i,file_diff);
					}
					diff.remove(j);
					flag=1;
					break;
				}
			}
			if(flag==0) {
				suplist.remove(i);
				i--;
			}
		}
		if(suptype==2) {
			suplist.addlist(diff);
		}
		snapshot=now;
	}
	public void modified() {
		///////////首先进行新的监视器的文件捕捉，获得新增文件///////////////
		Catchfile cat=new Catchfile();
		while(!all.fileask()) {
			;
		}
		cat.snapshot(searchpath);
		FileList now=cat.outfilelist();
		all.filend();
		FileList diff=Hashsearch.diff(snapshot, now);
		/////////遍历监督区文件//////////////////////////////////
		for(int i=0;i<suplist.len();i++) {
			Filenode file=suplist.search(i);
			Filenode file_change=file.exists(now);
			if(file_change==null) {
				suplist.remove(i);
				i--;
				continue;
			}
			if(file_change.outmodified()!=file.outmodified()) {
				action(file,file_change);
				System.out.println(file_change.outpath()+":Modified");
				suplist.replace(i,file_change);
				continue;
			}
		}
		if(suptype==2) {
			suplist.addlist(diff);
		}
		snapshot=now;
	}
	public void pathchanged() {
		///////////首先进行新的监视器的文件捕捉，获得新增文件///////////////
		Catchfile cat=new Catchfile();
		while(!all.fileask()) {
			;
		}
		cat.snapshot(searchpath);
		FileList now=cat.outfilelist();
		all.filend();
		FileList diff=Hashsearch.diff(snapshot, now);
		
		////////////遍历监督区文件//////////////////////////////////////////
		for(int i=0;i<suplist.len();i++) {
			Filenode file=suplist.search(i);
			Filenode file_change=file.exists(now);
			if(file_change!=null) {
				suplist.replace(i, file_change);
				continue;
			}
			int flag=0;
			for(int j=0;j<diff.len();j++) {
				Filenode file_diff=diff.search(j);
				if(file_diff.outname().equals(file.outname())&&file_diff.outmodified()==file.outmodified()&&file_diff.outsize()==file.outsize()) {
					action(file,file_diff);
					System.out.println(file_diff.outpath()+":path-changed");
					if(job!=3) {
						suplist.replace(i,file_diff);
						if(suptype==1) {//////////////////如果是监控文件，若改变了路径则监控范围也需要改变//////////////////
							searchpath=file_diff.outfatherpath();
						}
					}
					diff.remove(j);
					flag=1;
					break;
				}
			}
			if(flag==0) {
				suplist.remove(i);
				i--;
			}
		}
		if(suptype==2) {
			suplist.addlist(diff);
		}
		snapshot=now;		
	}
	public void sizechanged() {
		///////////首先进行新的监视器的文件捕捉，获得新增文件///////////////
		Catchfile cat=new Catchfile();
		while(!all.fileask()) {
			;
		}
		cat.snapshot(searchpath);
		FileList now=cat.outfilelist();
		all.filend();
		FileList diff=Hashsearch.diff(snapshot, now);
		/////////遍历监督区文件//////////////////////////////////
		for(int i=0;i<suplist.len();i++) {
			Filenode file=suplist.search(i);
			Filenode file_change=file.exists(now);
			if(file_change==null) {
				suplist.remove(i);
				i--;
				continue;
			}
			if(file_change.outmodified()!=file.outmodified()&&file_change.outsize()!=file.outsize()) {
				action(file,file_change);
				System.out.println(file_change.outpath()+":size-changed");
				suplist.replace(i,file_change);
				continue;
			}
		}
		if(suptype==2) {
			suplist.addlist(diff);
		}
		snapshot=now;
	}
	public void override() {
		if(trigger==1) {
			renamed();
			return;
		}
		if(trigger==2) {
			modified();
			return;
		}
		if(trigger==3) {
			pathchanged();
			return;
		}
		if(trigger==4) {
			sizechanged();
			return;
		}
	}
	public void run() {
		while(all.outendflag()==0) {
			override();
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("监督器%d已结束\n",id);
	}
}
