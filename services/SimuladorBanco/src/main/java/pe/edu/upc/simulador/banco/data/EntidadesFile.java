package pe.edu.upc.simulador.banco.data;

public enum EntidadesFile {

	TARJETA("ruta.archivo.info.estacion");

	private String tipo;

	EntidadesFile(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}