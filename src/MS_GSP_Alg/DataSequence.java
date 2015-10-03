package MS_GSP_Alg;

import java.util.ArrayList;


public class DataSequence {
	private ArrayList<ArrayList<Integer>> dataSeq;
//	private boolean seen = false;
	public DataSequence(){
		dataSeq = new ArrayList<ArrayList<Integer>>();
	}
	/**
	 * COPY CONSTRUCTOR
	 * @param DataSequence s, 
	 * get a copy of s
	 */
	public DataSequence(DataSequence s){
		dataSeq = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> elem : s.getDataSequence()){
			ArrayList<Integer> e_temp = new ArrayList<Integer>();
			for(int e : elem){
				e_temp.add(e);
			}
			dataSeq.add(elem);
//			seen = s.getSeen();
		}
	}
	

	public ArrayList<ArrayList<Integer>> getDataSequence(){
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> elem : dataSeq){
			ArrayList<Integer> e_temp = new ArrayList<Integer>();
			for(int e : elem){
				e_temp.add(e);
			}
			temp.add(elem);
		}
		return temp;
	}
	
	public void setDataSequence(ArrayList<ArrayList<Integer>> data){
		for(ArrayList<Integer> elem : data){
			ArrayList<Integer> e_temp = new ArrayList<Integer>();
			for(int e : elem){
				e_temp.add(e);
			}
			dataSeq.add(e_temp);
		}
	}
	
	public void displayDataSequence(){
		String dataTemp = "<";
		for(int i = 0; i < dataSeq.size(); i++){
			ArrayList<Integer> elem = dataSeq.get(i);
			String data = "{";
			for(int j = 0; j < elem.size(); j++){
				
				if(j == elem.size()-1){
					data += elem.get(j);
				}else{
					data += elem.get(j)+ ", ";
				}
				
			}
			data += "}";
			dataTemp += data;
		}
		dataTemp += ">";
		System.out.println(dataTemp);
	}
}