package pe.edu.upc.central.restclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pe.edu.upc.central.bean.Persona;

import com.google.gson.Gson;

@Component
public class RESTClientReniec extends RESTClient {
	private static final Logger logger = LoggerFactory.getLogger(RESTClientReniec.class);

	private final static String URL = "";

	public Persona validar(String dni) {
		try {
			String data = getConexion(URL + "/validar/" + dni, FORMAT);
			logger.debug(data);
			Gson gson = new Gson();
			Persona obj = gson.fromJson(data, Persona.class);
			return obj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
