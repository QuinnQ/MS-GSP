package MS_GSP_Alg;


public class MS_GSP_Main {
	
	/**
	 * When calling, pass the datafile name followed by the parameter file name
	 * @param args
	 */
	public static void main(String[] args) {
		String dataFileName = MS_GSP_Main.class.getResource("inputFiles/data.txt").getPath();
		String paraFileName = MS_GSP_Main.class.getResource("inputFiles/para.txt").getPath();
		
		new MS_GSP(dataFileName,paraFileName);
	}

}
