package oo11;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Light {
	/*Overview:这个类是红绿灯类，用于存储红绿灯的信息，提供了从文件读入红绿灯的方法，同时提供改变红绿灯的方法
	 * 
	 */
	private int[][] light;////////////存储路灯信息////////////////////
	private TaxiGUI gui;
	public Light(TaxiGUI _gui) {
		/**@REQUIRES:TaxiGUI _gui
		   @MODIFIES:this
		   @EFFECTS:this.light==new int[80][80];
		   			this.gui==_gui;
		 */
		light=new int[80][80];
		gui=_gui;
	}
    /*@repOk.
    check:\all int i,j;0<=i,j<80==>light[i][j]==0||light[i][j]==1||light[i][j]==2;
    */
    public boolean repOk(){
        /*
        @EFFECTS:\result = invariant(this)
        */
    	for(int i=0;i<80;i++) {
    		for(int j=0;j<80;j++) {
    			if(!(light[i][j]==0||light[i][j]==1||light[i][j]==2)) {
    				return false;
    			}
    		}
    	}
    	return true;
	}	
	public void checkwrite(int l,String s,int randomlight) {
		/**@REQUIRES:int l,String s,int randomlight;
		 * 			 0<=l<80,randomlight==1||randomlight==2;
		   @MODIFIES:this.light;
		   @EFFECTS:s.length()>=80==>System.out&&System.exit(0);
		   			\all int i;0<=i<80&&s.charAt(i)=='1'==>light[l][m]==randomlight;
		   			含有非法字符==>System.out&&System.exit(0);
		   @THREAD_REQUIRES:\locked(this.light);
		   @THREAD_EFFECTS:None;		   			
		 */		
		int m=0;
		for(int i=0;i<s.length();i++) {
			if(m==80) {
				System.out.println("Too many numbers!");
				System.exit(0);
			}
			if(s.charAt(i)=='0') {
				m++;
				continue;
			}
			if(s.charAt(i)=='1') {
				setlight(l,m,randomlight);
				m++;
				continue;
			}
			if(s.charAt(i)==' '||s.charAt(i)=='\t') {
				continue;
			}
			System.out.println("Error in file!");
			System.exit(0);
		}
		if(m!=80) {
			System.out.println("Need more numbers!");
			System.exit(0);
		}
	}
	public void initlight(String path) {
		/**@REQUIRES:String path;
		   @MODIFIES:this.light;
		   @EFFECTS:!File(path).exists==>System.out&&System.exit(0);
		   			File(path).lines'length!=80==>System.out&&System.exit(0);
		   			Others==>调用checkwrite()修改this.light&&System.out
		   @THREAD_REQUIRES:\locked(this.light);
		   @THREAD_EFFECTS:None;
		 */
		int randomlight=GetRandom.randomlight();
		File file = new File(path);
		if(!file.exists()) {
			System.out.println("The lightfile is not exist!");
			System.exit(0);
		}
		BufferedReader reader = null;  
		try {    
			reader = new BufferedReader(new FileReader(file));  
			String tempString = null;  
			int line = 0;  
			while ((tempString = reader.readLine()) != null) {
				if(line==80) {
					System.out.println("Too many lines!");
					System.exit(0);
				}
				checkwrite(line,tempString,randomlight);
				line++;
			}
			if(line!=80) {
				System.out.println("Need more lines!");
				System.exit(0);
			}
			reader.close();
		} catch (IOException e) {  
			e.printStackTrace();  
		}
		System.out.println("The light is already!");
	}
	public int change(int l,int m) {
		/**@REQUIRES:int l,m;0<=l<80&&0<=m<80;
		   @MODIFIES:None;
		   @EFFECTS:this.light[l][m]==0==>\result==0;
		   			this.light[l][m]==1==>\result==2;
		   			this.light[l][m]==2==>\result==1;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		if(light[l][m]==0) {
			return 0;
		}
		if(light[l][m]==1) {
			return 2;
		}
		else{
			return 1;
		}
	}
	public void setlight(int l,int m,int randomlight) {
		/**@REQUIRES:int l,m,randomlight;0<=l,m<80&&(randomlight==1||randomlight==2);
		   @MODIFIES:this.light;
		   @EFFECTS:this.light[l][m]==randomlight;
		   @THREAD_REQUIRES:\locked(this.light);
		   @THREAD_EFFECTS:None;
		 */
		light[l][m]=randomlight;
		gui.SetLightStatus(new Point(l,m),randomlight);
	}
	public void changelight() {
		/**@REQUIRES:None;
		   @MODIFIES:this.light;
		   @EFFECTS:\all int i,j;0<=i,j<80&&\old(light[i][j])==1==>light[i][j]==2;
		   			\all int i,j;0<=i,j<80&&\old(light[i][j])==2==>light[i][j]==1;
		   @THREAD_REQUIRES:\locked(this.light);
		   @THREAD_EFFECTS:None;
		 */
		int temp;
		for(int i=0;i<80;i++) {
			for(int j=0;j<80;j++) {
				temp=change(i,j);
				if(temp!=0) {
					light[i][j]=temp;
					gui.SetLightStatus(new Point(i,j),temp);
				}
			}
		}
	}
	public int getlight(Point a) {
		/**@REQUIRES:Point a;0<=a.x,a.y<80;
		   @MODIFIES:None;
		   @EFFECTS:\result==this.light[a.x][a.y];
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return light[a.x][a.y];
	}
}
