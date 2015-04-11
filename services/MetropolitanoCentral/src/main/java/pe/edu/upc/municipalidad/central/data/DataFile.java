package pe.edu.upc.municipalidad.central.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.upc.municipalidad.central.beans.Estacion;
import pe.edu.upc.municipalidad.central.beans.Estadistica;
import pe.edu.upc.municipalidad.central.beans.Ubicacion;

@Component
public class DataFile {

	private static final Logger logger = LoggerFactory
			.getLogger(DataFile.class);

	@Autowired
	private Propiedades properties;

	@Autowired
	private DataInstance dataInstance;

	public List<Estacion> getEstaciones() {
		return dataInstance.listarEstaciones();
	}

	public List<Estacion> getEstaciones(String tipoServicio) {
		logger.info(tipoServicio);
		List<Estacion> lst = new ArrayList<Estacion>();
		List<Estacion> oi = dataInstance.listarEstaciones();
		for (Estacion estacion : oi) {
			if (estacion.getTipoServicio().trim().equals(tipoServicio.trim())) {
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
		String idEstacion = String.valueOf(estacion);
		List<Estadistica> estadisticas = dataInstance.listarEstadistica();
		List<Estadistica> r = new ArrayList<Estadistica>();
		for (Estadistica estadistica : estadisticas) {

			logger.info("Estadistica estacion:" + estadistica.getEstacion());
			logger.info("Estacion:" + estacion);
			logger.info("Encontrado:" + (estadistica.getEstacion().toString().trim().equals(idEstacion.trim())) );
			if (estadistica.getEstacion().toString().trim()	.equals(idEstacion.trim())) {
				r.add(estadistica);
			}
		}
		return r;
	}

	public Ubicacion getUbicaciones(Long estacion) {
		String idEstacion = String.valueOf(estacion);
		List<Ubicacion> ubicacions = dataInstance.listarUbicaciones();
		for (Ubicacion u : ubicacions) {
			logger.info("Ubicacion estacion:" + u.getEstacion());
			logger.info("Estacion:" + estacion);
			logger.info("Encontrado:" + (u.getEstacion().toString().trim().equals(idEstacion.trim())) );
			if (u.getEstacion().toString().trim().equals(idEstacion.trim())) {
				return u;
			}
		}
		return null;
	}

	public List<Estacion> getEstacionHorario(String tipoServicio,
			String horaInicio, String horafin) {
		List<Estacion> oi = dataInstance.listarEstaciones();
		List<Estadistica> e = null, retuEst = new ArrayList<Estadistica>();
		for (Estacion estacion : oi) {
			if ((estacion.getTipoServicio().trim().equals(tipoServicio.trim()))) {
				e = getEstadisticas(estacion.getId());
				for (Estadistica estadistica : e) {
					if (estadistica.getHoraInicio().trim()
							.equals(horaInicio.trim())
							&& estadistica.getHoraFin().trim()
									.equals(horafin.trim())) {
						retuEst.add(estadistica);
					}
				}
				estacion.setEstadisticas(retuEst);
			}
		}
		return oi;
	}

}
