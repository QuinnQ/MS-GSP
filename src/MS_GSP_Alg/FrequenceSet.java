package MS_GSP_Alg;

import java.util.*;

public class FrequenceSet {
	private ArrayList<Candidate> F_List;
	private int level;
//	private int count = 0;
	private Map<String,String> f_map;

	public FrequenceSet(int levelp){
		F_List = new ArrayList<Candidate>();
		level = levelp;
		f_map = new HashMap<String, String>();
	}
	
	
	public int getLevel(){
		return level;
	}
	

	
	private void addF(Candidate f, String s){
		if(!f_map.containsKey(s)){
			f_map.put(f.CandidateToString(), s);
//			System.out.println(s);
			F_List.add(f);
		}
	}
	public ArrayList<Candidate> getFrequenceSet(){
		ArrayList<Candidate> candSet = new ArrayList<Candidate>();
		for (Candidate c:F_List){
				Candidate copy_c = new Candidate(c);
				candSet.add(copy_c);
		}
		
		return candSet;
	}
	
	public void setFrequenceSet(CandidateSet cS1, int trans){
		for(Candidate c : cS1.getCandidateSet()){
			c.findMinMISItem(c);
//			System.out.println("MinMIS number: " + c.getMinMIS());
//			System.out.println("Count number: " + c.getCount());
			if((double)c.getCount()/trans >= c.getMinMIS()){
				Candidate copy_c = new Candidate(c);
				addF(copy_c,copy_c.CandidateToString());
//				F_List.add(copy_c);
			}
		}
	}

	
	public void displayFrequenceSet(){
		if(F_List.size() != 0){
			System.out.println("The number of length " + level + "-sequential patterns is :" + F_List.size());	
			for(int i = 0; i< F_List.size(); i++){
				Candidate c = new Candidate(F_List.get(i));
				String candStr = "Pattern: " + c.CandidateToString() + " Count: " + c.getCount();
				System.out.println(candStr);
			}
		}
	}
	
	
	public void extractFrequenceSet(L_MIS L){
		ArrayList<MS> para = new ArrayList<MS>();
		para = L.getL_MIS();
		int transN = L.getTransN();
//		System.out.println("Number of Transactions: " + transN);
		for(int i = 0; i < para.size(); i++){
//			System.out.println("Counter: " + para.get(i).getCounter());
			if((double)para.get(i).getCounter()/transN >= para.get(i).getMISNumber()){
				int tempN = para.get(i).getItemNumber();
//				System.out.println("The item number: " + tempN);
				double tempMIS = para.get(i).getMISNumber();
				int tempCounter = para.get(i).getCounter();
				MS temp = new MS(tempN, tempMIS);
				temp.setCounter(tempCounter);
				
				ArrayList<MS> tempL= new ArrayList<MS>(); 
				tempL.add(temp);
				ArrayList<ArrayList<MS>> t = new ArrayList<ArrayList<MS>>();
				t.add(tempL);
				Candidate c = new Candidate();
				c.setCandidate(t);
				c.setCount(tempCounter);
				addF(c,c.CandidateToString());
//				F_List.add(c);
			}
		}
	}
}
