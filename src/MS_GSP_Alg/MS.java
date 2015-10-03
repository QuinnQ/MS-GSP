package MS_GSP_Alg;

public class MS {
	private int itemN;
	private double MISV;
	private int counter = 0;
	
	public MS(int item, double MIS){
		itemN = item;
		MISV = MIS;
	}
	
	public int getItemNumber(){
		return itemN;
	}
	
	public double getMISNumber(){
		return MISV;
	}
	
	public void setCounter(int val){
		counter = val;
	}
	
	public int getCounter(){
		return counter;
	}
}
