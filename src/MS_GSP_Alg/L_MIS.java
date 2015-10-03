package MS_GSP_Alg;

import java.util.ArrayList;


public class L_MIS {
	private ArrayList<MS> L_MIS_List;
	private int transN;
	
	public L_MIS(DataSet dataSet, MS_Parameters msPara){
		L_MIS_List = new ArrayList<MS>();
		transN = dataSet.getTransN();
		extract_L(dataSet, msPara);
	}
	
	public void extract_L(DataSet dataSet, MS_Parameters msPara){
		ArrayList<MS> para = new ArrayList<MS>();
		para = msPara.getParameters();
		ArrayList<DataSequence> data = new ArrayList<DataSequence>();
		data = dataSet.getDataList();
		for(int i = 0; i < para.size(); i++){
			int itemN = para.get(i).getItemNumber();
			int counter = 0;
			for(int j = 0; j < data.size(); j++){
				if(isInSequence(data.get(j).getDataSequence(),itemN)){
					counter++;
					continue;
				}
				
			}
			para.get(i).setCounter(counter);
		}
		extract_L_HP(dataSet, para);
	}
	
	private boolean isInSequence(ArrayList<ArrayList<Integer>> aai, int name){
		for(ArrayList<Integer> ai:aai){
			for(Integer i:ai){
				if(i == name)
					return true;
			}
		}
		return false;
		
	}
	
	
	public void extract_L_HP(DataSet dataSet, ArrayList<MS> ml){
		ArrayList<MS> para = new ArrayList<MS>();
		para = ml;
		int transN = dataSet.getTransN();
//		System.out.println("Number of Transactions: " + transN);
		double minMIS = para.get(0).getMISNumber();
//		System.out.println("minMIS = " + minMIS);
		for(int i = 0; i < para.size(); i++){
//			System.out.println("itemN:"+ para.get(i).getItemNumber() +"---Counter: " + para.get(i).getCounter());
//			System.out.println("value compare with minMIS: " + (double)para.get(i).getCounter()/transN + ", MIS(..) = " + para.get(i).getMISNumber());
			if((double)para.get(i).getCounter()/transN >= minMIS){
				int tempN = para.get(i).getItemNumber();
				double tempMIS = para.get(i).getMISNumber();
				int tempCounter = para.get(i).getCounter();
				MS temp = new MS(tempN, tempMIS);
				temp.setCounter(tempCounter);
				L_MIS_List.add(temp);
			}
		}
		
	}
	
	public ArrayList<MS> getL_MIS(){
		ArrayList<MS> para = new ArrayList<MS>();
		for(MS ms : L_MIS_List){
			MS ms1 = new MS(ms.getItemNumber(), ms.getMISNumber());
			ms1.setCounter(ms.getCounter());
			para.add(ms1);
		}
		return para;
	}
	
	public int getTransN(){
		return transN;
	}
	
	public void displayL_MIS(){
		String str = "";
		System.out.println("The L_MIS are:");
		for(int i = 0; i < L_MIS_List.size(); i++){
			str = str + L_MIS_List.get(i).getItemNumber() + " ";
		}
		System.out.println(str);
	}
	
}
