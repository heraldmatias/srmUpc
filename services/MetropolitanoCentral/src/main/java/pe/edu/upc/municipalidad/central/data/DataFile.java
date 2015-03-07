package pe.edu.upc.municipalidad.central.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.upc.municipalidad.central.beans.Estacion;

@Component
public class DataFile {

	@Autowired
	private Propiedades properties;
	
	@Autowired
	private DataInstance dataInstance;

	public List<Estacion> getEstaciones() {
		return dataInstance.getLista();
	}

	public List<Estacion> getEstaciones(String tipoServicio) {
		 List<Estacion> lst = new ArrayList<Estacion>();	
		 List<Estacion>  oi = dataInstance.getLista();
		 for (Estacion estacion : oi) {
			if(estacion.getTipoServicio().equals(tipoServicio)){
				lst.add(estacion);
			}
		}
		return lst;
	}


}
