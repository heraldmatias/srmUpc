package pe.edu.upc.simulador.reniec.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class Propiedades {

	private Properties prop;

	private void init() {
		if (prop == null) {
			prop = new Properties();
			InputStream input = null;
			try {
				String filename = "config.properties";
				input = getClass().getClassLoader().getResourceAsStream(
						filename);
				if (input == null) {
					System.out.println("Sorry, unable to find " + filename);
					return;
				}
				prop.load(input);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {

			}
		}
	}

	public String getValue(String key) {
		if (prop == null) {
			init();
		}
		return prop.getProperty(key);
	}

}
