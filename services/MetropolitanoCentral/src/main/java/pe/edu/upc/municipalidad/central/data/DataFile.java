package pe.edu.upc.municipalidad.central.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.upc.municipalidad.central.beans.Estacion;
import pe.edu.upc.municipalidad.central.beans.Estadistica;
import pe.edu.upc.municipalidad.central.beans.Ubicacion;

@Component
public class DataFile {

	@Autowired
	private Propiedades properties;

	@Autowired
	private DataInstance dataInstance;

	public List<Estacion> getEstaciones() {
		return dataInstance.listarEstaciones();
	}

	public List<Estacion> getEstaciones(String tipoServicio) {
		List<Estacion> lst = new ArrayList<Estacion>();
		List<Estacion> oi = dataInstance.listarEstaciones();
		for (Estacion estacion : oi) {
			if (estacion.getTipoServicio().equals(tipoServicio)) {
				lst.add(estacion);
			}
		}
		return lst;
	}

	public Estacion getEstacion(Long id) {
		Estacion e = dataInstance.getLista().get(id);
		return e;
	}

	public List<Estadistica> getEstadisticas(Long estacion) {
		List<Estadistica> estadisticas = dataInstance.listarEstadistica();
		List<Estadistica> r = new ArrayList<Estadistica>();
		for (Estadistica estadistica : estadisticas) {
			if (estadistica.getEstacion() == estacion) {
				r.add(estadistica);
			}
		}
		return r;
	}
	
	public Ubicacion getUbicaciones(Long estacion) {
		List<Ubicacion> ubicacions = dataInstance.listarUbicaciones();
		for (Ubicacion u : ubicacions) {
			if (u.getEstacion() == estacion) {
				return u;
			}
		}
		return null;
	}

	public List<Estacion> getEstacionHorario(String tipoServicio, String horaInicio,
			String horafin) {
		List<Estacion> oi = dataInstance.listarEstaciones();
		List<Estadistica> e = null,retuEst = new ArrayList<Estadistica>();
		for (Estacion estacion : oi) {
			if ((estacion.getTipoServicio().equals(tipoServicio))) {
				e = getEstadisticas(estacion.getId());
				for (Estadistica estadistica : e) {
					if(estadistica.equals(horaInicio)&&estadistica.equals(horafin)){
						retuEst.add(estadistica);				
					}					
				}
				estacion.setEstadisticas(retuEst);	
			}
		}				
		return oi;
	}
	
	
	
}
