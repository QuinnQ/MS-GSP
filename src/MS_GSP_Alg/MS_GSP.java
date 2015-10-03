package MS_GSP_Alg;

import java.util.*;

public class MS_GSP {
	private ArrayList<FrequenceSet> F;
	/**
	 * CONSTRUCTOR
	 */
	public MS_GSP(String data, String para){
		F = new ArrayList<FrequenceSet>();
		MS_GSP_Setup(data,para);
	}
	
	
	
	/**
	 * 
	 * @param data
	 * @param para, string input for initializing string input
	 */
	private void MS_GSP_Setup(String data, String para){
		DataSet dataset = new DataSet(data);
		MS_Parameters msPara = new MS_Parameters(para);
		L_MIS L = new L_MIS(dataset, msPara);
		FrequenceSet F1 = new FrequenceSet(1);
		F1.extractFrequenceSet(L);
		F1.displayFrequenceSet();
		F.add(F1);
		CandidateSet cS ;
		//= new CandidateSet(2, F1);
		FrequenceSet FS;
		int k = 2;
		while (!F.get(k-2).getFrequenceSet().isEmpty()){
			if(k == 2){
				cS = new CandidateSet(2,F1);
				cS.Level2_Candidate_Gen_SPM(L.getL_MIS(), F1.getFrequenceSet(), msPara.getSDC(), dataset.getTransN());
//				cS.displayCandidateSet();
//				System.out.println("Candidate size: "+cS.getCandidateSet().size());
//				System.out.println(dataset.getDataList().size() + "------" + cS.getCandidateSet().size());
			}
			else{
				cS = new CandidateSet(k,F.get(k-2));
				cS.MScandidate_gen_SPM(F.get(k-2).getFrequenceSet(), msPara.getSDC(), dataset.getTransN());
//				System.out.println(k+": "+cS.getCandidateSet().size());
//				cS.displayCandidateSet();
			}
			int dataSize = dataset.getDataList().size();
			
			for(int i = 0; i < dataSize; i++){
				int candSize = cS.getCandidateSet().size();
				for(int j = 0; j < candSize; j++){
					if(cS.getCandidateSet().get(j).isInDataSequence(dataset.getDataList().get(i))){
						int counter = cS.getCandidateSet().get(j).getCount() + 1;
	//					System.out.println(cS.getCandidateSet().get(j).CandidateToString() + "counter: "+ counter);
						cS.updateCounter(j, cS.getCandidateSet().get(j), counter);
						continue;
					}
				}
			}
			FS = new FrequenceSet(k);
			FS.setFrequenceSet(cS, dataset.getTransN());
			FS.displayFrequenceSet();
			F.add(FS);
			k++;
		}
	}
	
}



