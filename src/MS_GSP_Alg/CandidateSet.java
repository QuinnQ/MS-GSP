package MS_GSP_Alg;

import java.util.*;


public class CandidateSet {
	private ArrayList<Candidate> C_List;
	private int level = 0;
	private Map<String,String> map;
	
	public CandidateSet(int levelp, FrequenceSet s){
		C_List = new ArrayList<Candidate>();
		level = levelp;
		map = new HashMap<String,String>();
	}
	
//	public ArrayList<Candidate> getCandidateSet(){
//		ArrayList<Candidate> candSet = new ArrayList<Candidate>();
//		for (Candidate c:C_List){
//				Candidate copy_c = new Candidate(c);
//				candSet.add(copy_c);
//		}
//		
//		return candSet;
//	}
	
	public ArrayList<Candidate> getCandidateSet(){
		return C_List;
	}
	
	public void updateCounter(int index, Candidate c1, int counter){
		Candidate c = new Candidate(c1);
		c.setCount(counter);
		C_List.set(index, c);
	}
	
	public void displayCandidateSet(){
		System.out.println("The Candidates set " + level +":");
		
		for(int i = 0; i< C_List.size(); i++){
			Candidate c = new Candidate(C_List.get(i));
			String candStr = c.CandidateToString();
			System.out.println(candStr);
		}
	}
	
	
	private void addC(Candidate c){
		if(!map.containsKey(c.CandidateToString())){
			map.put(c.CandidateToString(), c.CandidateToString());
			C_List.add(c);
//			System.out.println(s);
		}
	}
	/**
	 * 
	 * @param L		L_MIS class
	 * @param F1	FrequenceSet level 1
	 * @param SDC	SDC constant from Parameters
	 * @param n		Transaction number from DataSet
	 */
	public void Level2_Candidate_Gen_SPM(ArrayList<MS> L, ArrayList<Candidate> F1, double SDC, int n){ // only for candidate set 2
		//Level2_candidate_gen
//		C_List = new ArrayList<Candidate>(); // candidate list, ex, {<{2}{3}>, <{2,3}>}
		for(int i = 0; i < L.size()-1; i++){
			if((double)L.get(i).getCounter()/n >= L.get(i).getMISNumber()){
				double sup_i = (double)L.get(i).getCounter()/n;
				for(int j = i+1; j < L.size(); j++){
					double sup_j = (double)L.get(j).getCounter()/n;
					if((double)L.get(j).getCounter()/n >= L.get(i).getMISNumber() 
							&& Math.abs(sup_j - sup_i) <= SDC){
//						System.out.println((sup_j - L.get(i).getMISNumber()) + " >= 0, " + (Math.abs(sup_j - sup_i) - SDC) + " <= 0" );
						ArrayList<ArrayList<MS>> c = new ArrayList<ArrayList<MS>>(); // for one candidate, ex, <{2, 3}>
						ArrayList<MS> celem = new ArrayList<MS>(); //for one element in one candidate, ex, {2, 3}
						MS temp = new MS(L.get(j).getItemNumber(), L.get(j).getMISNumber());
						temp.setCounter(L.get(j).getCounter());
						MS temp1 = new MS(L.get(i).getItemNumber(), L.get(i).getMISNumber());
						temp1.setCounter(L.get(i).getCounter());
						celem.add(temp1);
						celem.add(temp);
						c.add(celem);
						Candidate cand = new Candidate();
						cand.setCandidate(c);
						addC(cand);
						//{h}{l}
						c = new ArrayList<ArrayList<MS>>();
						celem = new ArrayList<MS>();
						celem.add(temp1);
						ArrayList<MS> celem2 = new ArrayList<MS>();
						celem2.add(temp);
						c.add(celem);
						c.add(celem2);
						cand = new Candidate();
						cand.setCandidate(c);
						addC(cand);
						//{l}{h}
						c = new ArrayList<ArrayList<MS>>();
						celem = new ArrayList<MS>();
						celem.add(temp);
						celem2 = new ArrayList<MS>();
						celem2.add(temp1);
						c.add(celem);
						c.add(celem2);
						cand = new Candidate();
						cand.setCandidate(c);
						addC(cand);
					}					
				}
				//<{l}{l}>
				ArrayList<ArrayList<MS>> c = new ArrayList<ArrayList<MS>>();
				ArrayList<MS> celem = new ArrayList<MS>();
				MS temp = new MS(L.get(i).getItemNumber(), L.get(i).getMISNumber());
				temp.setCounter(L.get(i).getCounter());
				celem.add(temp);
				c.add(celem);
				celem.clear();
				celem.add(temp);
				c.add(celem);
				Candidate cand = new Candidate();
				cand.setCandidate(c);
				addC(cand);
			}
			
		}
	}
	
