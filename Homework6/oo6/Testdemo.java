package oo6;

public class Testdemo extends Thread{
	private SafeFile safefile;
	public Testdemo(All a) {
		safefile=new SafeFile(a);
	}
	///////////这里可以参考指导书自己构造方法，也可以直接调用我SafeFile类的方法//////////////////////////
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public void run() {
		//////////在这里写入测试样例即可///////////////////////////////////////////////////////////////
		
		safefile.rename("d:\\test\\test2\\1.txt","d:\\test\\test2\\5.txt");
		safefile.write("d:\\test\\test3\\1.txt","d:\\test\\test2\\son\\1.txt", true);

		try {
			sleep(1876);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		safefile.rename("d:\\test\\test4\\1.txt","d:\\test\\test4\\son\\1.txt");
		safefile.write("d:\\test\\test5\\1.txt","d:\\test\\test2\\son\\1.txt", true);
	}
}
