package pe.edu.upc.central.bean;


public class Ubicacion {

	private Long id;
	
	private Long estacion;
	
	private String longitud;

	private String latitud;
	
	
	public String getLatitud() {
		return latitud;
	}
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEstacion() {
		return estacion;
	}
	public void setEstacion(Long estacion) {
		this.estacion = estacion;
	}
	
	
	
	
}
