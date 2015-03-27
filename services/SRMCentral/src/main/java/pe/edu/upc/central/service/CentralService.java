package pe.edu.upc.central.service;

import java.util.List;

import pe.edu.upc.central.bean.Estacion;
import pe.edu.upc.central.bean.Persona;


/**
 * Handles requests for the application home page.
 */

public interface CentralService {

	List<Estacion> getEstaciones();

	List<Estacion> getEstaciones(String tipoServicio);

	Estacion getEstacion(Long long1);

	List<Estacion> getEstacionHorario(String tipoServicio, String horaInicio,
			String horafin);

	Persona validarPersonaDni(String dni);
	

	
}
