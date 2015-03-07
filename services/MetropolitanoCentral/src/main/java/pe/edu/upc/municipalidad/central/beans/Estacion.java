package pe.edu.upc.municipalidad.central.beans;

public class Estacion {

	private Long id;
	private String nombre;
	private String tipoServicio;
	private String subServicios;
	private String[] transportes;
	private Ubicacion ubicacion;
	private Estadistica estadistica;
	
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
	public Estadistica getEstadistica() {
		return estadistica;
	}
	public void setEstadistica(Estadistica estadistica) {
		this.estadistica = estadistica;
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
	
	
}
