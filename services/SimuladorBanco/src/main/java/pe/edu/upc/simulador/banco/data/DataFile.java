package pe.edu.upc.simulador.banco.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.edu.upc.simulador.banco.bean.Tarjeta;

@Component
public class DataFile {

	@Autowired
	private Propiedades properties;

	@Autowired
	private DataInstance dataInstance;

	public List<Tarjeta> getTarjetas() {
		return dataInstance.listarTarjetas();
	}

	public List<Tarjeta> validarTarjeta(String tipoTrajeta,
			String codSeguridad, String numero_cuenta, String fechaVencimineto,
			String codSeguridad2, String clave, String dni) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
