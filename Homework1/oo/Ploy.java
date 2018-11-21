package oo;

public class Ploy
{
	int[] coff;
	int leng;
	public Ploy(int[] list,int l){
		this.coff=list;
		this.leng=l;
	}
	public void add(Ploy a){
		int[] array=new int[1000005];
		int i;
		for(i=0;i<this.leng;i=i+2){
			array[this.coff[i+1]]=array[this.coff[i+1]]+this.coff[i];
		}
		for(i=0;i<a.leng;i=i+2){
			array[a.coff[i+1]]=array[a.coff[i+1]]+a.coff[i];
		}
		int[] result=new int[2050];
		int len=0;
		for(i=0;i<1000005;i=i+1){
			if(array[i]!=0){
				result[len]=array[i];
				result[len+1]=i;
				len=len+2;
			}
		}
		this.coff=result;
		this.leng=len;
	}
	
	public void sub(Ploy a){
		int[] array=new int[1000005];
		int i;
		for(i=0;i<this.leng;i=i+2){
			array[this.coff[i+1]]=array[this.coff[i+1]]+this.coff[i];
		}
		for(i=0;i<a.leng;i=i+2){
			array[a.coff[i+1]]=array[a.coff[i+1]]-a.coff[i];
		}
		int[] result=new int[2050];
		int len=0;
		for(i=0;i<1000005;i=i+1){
			if(array[i]!=0){
				result[len]=array[i];
				result[len+1]=i;
				len=len+2;
			}
		}
		this.coff=result;
		this.leng=len;
	}

	public void show(){
		if(this.leng==0){
			System.out.println(0);
		}
		else{
			System.out.print("{");
			int i;
			for(i=0;i<this.leng;i=i+2){
				if(i>=2){
					System.out.print(",");
				}
				System.out.print("("+this.coff[i]+","+this.coff[i+1]+")");
			}
			System.out.print("}");
		}
	}
	
}
