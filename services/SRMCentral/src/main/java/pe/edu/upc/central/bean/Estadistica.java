package pe.edu.upc.central.bean;



public class Estadistica {

	private Long id;
	private Long estacion;
	private String horaInicio;
	private String horaFin;
	private Double cantidad;
	
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
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
