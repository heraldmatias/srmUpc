package pe.edu.upc.municipalidad.central.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.upc.municipalidad.central.beans.Estacion;

@Component
public class DataFile {

	@Autowired
	private Propiedades properties;


	public List<Estacion> getEstaciones() {		
		ReaderFileUtil readerFileUtil = ReaderFileUtil.getInstace();	
		StringBuffer info = readerFileUtil.getData(properties.getValue(EntidadesFile.INFO_ESTACION.getTipo()));
		List<Estacion> listaEstaciones = transforTextoEstacion(info);
		return listaEstaciones;
	}

	private List<Estacion> transforTextoEstacion(StringBuffer info) {
		
		return null;
	}

	public List<Estacion> getEstaciones(String tipoServicio) {
		
		return null;
	}

}
