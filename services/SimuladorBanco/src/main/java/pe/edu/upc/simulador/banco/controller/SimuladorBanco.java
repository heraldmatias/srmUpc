package pe.edu.upc.simulador.banco.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.edu.upc.simulador.banco.bean.Tarjeta;
import pe.edu.upc.simulador.banco.data.DataFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SimuladorBanco {

	private static final Logger logger = LoggerFactory
			.getLogger(SimuladorBanco.class);

	@Autowired
	private DataFile datafile;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/tarjetas", method = RequestMethod.GET)
	public @ResponseBody List<Tarjeta> listarTarjetas() {
		return datafile.getTarjetas();
	}

	@RequestMapping(value = "/tarjetas", method = RequestMethod.POST)
	public @ResponseBody int validarTarjeta(String tipoTrajeta,
			String numero_cuenta, String fechaVencimineto, String codSeguridad,
			String clave, String dni) {
		return datafile.validarTarjeta(tipoTrajeta,numero_cuenta,fechaVencimineto,codSeguridad,clave,dni);
	}

}
