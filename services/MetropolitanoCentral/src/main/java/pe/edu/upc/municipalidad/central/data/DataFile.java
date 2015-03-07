package pe.edu.upc.municipalidad.central.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.upc.municipalidad.central.beans.Estacion;


@Component
public class DataFile {
	
	@Autowired
	private Propiedades properties;
	private final static String INFO_METROPILITANO = "ruta.archivo.info.metropilitano";
	private final static String INFO_CORREDOR_AZUL= "ruta.archivo.info.corredor.azul";
	private final static String INFO_TREN= "ruta.archivo.info.tren";
	private final static String INFO_UBICACION="ruta.archivo.info.ubcacion";
	private final static String INFO_ESTADISTICA="ruta.archivo.info.estadistica";
		
	public Estacion listarTodos(){

		System.out.println(properties.getValue(INFO_METROPILITANO));
		System.out.println(properties.getValue(INFO_CORREDOR_AZUL));
		System.out.println(properties.getValue(INFO_TREN));
		System.out.println(properties.getValue(INFO_UBICACION));
		System.out.println(properties.getValue(INFO_ESTADISTICA));
		
		return null; 
	}

	
}
