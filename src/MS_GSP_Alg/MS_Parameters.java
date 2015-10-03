package MS_GSP_Alg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class MS_Parameters {
	private ArrayList<MS> MS_List;
	private double SDC;
	public static double minMIS;
	public MS_Parameters(String filename){
		readParameters(filename);
		sortParameters(this.getParameters());
	}
	/**
	 * This function is used to read the data.txt and fill the datasets arraylist
	 * @param filename
	 */
	public void readParameters(String filename){
		BufferedReader breader = null;
		try{
			minMIS = 1;
			File file = new File(filename);
			breader = new BufferedReader(new FileReader(file));
			String line;
			MS_List = new ArrayList<MS>();
			while((line = breader.readLine())!=null){
				if(line.contains("SDC")){
					StringTokenizer strtok = new StringTokenizer(line, "SDC =");
					SDC = Double.parseDouble(strtok.nextToken());
				}else{
					StringTokenizer strtok = new StringTokenizer(line, "MIS() =");
					int num = Integer.parseInt(strtok.nextToken());
					double MISv = Double.parseDouble(strtok.nextToken());
					MS ms = new MS(num, MISv);
					if (minMIS > MISv){
						minMIS = MISv;
					}
					MS_List.add(ms);
				}
			}
			breader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public double getSDC(){
		return SDC;
	}

	/**
	 * Return a copy of the datasets
	 * @return
	 */
	public ArrayList<MS> getParameters(){
		ArrayList<MS> para = new ArrayList<MS>();
		for(MS ms : MS_List){
			MS ms1 = new MS(ms.getItemNumber(), ms.getMISNumber());
			ms1.setCounter(ms.getCounter());
			para.add(ms1);
		}
		return para;
	}
	
		/**
		 * sort the ArrayList<MS> in ascending orders of MIS 
		 * @param ms_p
		 */
	public void sortParameters(ArrayList<MS> ms_p){
		ArrayList<MS> para = new ArrayList<MS>();
		para = ms_p;
		int j;
		boolean flag = true;
		int tempN;
		double tempMIS;
		while(flag){
			flag = false;
			for(j = 0; j < para.size()-1; j++ ){
				if(para.get(j).getMISNumber() > para.get(j+1).getMISNumber()){
					tempN = para.get(j).getItemNumber();
					tempMIS = para.get(j).getMISNumber();
					MS tempMS = new MS(tempN, tempMIS);
					MS msCopy = new MS(para.get(j+1).getItemNumber(), para.get(j+1).getMISNumber());
					para.set(j, msCopy);
					para.set(j+1, tempMS);
					flag = true;
				}
			}
		}
		MS_List = para;
	}
	
	public void displayParameters(){
//		sortParameters(this.getParameters());
		String str = "";
		System.out.println("The Sorted Parameters are:");
		for(int i = 0; i < MS_List.size(); i++){
			str = str + MS_List.get(i).getItemNumber() + " ";
		}
		System.out.println(str);
	}
	
	public void setParameters(ArrayList<MS> mL){
		ArrayList<MS> para = new ArrayList<MS>();
		for(MS ms : mL){
			MS ms1 = new MS(ms.getItemNumber(), ms.getMISNumber());
			ms1.setCounter(ms.getCounter());
			para.add(ms1);
		}
		MS_List.clear();
		MS_List.addAll(para);
	}
	public double getMinMIS(){
		return minMIS;
	}
}
