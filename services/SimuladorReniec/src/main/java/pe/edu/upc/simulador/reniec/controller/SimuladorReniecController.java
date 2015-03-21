package pe.edu.upc.simulador.reniec.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.edu.upc.simulador.reniec.bean.Persona;
import pe.edu.upc.simulador.reniec.data.DataFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SimuladorReniecController {

	private static final Logger logger = LoggerFactory
			.getLogger(SimuladorReniecController.class);

	@Autowired
	private DataFile datafile;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping( value = "/", method = RequestMethod.GET)
	public String get(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}
// , headers="Accept: application/json" , produces = "application/json"
	@RequestMapping(value = "/personas" , method = RequestMethod.GET)
	public @ResponseBody List<Persona> listarPersonas() {
		System.out.println("entra");
		return datafile.getPersonas();
	}

	@RequestMapping(value = "/validar/{dni}" , method = RequestMethod.GET)
	public @ResponseBody int validarPersonaDNI(@PathVariable String dni) {
		System.out.println("entra2");
		return datafile.validarPersonaDni(dni);
	}

}
