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

	public int validarTarjeta(String tipoTrajeta, String numero_cuenta,
			String fechaVencimineto, String codSeguridad, String clave,
			String dni) {
		List<Tarjeta> t = dataInstance.listarTarjetas();
		for (Tarjeta tarjeta : t) {
			if (tarjeta.getTipoTrajeta().equals(tipoTrajeta)
					&& tarjeta.getCodSeguridad().equals(codSeguridad)
					&& tarjeta.getNumero_cuenta().equals(numero_cuenta)
					&& tarjeta.getFechaVencimineto().equals(fechaVencimineto)
					&& tarjeta.getClave().equals(clave)
					&& tarjeta.getDni().equals(dni)) {
				return 1;
			}
		}
		return 0;
	}

}
