package pe.edu.upc.simulador.reniec.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;

import pe.edu.upc.simulador.reniec.bean.Persona;

public class DataInstance {

	@Autowired
	private Propiedades properties;

	private Map<Long, Persona> lista;

	public void init() {
		ReaderFileUtil readerFileUtil = ReaderFileUtil.getInstace();
		StringBuffer info = readerFileUtil.getData(properties
				.getValue(EntidadesFile.TARJETA.getTipo()));
		setLista(transforTextoPersona(info));
		
	}

	private Map<Long, Persona> transforTextoPersona(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),
				properties.getValue("toke.dato"));
		Map<Long, Persona> lista = new HashMap<Long, Persona>();
		Persona persona = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if (dtTemmp.length() == 0) {
				break;
			}
			persona = new Persona();
			persona.setId(Long.valueOf(dtTemmp));
			
			dtTemmp = tokens.nextElement().toString().trim();			
			persona.setNombre(dtTemmp);
			
			dtTemmp = tokens.nextElement().toString().trim();	
			persona.setApellidoPaterno(dtTemmp);
			
			dtTemmp = tokens.nextElement().toString().trim();	
			persona.setApellidoMaterno(dtTemmp);

			dtTemmp = tokens.nextElement().toString().trim();	
			persona.setFechaNacimiento(dtTemmp);
			
			dtTemmp = tokens.nextElement().toString().trim();	
			persona.setDni(dtTemmp);
			
			lista.put(persona.getId(), persona);
		}
		return lista;
	}

	public List<Persona> listarPersonas() {
		Map<Long, Persona> mapEsta = getLista();
		Collection<Persona> r = mapEsta.values();
		List<Persona> a = new ArrayList<Persona>();
		a.addAll(r);
		return a;
	}

	public Map<Long, Persona> getLista() {
		return lista;
	}

	public void setLista(Map<Long, Persona> lista) {
		this.lista = lista;
	}

	
	
}
