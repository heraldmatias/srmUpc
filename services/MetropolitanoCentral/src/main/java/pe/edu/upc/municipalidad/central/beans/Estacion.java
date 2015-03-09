package pe.edu.upc.municipalidad.central.beans;

import java.util.List;

public class Estacion {

	private Long id;
	private String nombre;
	private String direccion;
	private String tipoServicio;
	private String subServicios;
	private String[] transportes;
	private Ubicacion ubicacion;
	private List<Estadistica> estadisticas;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Estadistica> getEstadisticas() {
		return estadisticas;
	}
	public void setEstadisticas(List<Estadistica> estadisticas) {
		this.estadisticas = estadisticas;
	}
	public String getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	public String[] getTransportes() {
		return transportes;
	}
	public void setTransportes(String[] transportes) {
		this.transportes = transportes;
	}
	public String getSubServicios() {
		return subServicios;
	}
	public void setSubServicios(String subServicios) {
		this.subServicios = subServicios;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
}
