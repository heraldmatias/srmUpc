package pe.edu.upc.simulador.reniec.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.upc.simulador.reniec.bean.Persona;

@Component
public class DataFile {

	@Autowired
	private Propiedades properties;

	@Autowired
	private DataInstance dataInstance;

	public List<Persona> getPersonas() {
		return dataInstance.listarPersonas();
	}

	public int validarPersonaDni(String dni) {
		List<Persona> t = dataInstance.listarPersonas();
		for (Persona persona : t) {
			if ( persona.getDni().equals(dni)) {
				return 1;
			}
		}
		return 0;
	}

}
