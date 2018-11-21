package oo7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CarMap {
	private int[][] map;///存储原始地图信息/////
	private int[][] Matrix;//////////存储邻接矩阵////////////////////
	public CarMap() {
		map=new int[80][80];
		Matrix=new int[6400][6400];
	}
	public boolean isnumber(char c) {
		return c>='0'&&c<='3';
	}
	public boolean ispace(char c) {
		return c==' '||c=='\t';
	}
	public void checkwrite(int l,String s) {
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
		return map[l][m];
	}
	public int getmatrix(int l,int m) {
		return Matrix[l][m];
	}
	public int[][] wholemap(){
		return map;
	}
}
