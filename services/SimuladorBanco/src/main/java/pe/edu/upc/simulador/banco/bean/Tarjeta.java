package pe.edu.upc.simulador.banco.bean;

public class Tarjeta {

	private Long id;
	private String tipoTrajeta;//debito o credito
	private String numero_cuenta ;
	private String fechaVencimineto;
	private String codSeguridad;
	private String clave;
	private String dni;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipoTrajeta() {
		return tipoTrajeta;
	}
	public void setTipoTrajeta(String tipoTrajeta) {
		this.tipoTrajeta = tipoTrajeta;
	}
	public String getNumero_cuenta() {
		return numero_cuenta;
	}
	public void setNumero_cuenta(String numero_cuenta) {
		this.numero_cuenta = numero_cuenta;
	}
	public String getFechaVencimineto() {
		return fechaVencimineto;
	}
	public void setFechaVencimineto(String fechaVencimineto) {
		this.fechaVencimineto = fechaVencimineto;
	}
	public String getCodSeguridad() {
		return codSeguridad;
	}
	public void setCodSeguridad(String codSeguridad) {
		this.codSeguridad = codSeguridad;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	
	
	
}
