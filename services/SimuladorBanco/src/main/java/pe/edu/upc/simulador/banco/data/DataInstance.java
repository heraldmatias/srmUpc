package pe.edu.upc.simulador.banco.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import pe.edu.upc.simulador.banco.bean.Tarjeta;

public class DataInstance {

	@Autowired
	private Propiedades properties;

	private Map<Long, Tarjeta> lista;

	public void init() {
		ReaderFileUtil readerFileUtil = ReaderFileUtil.getInstace();
		StringBuffer info = readerFileUtil.getData(properties
				.getValue(EntidadesFile.TARJETA.getTipo()));
		setLista(transforTextoTarjeta(info));
		
	}

	private Map<Long, Tarjeta> transforTextoTarjeta(StringBuffer info) {
		StringTokenizer tokens = new StringTokenizer(info.toString(),
				properties.getValue("toke.dato"));
		Map<Long, Tarjeta> lista = new HashMap<Long, Tarjeta>();
		Tarjeta estacion = null;
		String dtTemmp = null;
		while (tokens.hasMoreElements()) {
			dtTemmp = tokens.nextElement().toString().trim();
			if (dtTemmp.length() == 0) {
				break;
			}
			estacion = new Tarjeta();
			estacion.setId(Long.valueOf(dtTemmp));
			
			dtTemmp = tokens.nextElement().toString().trim();			
			estacion.setTipoTrajeta(dtTemmp);
			
			dtTemmp = tokens.nextElement().toString().trim();	
			estacion.setNumero_cuenta(dtTemmp);
			
			dtTemmp = tokens.nextElement().toString().trim();	
			estacion.setFechaVencimineto(dtTemmp);

			dtTemmp = tokens.nextElement().toString().trim();	
			estacion.setCodSeguridad(dtTemmp);
			
			dtTemmp = tokens.nextElement().toString().trim();	
			estacion.setClave(dtTemmp);
			
			dtTemmp = tokens.nextElement().toString().trim();	
			estacion.setDni(dtTemmp);	
			

			lista.put(estacion.getId(), estacion);
		}
		return lista;
	}

	public List<Tarjeta> listarTarjetas() {
		Map<Long, Tarjeta> mapEsta = getLista();
		Collection<Tarjeta> r = mapEsta.values();
		List<Tarjeta> a = new ArrayList<Tarjeta>();
		a.addAll(r);
		return a;
	}

	public Map<Long, Tarjeta> getLista() {
		return lista;
	}

	public void setLista(Map<Long, Tarjeta> lista) {
		this.lista = lista;
	}

	
	
}
