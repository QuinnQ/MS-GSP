package MS_GSP_Alg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * DataSet: take a filename as a argument, convert the contents to integer arraylist
 * @author qianwang
 *
 */
public class DataSet {
	private ArrayList<DataSequence> data_List;
	private int transN; 
	
	public DataSet(String filename){
		data_List = new ArrayList<DataSequence>();
		readDataSet(filename);
		transN = data_List.size(); // if n is the size of the sequence

	}
	
//	public ArrayList<DataSequence> getDataList(){
//		ArrayList<DataSequence> temp = new ArrayList<DataSequence>();
//		for(DataSequence s : data_List){
//			DataSequence copy = new DataSequence(s);
//			temp.add(copy);
//		}
//		return temp;
//	}
	
	public ArrayList<DataSequence> getDataList(){
		return data_List;
	}
	
	/**
	 * This function is used to read the data.txt and fill the datasets arraylist
	 * @param filename
	 */
	public void readDataSet(String filename){
		BufferedReader breader = null;
		try{
			File file = new File(filename);
			breader = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = breader.readLine())!=null){
				StringTokenizer strtok = new StringTokenizer(line, "<>");
				while(strtok.hasMoreTokens()){
					ArrayList<ArrayList<Integer>> seq = new ArrayList<ArrayList<Integer>>();
					StringTokenizer strtokSeq = new StringTokenizer(strtok.nextToken(), "{}");
					while(strtokSeq.hasMoreTokens()){
						ArrayList<Integer> itemset = new ArrayList<Integer>();
						StringTokenizer strtokitems = new StringTokenizer(strtokSeq.nextToken(), ", ");
						while(strtokitems.hasMoreTokens()){
							String item = strtokitems.nextToken();
							itemset.add(Integer.parseInt(item));
//							datasets.add(Integer.parseInt(item));
						}
						seq.add(itemset);
//						transactionSet.add(itemset);
					}
					DataSequence dataSeq = new DataSequence();
					dataSeq.setDataSequence(seq);
					data_List.add(dataSeq);
				}
			}
			breader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the transaction number to val
	 * @param val
	 */
	
	public void countTransN(String filename){
		BufferedReader breader = null;
		try{
			File file = new File(filename);
			breader = new BufferedReader(new FileReader(file));
			String line;
			int counter = 0;
			while((line = breader.readLine())!=null){
				StringTokenizer strtok = new StringTokenizer(line, "{");
				counter += strtok.countTokens() - 1; // count transactions in one line
			}
			setTransN(counter);
			breader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setTransN(int val){
		transN = val;
	}
	/**
	 * Get the transaction number
	 * @return
	 */
	public int getTransN(){
		return transN;
	}

	
	public void displayDataList(){
		System.out.println("Data Set List is:");
		for(DataSequence data : data_List){
			data.displayDataSequence();
		}
	}
	
}
