package pe.edu.upc.central.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.central.bean.Estacion;
import pe.edu.upc.central.bean.Persona;
import pe.edu.upc.central.restclient.RESTClientReniec;
import pe.edu.upc.central.restclient.RESTClientTransportes;
import pe.edu.upc.central.service.CentralService;

/**
 * Handles requests for the application home page.
 */
@Service("CentralService")
public class CentralServiceImpl  implements CentralService{
	
//	private static final Logger logger = LoggerFactory.getLogger(CentralServiceImpl.class);

	@Autowired
	private RESTClientTransportes clientTransportes;
	@Autowired
	private RESTClientReniec restClientReniec;
	
	@Override
	public List<Estacion> getEstaciones() {
		return clientTransportes.getEstaciones();
	}

	@Override
	public List<Estacion> getEstaciones(String tipoServicio) {
		return clientTransportes.getEstaciones(tipoServicio);
	}

	@Override
	public Estacion getEstacion(Long long1) {
		return clientTransportes.getEstacion(long1.intValue());
	}

	@Override
	public List<Estacion> getEstacionHorario(String tipoServicio,
			String horaInicio, String horafin) {
		return clientTransportes.getEstacionHorario(tipoServicio,horaInicio,horafin);
	}

	@Override
	public Persona validarPersonaDni(String dni) {
		return restClientReniec.validar(dni);
	}

	
}
