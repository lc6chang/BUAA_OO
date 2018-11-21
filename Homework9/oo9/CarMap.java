package oo9;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CarMap {
	private int[][] map;///存储原始地图信息/////
	private int[][] Matrix;//////////存储邻接矩阵////////////////////
	private int[][] volume;//////////存储车流量信息//////////////////
	public CarMap() {
		/**@REQUIRES:None;
		   @MODIFIES:this.map,this.Matrix,this.volume;
		   @EFFECTS:初始化以上三个属性;
		 */
		map=new int[80][80];
		Matrix=new int[6400][6400];
		volume=new int[6400][6400];
	}
	public boolean isnumber(char c) {
		/**@REQUIRES:char c;
		   @MODIFIES:None;
		   @EFFECTS:c>='0'&& c<='3'==>\result=true;
		    		!(c>='0'&& c<='3')==>\result=false;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		*/
		return c>='0'&&c<='3';
	}
	public boolean ispace(char c) {
		/**@REQUIRES:char c;
		   @MODIFIES:None;
		   @EFFECTS:c==' '||c=='\t'==>\result=true;
		   			!(c==' '||c=='\t')==>\result=false;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		*/
		return c==' '||c=='\t';
	}
	public void checkwrite(int l,String s) {
		/**@REQUIRES:int l,String s;
		 			 0<=l<=79;
		   @MODIFIES:this.map;
		   @EFFECTS:s.length()!=80=>System.out&&System.exit(0);
		   			0<=i<=79&&'0'<=s.charAt(i)<='3'==>map[l][i]=s.charAt(i)-'0';
		   			存在非法字符==>System.ouy&&System.exit(0);
		   @THREAD_REQUIRES:\locked(this.map);
		   @THREAD_EFFECTS:None;
		*/
		int m=0;
		for(int i=0;i<s.length();i++) {
			if(m==80) {
				System.out.println("Too many numbers!");
				System.exit(0);
			}
			if(isnumber(s.charAt(i))) {
				map[l][m++]=s.charAt(i)-'0';
				continue;
			}
			if(ispace(s.charAt(i))) {
				continue;
			}
			System.out.println("Error in map!");
			System.exit(0);
		}
		if(m!=80) {
			System.out.println("Need more numbers!");
			System.exit(0);
		}
	}
	public void setmap(String path) {
		/**@REQUIRES:String path;
		   @MODIFIES:this.map;
		   @EFFECTS:!File(path).exists==>System.out&&System.exit(0);
		   			File(path).lines'length!=80==>System.out&&System.exit(0);
		   			Others==>调用checkwrite()修改map&&System.out
		   @THREAD_REQUIRES:\locked(this.map);
		   @THREAD_EFFECTS:None;
		 */
		File file = new File(path);
		if(!file.exists()) {
			System.out.println("The map is not exist!");
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
				checkwrite(line,tempString);///////////////判断并给map赋值//////////////////
				line++;
			}
			if(line!=80) {
				System.out.println("Need more lines!");
				System.exit(0);
			}
			reader.close();
			System.out.println("The map is already!");
		} catch (IOException e) {  
			e.printStackTrace();  
		} 
	}
	public void initmatrix() {
		/**@REQUIRES:None;
		   @MODIFIES:this.Matrix
		   @EFFECTS:0<=i<6400&&0<=j<6400&& i==j==>Matrix[i][j]=0;
		   			0<=i<80&&0<=j<80&&(map[i][j]==1||map[i][j]==3)==>Matrix[i*80+j][i*80+j+1]=1&&Matrix[i*80+j+1][i*80+j]=1;
		   			0<=i<80&&0<=j<80&&(map[i][j]==2||map[i][j]==3)==>Matrix[i*80+j][(i+1)*80+j]=1&&Matrix[(i+1)*80+j][i*80+j]=1;
		   			对于其他的不在上述三条范围的Matrix,Matrix[i][j]=10000000;
		   @THREAD_REQUIRES:\locked(this.Matrix);
		   @THREAD_EFFECTS:None;
		 */
		int MAXNUM = 10000000;
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				if (i == j)
					Matrix[i][j] = 0;
				else
					Matrix[i][j] = MAXNUM;
			}
		}
		for (int i = 0; i < 80; i++) {
			for (int j = 0; j < 80; j++) {
				if (map[i][j] == 1 || map[i][j] == 3) {
					Matrix[i * 80 + j][i * 80 + j + 1] = 1;
					Matrix[i * 80 + j + 1][i * 80 + j] = 1;
				}
				if (map[i][j] == 2 || map[i][j] == 3) {
					Matrix[i * 80 + j][(i + 1) * 80 + j] = 1;
					Matrix[(i + 1) * 80 + j][i * 80 + j] = 1;
				}
			}
		}
		System.out.println("The matrix is already!");
	}
	public int getmap(int l,int m) {
		/**@REQUIRES:int l,m;
		    		 0<=l,m<80;
		   @MODIFIES:None;
		   @EFFECTS:\result=map[l][m];
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return map[l][m];
	}
	public int getmatrix(int l,int m) {
		/**@REQUIRES:int l,m;
		    		 0<=l,m<80;
		   @MODIFIES:None;
		   @EFFECTS:\result=Matrix[l][m];
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return Matrix[l][m];
	}
	public int[][] wholemap(){
		/**@REQUIRES:None;
		   @MODIFIES:None;
		   @EFFECTS:\result=map;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return map;
	}
	public int getvolume(int l,int m) {
		/**@REQUIRES:int l,m;
		    		 0<=l,m<80;
		   @MODIFIES:None;
		   @EFFECTS:\result=volume[l][m];
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:None;
		 */
		return volume[l][m];
	}
	synchronized public void addvolume(Point a,Point b) {
		/**@REQUIRES:Point a,b;
		 			 0<=a.x,a.y,b.x,b.y<=79;
		   @MODIFIES:volume;
		   @EFFECTS:volume[a.x*80+a.y][b.x*80+b.y]++;
		   			volume[b.x*80+b.y][a.x*80+a.y]++;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		int aa=a.x*80+a.y;
		int bb=b.x*80+b.y;
		volume[aa][bb]++;
		volume[bb][aa]++;
	}
	synchronized public void subvolume(Point a,Point b) {
		/**@REQUIRES:Point a,b;
					 0<=a.x,a.y,b.x,b.y<=79;
		   @MODIFIES:volume;
		   @EFFECTS:a==null||b==null==>return;
		   			volume[a.x*80+a.y][b.x*80+b.y]--;
		   			volume[b.x*80+b.y][a.x*80+a.y]--;
		   @THREAD_REQUIRES:None;
		   @THREAD_EFFECTS:\locked();
		 */
		if(a==null||b==null) {
			return;
		}
		int aa=a.x*80+a.y;
		int bb=b.x*80+b.y;
		volume[aa][bb]--;
		volume[bb][aa]--;
	}
	public void openpath(Point a,Point b) {
		/**@REQUIRES:Point a,b;
					 0<=a.x,a.y,b.x,b.y<=79;
		   @MODIFIES:this.Matrix;
		   @EFFECTS:Matrix[a.x*80+a.y][b.x*80+b.y]=1;
		   			Matrix[b.x*80+b.y][a.x*80+a.y]=1;
		   @THREAD_REQUIRES:\locked(this.Matrix);
		   @THREAD_EFFECTS:None;
		 */
		int aa=a.x*80+a.y;
		int bb=b.x*80+b.y;
		Matrix[aa][bb]=1;
		Matrix[bb][aa]=1;
	}
	public void closepath(Point a,Point b) {
		/**@REQUIRES:Point a,b;
					 0<=a.x,a.y,b.x,b.y<=79;
		   @MODIFIES:this.Matrix,this.volume;
		   @EFFECTS:Matrix[a.x*80+a.y][b.x*80+b.y]=2;
		   			Matrix[b.x*80+b.y][a.x*80+a.y]=2;
		   			volume[a.x*80+a.y][b.x*80+b.y]=0;
		   			volume[b.x*80+b.y][a.x*80+a.y]=0;
		   @THREAD_REQUIRES:\locked(this.Matrix,this.volume);
		   @THREAD_EFFECTS:None;
		 */
		int aa=a.x*80+a.y;
		int bb=b.x*80+b.y;
		Matrix[aa][bb]=2;
		Matrix[bb][aa]=2;
		volume[aa][bb]=0;
		volume[bb][aa]=0;
	}
	public void setvolume(Point a,Point b,int m) {
		/**@REQUIRES:Point a,b;
					 0<=a.x,a.y,b.x,b.y<=79;
		   @MODIFIES:this.Matrix,this.volume;
		   @EFFECTS:volume[a.x*80+a.y][b.x*80+b.y]=m;
		   			volume[b.x*80+b.y][a.x*80+a.y]=m;
		   @THREAD_REQUIRES:\locked(this.volume);
		   @THREAD_EFFECTS:None;
		 */
		int aa=a.x*80+a.y;
		int bb=b.x*80+b.y;
		volume[aa][bb]=m;
		volume[bb][aa]=m;
	}
}
