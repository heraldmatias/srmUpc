package pe.edu.upc.municipalidad.central.data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

import pe.edu.upc.municipalidad.central.beans.Estacion;
import pe.edu.upc.municipalidad.central.beans.Estadistica;
import pe.edu.upc.municipalidad.central.beans.Ubicacion;


public class DataInstance {

	@Autowired
	private Propiedades properties;

	private List<Estacion> lista;
	private List<Estadistica> estadisticas;
	private List<Ubicacion> ubicacions;
	
	public void init(){
		ReaderFileUtil readerFileUtil = ReaderFileUtil.getInstace();
		StringBuffer info = readerFileUtil.getData(properties.getValue(EntidadesFile.INFO_ESTACION.getTipo()));
		setLista(transforTextoEstacion(info));
		info = readerFileUtil.getData(properties.getValue(EntidadesFile.ESTADISTICA.getTipo()));
		setEstadisticas(transforTextoEstadistica(info));
		info = readerFileUtil.getData(properties.getValue(EntidadesFile.UBICACION.getTipo()));
		setUbicacions(transforTextoUbicacion(info));
	}
	
	private List<Ubicacion> transforTextoUbicacion(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),properties.getValue("toke.dato"));
		List<Ubicacion> lista = new ArrayList<Ubicacion>();
		Ubicacion estacion = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if(dtTemmp.length()==0){
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
			
			lista.add(estacion);
		} 	
		return lista;
	}

	private List<Estadistica> transforTextoEstadistica(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),properties.getValue("toke.dato"));
		List<Estadistica> lista = new ArrayList<Estadistica>();
		Estadistica estacion = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if(dtTemmp.length()==0){
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
			
			lista.add(estacion);
		} 	
		return lista;
	}

	private List<Estacion> transforTextoEstacion(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),properties.getValue("toke.dato"));
		List<Estacion> lista = new ArrayList<Estacion>();
		Estacion estacion = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if(dtTemmp.length()==0){
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
			lista.add(estacion);
		} 	
		return lista;
	}

	
	public List<Estacion> getLista() {
		return lista;
	}

	public void setLista(List<Estacion> lista) {
		this.lista = lista;
	}

	public List<Estadistica> getEstadisticas() {
		return estadisticas;
	}

	public void setEstadisticas(List<Estadistica> estadisticas) {
		this.estadisticas = estadisticas;
	}

	public List<Ubicacion> getUbicacions() {
		return ubicacions;
	}

	public void setUbicacions(List<Ubicacion> ubicacions) {
		this.ubicacions = ubicacions;
	}
	
}
