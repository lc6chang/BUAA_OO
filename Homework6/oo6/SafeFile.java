package oo6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SafeFile {
	private All all;
	public SafeFile(All a) {
		all=a;
	}
	///////////////////创建目录，参数为目录的绝对路径///////////////////////////////////////////////////////////////
	public boolean adddir(String dirpath) {
		while(!all.fileask()) {
			;
		}
		File dir = new File(dirpath);
		boolean result=dir.mkdirs();
		all.filend();
		return result;
	}
	///////////////////创建文件，参数为文件的绝对路径，若文件绝对路径的目录不存在，则自动创建////////////////////////
	public boolean addfile(String filepath) {
		while(!all.fileask()) {
			;
		}
		File file=new File(filepath);
		File dir=new File(file.getParent());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		boolean result=false;
		try {
			result = file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		all.filend();
		return result;
	}
	/////////////////删除目录，（老铁我这个只能删除空目录，你要删除非空的就先删除文件再删目录吧，感谢了！我实在水平有限写不出来了///////////////////////////
	public boolean deletedir(String dirpath) {
		while(!all.fileask()) {
			;
		}
		File dir=new File(dirpath);
		boolean result=dir.delete();
		all.filend();
		return result;
	}
	///////////////删除文件，参数为需要删除文件的绝对路径//////////////////////////////////////////////////////////
	public boolean deletefile(String filepath) {
		while(!all.fileask()) {
			;
		}
		File file=new File(filepath);
		boolean result=file.delete();
		all.filend();
		return result;
	}
	///////////////重命名文件，两个参数分别为需要重命名的文件的原始绝对路径和新命名后的绝对路径///////////////////////
	public boolean rename(String oldpath,String newpath) {
		while(!all.fileask()) {
			;
		}
		File oldfile=new File(oldpath);
		File newfile=new File(newpath);
		boolean result=oldfile.renameTo(newfile);
		all.filend();
		return result;
	}
	/////////////移动文件（修改文件路径），两个参数分别是移动前的绝对路径和移动后的绝对路径/////////////////////
	public boolean move(String oldpath,String newpath) {
		while(!all.fileask()) {
			;
		}
		File oldfile=new File(oldpath);
		File newfile=new File(newpath);
		boolean result=oldfile.renameTo(newfile);
		all.filend();
		return result;
	}
	///////////////写入文件，三个参数分别是写入的文件的绝对路径、写入的内容和是否追加写入（追加则addornot赋值true）；若路径目录或文件不存在，则自动创建///////////////////////
	public boolean write(String filepath,String writein,boolean addornot){
		boolean result=false;
		while(!all.fileask()) {
			;
		}
		File file=new File(filepath);
		File dir=new File(file.getParent());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		try {
			FileWriter filewrite=new FileWriter(filepath,addornot);
			filewrite.write(writein);
			filewrite.flush();
			filewrite.close();
			result=true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		all.filend();
		return result;
	}
}
