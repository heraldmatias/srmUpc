package pe.edu.upc.central.restclient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pe.edu.upc.central.bean.Estacion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class RESTClientTransportes extends RESTClient {
	private static final Logger logger = LoggerFactory.getLogger(RESTClientTransportes.class);
	private final static String URL="";

	public List<Estacion> getEstaciones(){
		try {
			String data = getConexion(URL, FORMAT);		
			logger.debug(data);
			Gson gson = new Gson();			
			Type type = new TypeToken<List<Estacion>>(){}.getType();
			List<Estacion> obj = gson.fromJson(data, type);
			return obj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Estacion> getEstaciones(String tipo){
		try {
			String data = getConexion(URL+"/"+tipo, FORMAT);		
			logger.debug(data);	
			Gson gson = new Gson();			
			Type type = new TypeToken<List<Estacion>>(){}.getType();
			List<Estacion> obj = gson.fromJson(data, type);
			return obj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Estacion getEstacion(Integer id)  {
		try {
			String data = getConexion(URL+"/Estacion/"+id, FORMAT);	
			logger.debug(data);		
			Gson gson = new Gson();			
			Estacion obj = gson.fromJson(data, Estacion.class);
			return obj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public  List<Estacion> getEstacionHorario(String tipo, String horaInicio, String horaFin)  {
		try {
			String data = getConexion(URL+"/Estacion/"+tipo+"/"+horaInicio+"/"+horaFin, FORMAT);
			logger.debug(data);			
			Gson gson = new Gson();			
			Type type = new TypeToken<List<Estacion>>(){}.getType();
			List<Estacion> obj = gson.fromJson(data, type);
			return obj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	
}
