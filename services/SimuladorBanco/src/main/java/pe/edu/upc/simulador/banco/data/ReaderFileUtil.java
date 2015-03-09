package pe.edu.upc.simulador.banco.data;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReaderFileUtil {
		
	
	private ReaderFileUtil(){}
	
	public static ReaderFileUtil getInstace(){
		return new ReaderFileUtil();
	}

	public StringBuffer getData(String... file) {

		StringBuffer linel = null;
		
		try {
			BufferedReader br= null;
			String line = null;
			linel = new StringBuffer();
			for (int i = 0; i < file.length; i++) {
				br = new BufferedReader(new FileReader(file[i]));
				while ((line = br.readLine()) != null) {
					linel.append(line).append("\n");
				}
				br.close();
			}
						
		} catch (Exception e) {
			System.err.println("Error en lectura"+e.getMessage());
			e.printStackTrace();
		}
		
		return linel;
	}
	
	
}