	public boolean checkSDC(ArrayList<ArrayList<MS>> s1, MS ms, double SDC, int n){
		for(ArrayList<MS> elem: s1){
			for(MS e : elem){
//				System.out.println("ITEM "+ e.getItemNumber()+" : "+ e.getCounter() +" and "+ ms.getItemNumber() +" : "+ ms.getCounter());
				if(Math.abs((double)e.getCounter()/n - (double)ms.getCounter()/n) > SDC){
//					System.out.println(Math.abs((double)e.getCounter()/n - (double)ms.getCounter())/n + " > " + SDC + " FALSE!!");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * if k > 2 then follow MScandidate_gen_SPM(Fk-1)
	 * @param F		k-1 Sequence to generate the candidate sets to C_List
	 */	
	public void MScandidate_gen_SPM(ArrayList<Candidate> F, double SDC, int n){
		for(int i = 0; i < F.size(); i++){
			for(int j = 0; j < F.size(); j++){
				ArrayList<ArrayList<MS>> s1 = F.get(i).getCandidate();
				ArrayList<ArrayList<MS>> s2 = F.get(j).getCandidate();
//				System.out.println(s1.size() + "---s1.size and " + s2.size() + "---s2.size");
				if(isMinMISElem(s1, s1.get(0).get(0).getMISNumber())){
//					System.out.println("In if isMinMISElem s1");
					MScandidate_gen_SPM_S1_First_Min(s1, s2, SDC, n);
				}else if(isMinMISElem(s2, s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber())){
//					System.out.println("In else if isMinMISElem s2");
					MScandidate_gen_SPM_S2_Last_Min(s1, s2, SDC, n);
				}else{
//					System.out.println("In else Join sequence s1 and s2");
					JoinFormSequence(s1, s2, SDC, n);
				}
			}
		}
//		System.out.println("The size of candidate : " + C_List.size());
//		this.displayCandidateSet();
		//prue step:
//		pruning(F);
	}
	/**
	 * Join step in 2.13
	 * @param s1	a sequence set
	 * @param s2	a sequence set
	 */
	public void JoinFormSequence(ArrayList<ArrayList<MS>> s1, ArrayList<ArrayList<MS>> s2, double SDC, int n){
		if(isDuplicate(dropItem(s1, 1), dropItem(s2, getLength(s2)))){
			//similar to helper function steps
			if(s2.get(s2.size()-1).size() == 1){ // if s2.last only have one item then {s1, {s2.last}}
				//checkSDC
				if(checkSDC(s1, s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1), SDC, n)){
					ArrayList<ArrayList<MS>> c1 = new ArrayList<ArrayList<MS>>();
					for(ArrayList<MS> itemset : s1){
						ArrayList<MS> set = new ArrayList<MS>();
						for(MS item : itemset){
							MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
							copy_i.setCounter(item.getCounter());
							set.add(copy_i);
						}
						c1.add(set);
					}
					//add the last itemset of s2 to this c1
					MS copy_i = new MS(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getItemNumber(), 
							s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber());
					copy_i.setCounter(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getCounter());
					ArrayList<MS> set = new ArrayList<MS>();
					set.add(copy_i);
					c1.add(set);
					Candidate cand1 = new Candidate();
					cand1.setCandidate(c1);
//					System.out.println("Join_cand1_1: "+ cand1.CandidateToString());
					//add generated sequence to C_List
					addC(cand1);
				}
			}else if(s2.get(s2.size()-1).size() > 1){ //add the last item to s1 {s1, s1.lastitemset + s2.lastitem}
				if(checkSDC(s1, s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1), SDC, n)){
					ArrayList<ArrayList<MS>> c2 = new ArrayList<ArrayList<MS>>();
					for(int i = 0; i < s1.size(); i++){
						ArrayList<MS> set2 = new ArrayList<MS>();
						for(int j = 0; j < s1.get(i).size(); j++){
							MS copy_i2 = new MS(s1.get(i).get(j).getItemNumber(), s1.get(i).get(j).getMISNumber());
							copy_i2.setCounter(s1.get(i).get(j).getCounter());
							set2.add(copy_i2);
						}
						if(i == s1.size()-1){
							MS copy_i2 = new MS(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getItemNumber(), 
									s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber());
							copy_i2.setCounter(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getCounter());
							set2.add(copy_i2);
						}
						c2.add(set2);
					}
					Candidate cand2 = new Candidate();
					cand2.setCandidate(c2);
//					System.out.println("Join_cand2_1: "+ cand2.CandidateToString());
					//add generated sequence to C_List
					addC(cand2);
				}
			}
		}
	}
	
	
	public boolean isMinMISElem(ArrayList<ArrayList<MS>> s1, double MIS_val){
		for(ArrayList<MS> elem : s1){
			for(MS item : elem){
				if(item.getMISNumber() < MIS_val){
					return false;
				}
			}
		}
		return true;
	}
	
//	/**
//	 * help deal with pruning step
//	 * @param s1
//	 * @param MIS_val
//	 * @return boolean
//	 */
//	private boolean hasMinMISItem(ArrayList<ArrayList<MS>> s1, double MIS_val){
//		for (ArrayList<MS> elem: s1){
//			for (MS item : elem){
//				if (item.getMISNumber() == MIS_val)
//					return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * This function will return a candidate set by join s1 and s2, if MIS_S1[first] < every other MIS_S1 
	 * @param s1	the one will drop second item
	 * @param s2	the one will drop last item, MIS_S2[last] > MIS_S1[first]
	 * @return
	 */
	public void MScandidate_gen_SPM_S1_First_Min(ArrayList<ArrayList<MS>> s1, ArrayList<ArrayList<MS>> s2, double SDC, int n){
		if(isDuplicate(dropItem(s1, 2), dropItem(s2, getLength(s2))) &&
				s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber() >= s1.get(0).get(0).getMISNumber()){
			//s1 drop the second item equal to s2 drop the last item, and s2.last > s1.first
			if(s2.get(s2.size()-1).size() == 1 && 
					checkSDC(s1, s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1), SDC, n)){ // if s2.last only have one item then {s1, {s2.last}}
				ArrayList<ArrayList<MS>> c1 = new ArrayList<ArrayList<MS>>();
				for(ArrayList<MS> itemset : s1){
					ArrayList<MS> set = new ArrayList<MS>();
					for(MS item : itemset){
						MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
						copy_i.setCounter(item.getCounter());
						set.add(copy_i);
					}
					c1.add(set);
				}
				//add the last itemset of s2 to this c1
				MS copy_i = new MS(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getItemNumber(), 
						s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber());
				copy_i.setCounter(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getCounter());
				ArrayList<MS> set = new ArrayList<MS>();
				set.add(copy_i);
				c1.add(set);
//				System.out.println("if s2.last only have one item then {s1, {s2.last}}"+ checkDisplay(c1));
				
				Candidate cand1 = new Candidate();
				cand1.setCandidate(c1);
				//add generated sequence to C_List
//				System.out.println("first_min_cand1_1: "+ cand1.CandidateToString());
				addC(cand1);

//				C_List.add(cand1);
				
				// check if we can form c2
				if((getLength(s1) == getSize(s1) && getSize(s1) == 2 &&
						s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber() >= s1.get(s1.size()-1).get(s1.get(s1.size()-1).size()-1).getMISNumber()
						&& checkSDC(s1, s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1), SDC, n))
						&&s1.get(s1.size()-1).get(s1.get(s1.size()-1).size()-1).getItemNumber()<s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getItemNumber()){ // add to the last item of s1 to form c2
					ArrayList<ArrayList<MS>> c2 = new ArrayList<ArrayList<MS>>();
					for(int i = 0; i < s1.size(); i++){
						ArrayList<MS> set2 = new ArrayList<MS>();
						for(int j = 0; j < s1.get(i).size(); j++){
							MS copy_i2 = new MS(s1.get(i).get(j).getItemNumber(), s1.get(i).get(j).getMISNumber());
							copy_i2.setCounter(s1.get(i).get(j).getCounter());
							set2.add(copy_i2);
						}
						if(i == s1.size()-1){
							MS copy_i2 = new MS(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getItemNumber(), 
									s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber());
							copy_i2.setCounter(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getCounter());
							set2.add(copy_i2);
						}
						c2.add(set2);
					}
//						System.out.println("add the the last item of s1 to form c2" + checkDisplay(c2));
						
						Candidate cand2 = new Candidate();
						cand2.setCandidate(c2);
//						System.out.println("first_min_cand2_1: "+ cand2.CandidateToString());
						//add generated sequence to C_List
//						C_List.add(cand2);
						addC(cand2);

					
				}
			}else if (((getLength(s1) == 2 && getSize(s1) == 1 && 
					(s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber() >= s1.get(s1.size()-1).get(s1.get(s1.size()-1).size()-1).getMISNumber())) ||
					getLength(s1) > 2 && checkSDC(s1, s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1), SDC, n)
					)&&s1.get(s1.size()-1).get(s1.get(s1.size()-1).size()-1).getItemNumber()<s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getItemNumber()){
				ArrayList<ArrayList<MS>> c2 = new ArrayList<ArrayList<MS>>();
				for(ArrayList<MS> itemset : s1){
					ArrayList<MS> set = new ArrayList<MS>();
					for(MS item : itemset){
						MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
						copy_i.setCounter(item.getCounter());
						set.add(copy_i);
					}
					c2.add(set);
				}
				//add the last item of s2 to c2
				MS lastItem = s2.get(s2.size() - 1).get(s2.get(s2.size()-1).size()-1);
				c2.get(c2.size()-1).add(lastItem);
				
				Candidate cand2 = new Candidate();
				cand2.setCandidate(c2);
//				System.out.println("first_min_cand2_2: "+ cand2.CandidateToString());
				//add generated sequence to C_List
				addC(cand2);

//				C_List.add(cand2);
			}
		}
	}
	

	public void MScandidate_gen_SPM_S2_Last_Min(ArrayList<ArrayList<MS>> s1, ArrayList<ArrayList<MS>> s2, double SDC, int n){
		if(isDuplicate(dropItem(s1, 1), dropItem(s2, getLength(s2)-1)) &&
				s1.get(0).get(0).getMISNumber() >= s2.get(s2.size()-1).get(s2.get(s2.size()-1).size()-1).getMISNumber()){
			//s2 drop the last second item equal to s1 drop the first item, and s1.first > s2.last
//			System.out.println("sdlfjldfjklfj");
			if(s1.get(0).size() == 1 && checkSDC(s2, s1.get(0).get(0), SDC, n)){ // if s1.first only have one item then {{s1.first} {s2}}
				ArrayList<ArrayList<MS>> c1 = new ArrayList<ArrayList<MS>>();
				//add the s1.first to the set
				MS copy_i = new MS(s1.get(0).get(0).getItemNumber(), s1.get(0).get(0).getMISNumber());
				copy_i.setCounter(s1.get(0).get(0).getCounter());
				ArrayList<MS> set = new ArrayList<MS>();
				set.add(copy_i);
				c1.add(set);
				//add the all the itemset in s2 to the set
				for(ArrayList<MS> itemset : s2){
					ArrayList<MS> set_s2 = new ArrayList<MS>();
					for(MS item : itemset){
						MS copy_i_s2 = new MS(item.getItemNumber(), item.getMISNumber());
						copy_i_s2.setCounter(item.getCounter());
						set_s2.add(copy_i_s2);
					}
					c1.add(set_s2);
				}
				
//				System.out.println("if s1.first only have one item then {s1, {s2.last}}"+ checkDisplay(c1));
				
				Candidate cand1 = new Candidate();
				cand1.setCandidate(c1);
//				System.out.println("Last_Min_cand1: " + cand1.CandidateToString());
				//add generated sequence to C_List
				addC(cand1);

//				C_List.add(cand1);
				
				// check if we can form c2
				if(getLength(s2) == getSize(s2) && getSize(s2) == 2 &&
						s1.get(0).get(0).getMISNumber() >= s2.get(0).get(0).getMISNumber() &&
						checkSDC(s2, s1.get(0).get(0), SDC, n)
						&&s1.get(0).get(s1.get(0).size()-1).getItemNumber()<s2.get(0).get(s2.get(0).size()-1).getItemNumber()
						){ // add the the first item of s1 to form c2
					ArrayList<ArrayList<MS>> c2 = new ArrayList<ArrayList<MS>>();
					MS copy_S1_first = new MS(s1.get(0).get(0).getItemNumber(), s1.get(0).get(0).getMISNumber());
					copy_S1_first.setCounter(s1.get(0).get(0).getCounter());
					for(int i = 0; i < s2.size(); i++){
						ArrayList<MS> set_c2 = new ArrayList<MS>();
						if(i == 0){
							set_c2.add(copy_S1_first);
						}
						for(MS item : s2.get(i)){
							MS copy_i_s2 = new MS(item.getItemNumber(), item.getMISNumber());
							copy_i_s2.setCounter(item.getCounter());
							set_c2.add(copy_i_s2);
						}
						c2.add(set_c2);
					}
//						System.out.println("add the the first item of s1 to form c2" + checkDisplay(c2));
						
					Candidate cand2 = new Candidate();
					cand2.setCandidate(c2);
//					System.out.println("Last_Min_cand2_1: "+ cand2.CandidateToString());
					//add generated sequence to C_List
					addC(cand2);
					
//					C_List.add(cand2);
					
				}
			}else if (((getLength(s2) == 2 && getSize(s2) == 1 && 
					(s1.get(0).get(0).getMISNumber() >= s2.get(0).get(0).getMISNumber())) ||
					getLength(s2) > 2 &&
					checkSDC(s2, s1.get(0).get(0), SDC, n))&&s1.get(0).get(s1.get(0).size()-1).getItemNumber()<s2.get(0).get(s2.get(0).size()-1).getItemNumber()){
				ArrayList<ArrayList<MS>> c2 = new ArrayList<ArrayList<MS>>();
				//add the first item of s1 to c2
				ArrayList<MS> firstelem = s2.get(0);
				MS firstItem = s1.get(0).get(0);
				ArrayList<MS> set = new ArrayList<MS>();
				set.add(firstItem);
				
				for(MS item : firstelem){
					MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
					copy_i.setCounter(item.getCounter());
					set.add(copy_i);
				}
				c2.add(set);
				
				for (int i = 1; i < s2.size(); i++){
					ArrayList<MS> set_s1 = new ArrayList<MS>();
					
					for(MS item : s2.get(i)){
						MS copy_i = new MS(item.getItemNumber(), item.getMISNumber());
						copy_i.setCounter(item.getCounter());
						set_s1.add(copy_i);
					}
					c2.add(set_s1);
				}
				Candidate cand2 = new Candidate();
				cand2.setCandidate(c2);
//				System.out.println("Last_Min_cand2_2: "+ cand2.CandidateToString());
				//add generated sequence to C_List
				addC(cand2);
			}
		}
	}
	
	
	/**
	 * This function use to drop the item in the candidate set
	 * @param s		the candidate set 
	 * @param d_i	the index of which item need to be dropped
	 * @return		the candidate set without dropped item
	 */
	public String checkDisplay(ArrayList<ArrayList<MS>> c){
		String candTemp = "<";
		for(int i = 0; i < c.size(); i++){
			ArrayList<MS> elem = c.get(i);
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
	
	

	public ArrayList<ArrayList<MS>> dropItem(ArrayList<ArrayList<MS>> s, int d_i){ // if need to drop first item the d_i = 0
		int index = 0;
		int pos = -1;
		int set_pos = -1;
		ArrayList<ArrayList<MS>> goal_s = new ArrayList<ArrayList<MS>>();
		for(int i = 0 ; i < s.size(); i++){
			int setSize = s.get(i).size();
			index += setSize;
			if(index >= d_i && d_i > index - setSize){
				pos = i;
				set_pos = setSize - (index - d_i) - 1;
			}
		}
		
		ArrayList<MS> copy_m = new ArrayList<MS>();
		if(pos != -1){
			for( int i = 0 ; i < s.size(); i++){
				if(pos != i){
//					ArrayList<MS> copy_m = new ArrayList<MS>();
					for(MS me : s.get(i)){
						MS copy_me = new MS(me.getItemNumber(), me.getMISNumber());
						copy_me.setCounter(me.getCounter());
						copy_m.add(copy_me);
					}
					goal_s.add(copy_m);
				}else{
					if(s.get(i).size() == 1){
//						System.out.println("Droped one set, cause size = 1");
					}else{
//						ArrayList<MS> copy_m = new ArrayList<MS>();
						for(int j = 0; j < s.get(i).size(); j++){
							if(j != set_pos){
								MS copy_me = new MS(s.get(i).get(j).getItemNumber(), s.get(i).get(j).getMISNumber());
								copy_me.setCounter(s.get(i).get(j).getCounter());
								copy_m.add(copy_me);
							}
							else{
//								System.out.println("Droped item in the set");
							}
						}
						goal_s.add(copy_m);
					}
					
				}
			}
		}
		return goal_s;	
	}
	/**
	 * This function use to get total item number in one candidate set
	 * @param s candidate
	 * @return
	 */
	public int getLength(ArrayList<ArrayList<MS>> s){ // get items total number in one candidate set
		int length = 0;
		for(ArrayList<MS> m : s){
			length += m.size();
		}
		return length;
	}
	/**
	 * This function use to get total itemset number in one candidate
	 * @param s
	 * @return
	 */
	public int getSize(ArrayList<ArrayList<MS>> s){ // get itemset total number in one candidate set
		return s.size();		
	}
	
	/**
	 * This function use to tell if two candidate are same
	 * @param s1	
	 * @param s2
	 * @return	boolean
	 */
	
	
	
	public boolean isDuplicate(ArrayList<ArrayList<MS>> s1, ArrayList<ArrayList<MS>> s2){
		if(s1.size()!=s2.size())
			return false;
		else{
			ArrayList<ArrayList<MS>> temp = s1;
			for(int i = 0;i<temp.size();i++){
				for(int j = 0; j < temp.get(i).size(); j++){
					if(s1.get(i).get(j).getItemNumber()!=s2.get(i).get(j).getItemNumber())
						return false;
				}
			}
		}
		return true;
	}
///**
// * Pruning the candidate set base on the k-1 frequenceSet
// * @param F_p
// */
//	public void pruning(ArrayList<Candidate> F){
//		int i;
//		int j;
//		for(i = 0; i < C_List.size();i++){
//			boolean isRemoved = false;
//			int length = C_List.get(i).getLength();
//			for(j = 1; j < length; j++){
//				//use a temp candidate for checking duplicate
//				Candidate temp = new Candidate(C_List.get(i));
//				temp.setCandidate(dropItem(temp.getCandidate(), j));
////				System.out.println("temp:"+temp.CandidateToString());
//				int k = 0;
//				for(k = 0; k < F.size(); k++){
//					if(temp.checkDuplicate(F.get(k))){
//						break;
//					}
//				}
//				//&&hasMinMISItem(temp.getCandidate(),MS_Parameters.minMIS)
//				if(k == F.size()){
////					if(!hasMinMISItem(temp.getCandidate(),MS_Parameters.minMIS))
////						continue;
//					C_List.remove(i); //if can not find the subsequence in k-1 frequenceSet, remove this candidate from Set, so i keep original value
//					isRemoved = true;
//					break;
//				}				
//			}
//			if(isRemoved)
//				i--;
//		}
//	}

}
