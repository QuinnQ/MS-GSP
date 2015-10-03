package MS_GSP_Alg;

import java.util.ArrayList;


public class Candidate {
	private ArrayList<ArrayList<MS>> C;
	private int count = 0;
	private double minMIS;
	
	//constructor
	public Candidate(){
		C = new ArrayList<ArrayList<MS>>();
	}
	
	//copy constructor
	public Candidate(Candidate c1){
		C = new ArrayList<ArrayList<MS>>();
		for(ArrayList<MS> itemset : c1.getCandidate()){
			ArrayList<MS> set = new ArrayList<MS>();
			for(MS item : itemset){
				MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
				copy_i.setCounter(item.getCounter());
				set.add(copy_i);
			}
			C.add(set);
		}
		count = c1.getCount();
	}
	
	public double getMinMIS(){
		return minMIS;
	}
	
	/**
	 * Check the candidate in Data sequence
	 * @param c
	 * @param d
	 * @return
	 */
	public boolean isInDataSequence(DataSequence d){
		ArrayList<ArrayList<Integer>> dd = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> elem:d.getDataSequence()){
			ArrayList<Integer> e_temp = new ArrayList<Integer>();
			for(int e:elem){
				e_temp.add(e);
			}
			dd.add(elem);
		}
		
		int i = 0;
		int j = 0;
		int index = 0;
		
		for(i = 0; i < C.size(); i++){
			for(j = index; j < dd.size(); j++){
				if(isSubset(C.get(i), dd.get(j))){
					index = j+1;
					break;
				}
			}
			if(j == dd.size()){
				return false;
			}
			
		}
//		for(ArrayList<MS> itemset : C){
//			int i = 0;
//			for(; i < dd.size(); i++){
//				if(isSubset(itemset, dd.get(i))){
////					dd.remove(i);
//					break;
//				}
//				i++;
//				
//			}
//			if(i == dd.size()){
//				return false;
//			}
//		}
		return true;
	}
	
	/**
	 * This function is used for check if candidate itemSet is the subset of one transaction in transaction set
	 * @param c		the candidate itemset(arrayList) of one candidate
	 * @param trans 	means one transaction of all the transaction set
	 * @return
	 */
	public boolean isSubset(ArrayList<MS> c, ArrayList<Integer> trans){ 
		if(c.size()>trans.size())
			return false;
		int C_size = c.size();
		int trans_size = trans.size();
		int i = 0;
		int j = 0;
		for(i = 0; i < C_size; i++){
			for(j = 0; j < trans_size; j++){
				if(c.get(i).getItemNumber() == trans.get(j))
					break;
			}
			if(j == trans_size)
				return false;
		}
		return true;
	}
	
	
/**
 * Find the minMISItem in the candidate, return value is the index of the item in the itemlist	
 * @param c
 * @return
 */
	public int findMinMISItem(Candidate c){
		Candidate c_copy = new Candidate(c);
		int i = 0;
		int index = 0;
		ArrayList<Double> extractItems = new ArrayList<Double>();
		//find the minMISItem and remove from the candidate
		for(ArrayList<MS> itemset : c_copy.getCandidate()){
			for(MS item: itemset){
				extractItems.add(item.getMISNumber());
			}
		}
		if(extractItems.size() > 1){
			Double min = extractItems.get(0);			
			for(i = 1; i < extractItems.size(); i++){
				if(min > extractItems.get(i)){
					min = extractItems.get(i);
					index = i;
				}
			}
			minMIS = min;
		}
		
		return index;
	}
	
	/**
	 * to set C in a candidate 
	 * @param c1
	 * copy the value from c1
	 */
	public void setCandidate(ArrayList<ArrayList<MS>> c1){
		C = new ArrayList<ArrayList<MS>>();
		for(ArrayList<MS> itemset : c1){
			ArrayList<MS> set = new ArrayList<MS>();
			for(MS item : itemset){
				MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
				copy_i.setCounter(item.getCounter());
				set.add(copy_i);
			}
			C.add(set);
		}
	}
	
	/**
	 * This function use to get total item number in one candidate set
	 * @param s candidate
	 * @return
	 */
	public int getLength(){ // get items total number in one candidate set
		int length = 0;
		for(ArrayList<MS> m : C){
			length += m.size();
		}
		return length;
	}
	/**
	 * This function use to get total itemset number in one candidate
	 * @param s
	 * @return
	 */
	public int getSize(){ // get itemset total number in one candidate set
		return C.size();		
	}
	
	/**
	 * set the candidate to string for display and hashmaps
	 * @return
	 */
	public String CandidateToString(){
		String candTemp = "<";
		for(int i = 0; i < C.size(); i++){
			ArrayList<MS> elem = C.get(i);
			String cand = "{";
			for(int j = 0; j < elem.size(); j++){
				
				if(j == elem.size()-1){
					cand += elem.get(j).getItemNumber();
				}else{
					cand += elem.get(j).getItemNumber() + ", ";
				}
				
			}
			cand += "}";
			candTemp += cand;
		}
		candTemp += ">";
		return candTemp;
	}
	
	
	public boolean checkDuplicate(Candidate c){
		if(C.size()!=c.getCandidate().size())
			return false;
		else{
//			Candidate tempc1 = new Candidate();
//			tempc1.setCandidate(C);
//			Candidate tempc2 = new Candidate(c);
			ArrayList<ArrayList<MS>> temp = c.getCandidate();
			for(int i = 0;i<temp.size();i++){
				for(int j = 0; j < temp.get(i).size(); j++){
					if(C.get(i).get(j).getItemNumber()!=temp.get(i).get(j).getItemNumber())
						return false;
				}
			}
		}
		return true;
	}
	public boolean isDuplicate(Candidate c){
		if(C.size() != c.getCandidate().size()){
			return false;
		}
		
		else{
			Candidate temp1 = new Candidate();
			temp1.setCandidate(C);
			Candidate temp2 = new Candidate(c);
			int j = 0;
			int i = 0;
			for(i = 0; i < temp1.getCandidate().size(); i++){
				for(j = 0; j < temp2.getCandidate().size(); j++){
					if(C.get(i).size() == c.getCandidate().get(j).size()){ //find the itemset have the same size
						int k = 0;
						for(MS s_1 : C.get(i)){
							for(k = 0; k < c.getCandidate().get(j).size(); k++){
								if(s_1.getItemNumber() == c.getCandidate().get(j).get(k).getItemNumber()){
									break;
								}
							}
							if(k == c.getCandidate().get(j).size()){
//								System.out.println("do not find the item");
								return false;
							}
						}
					}
				}
//				if(j == s2.size()){
//					return false;
//				}
			}
			return true;
		}
	}
	
	public ArrayList<ArrayList<MS>> getCandidate(){
		ArrayList<ArrayList<MS>> copy_c = new ArrayList<ArrayList<MS>>();
		for(ArrayList<MS> itemset : C){
			ArrayList<MS> set = new ArrayList<MS>();
			for(MS item : itemset){
				MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
				copy_i.setCounter(item.getCounter());
				set.add(copy_i);
			}
			copy_c.add(set);
		}
		return copy_c;
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int val){
		count = val;
	}
}
