package pe.edu.upc.municipalidad.central.data;

public enum EntidadesFile {

	INFO_ESTACION("ruta.archivo.info.estacion"), 
	UBICACION(	"ruta.archivo.info.ubcacion"), 
	ESTADISTICA("ruta.archivo.info.estadistica");

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
