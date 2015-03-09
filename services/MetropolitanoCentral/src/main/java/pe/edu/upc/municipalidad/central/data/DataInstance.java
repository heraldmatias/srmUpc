package pe.edu.upc.municipalidad.central.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

import pe.edu.upc.municipalidad.central.beans.Estacion;
import pe.edu.upc.municipalidad.central.beans.Estadistica;
import pe.edu.upc.municipalidad.central.beans.Ubicacion;

public class DataInstance {

	@Autowired
	private Propiedades properties;

	private Map<Long, Estacion> lista;
	private Map<Long, Estadistica> estadisticas;
	private Map<Long, Ubicacion> ubicacions;

	public void init() {
		ReaderFileUtil readerFileUtil = ReaderFileUtil.getInstace();
		StringBuffer info = readerFileUtil.getData(properties
				.getValue(EntidadesFile.INFO_ESTACION.getTipo()));
		setLista(transforTextoEstacion(info));
		info = readerFileUtil.getData(properties
				.getValue(EntidadesFile.ESTADISTICA.getTipo()));
		setEstadisticas(transforTextoEstadistica(info));
		info = readerFileUtil.getData(properties
				.getValue(EntidadesFile.UBICACION.getTipo()));
		setUbicacions(transforTextoUbicacion(info));
	}

	private Map<Long, Ubicacion> transforTextoUbicacion(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),
				properties.getValue("toke.dato"));
		Map<Long, Ubicacion> lista = new HashMap<Long, Ubicacion>();
		Ubicacion estacion = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if (dtTemmp.length() == 0) {
				break;
			}
			estacion = new Ubicacion();
			estacion.setId(Long.valueOf(dtTemmp));
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setEstacion(Long.valueOf(dtTemmp));
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setLongitud(dtTemmp);
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setLatitud(dtTemmp);

			lista.put(estacion.getId(), estacion);
		}
		return lista;
	}

	private Map<Long, Estadistica> transforTextoEstadistica(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),
				properties.getValue("toke.dato"));
		Map<Long, Estadistica> lista = new HashMap<Long, Estadistica>();
		Estadistica estacion = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if (dtTemmp.length() == 0) {
				break;
			}
			estacion = new Estadistica();
			estacion.setId(Long.valueOf(dtTemmp));
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setEstacion(Long.valueOf(dtTemmp));
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setHoraInicio(dtTemmp);
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setHoraFin(dtTemmp);
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setCantidad(Double.valueOf(dtTemmp));

			lista.put(estacion.getId(), estacion);
		}
		return lista;
	}

	private Map<Long, Estacion> transforTextoEstacion(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),
				properties.getValue("toke.dato"));
		Map<Long, Estacion> lista = new HashMap<Long, Estacion>();
		Estacion estacion = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if (dtTemmp.length() == 0) {
				break;
			}
			estacion = new Estacion();
			estacion.setId(Long.valueOf(dtTemmp));
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setNombre(dtTemmp);
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setDireccion(dtTemmp);
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setTipoServicio(dtTemmp);
			dtTemmp = tokens.nextElement().toString().trim();
			estacion.setSubServicios(dtTemmp);
			lista.put(estacion.getId(), estacion);
		}
		return lista;
	}

	public List<Estacion> listarEstaciones() {
		Map<Long, Estacion> mapEsta = getLista();
		Collection<Estacion> r = mapEsta.values();
		List<Estacion> a = new ArrayList<Estacion>();
		a.addAll(r);
		return a;
	}

	public List<Estadistica> listarEstadistica() {
		Map<Long, Estadistica> mapEsta = getEstadisticas();
		Collection<Estadistica> r = mapEsta.values();
		List<Estadistica> a = new ArrayList<Estadistica>();
		a.addAll(r);
		return a;
	}

	public List<Ubicacion> listarUbicaciones() {
		Map<Long, Ubicacion> mapEsta = getUbicacions();
		Collection<Ubicacion> r = mapEsta.values();
		List<Ubicacion> a = new ArrayList<Ubicacion>();
		a.addAll(r);
		return a;
	}

	public Map<Long, Estacion> getLista() {
		return lista;
	}

	public void setLista(Map<Long, Estacion> lista) {
		this.lista = lista;
	}

	public Map<Long, Estadistica> getEstadisticas() {
		return estadisticas;
	}

	public void setEstadisticas(Map<Long, Estadistica> estadisticas) {
		this.estadisticas = estadisticas;
	}

	public Map<Long, Ubicacion> getUbicacions() {
		return ubicacions;
	}

	public void setUbicacions(Map<Long, Ubicacion> ubicacions) {
		this.ubicacions = ubicacions;
	}

	// public List<Estacion> getLista() {
	// return lista;
	// }
	//
	// public void setLista(List<Estacion> lista) {
	// this.lista = lista;
	// }
	//
	// public List<Estadistica> getEstadisticas() {
	// return estadisticas;
	// }
	//
	// public void setEstadisticas(List<Estadistica> estadisticas) {
	// this.estadisticas = estadisticas;
	// }
	//
	// public List<Ubicacion> getUbicacions() {
	// return ubicacions;
	// }
	//
	// public void setUbicacions(List<Ubicacion> ubicacions) {
	// this.ubicacions = ubicacions;
	// }

}
