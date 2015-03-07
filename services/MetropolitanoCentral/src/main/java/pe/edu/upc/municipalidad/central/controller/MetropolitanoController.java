package pe.edu.upc.municipalidad.central.controller;

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

import pe.edu.upc.municipalidad.central.beans.Estacion;
import pe.edu.upc.municipalidad.central.data.DataFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MetropolitanoController {
	
	private static final Logger logger = LoggerFactory.getLogger(MetropolitanoController.class);
	
	@Autowired
	private DataFile datefile;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		datefile.listarTodos();
		return "home";
	}

	@RequestMapping(value="/Estaciones", method = RequestMethod.GET)
	public @ResponseBody List<Estacion> getEstaciones() {
		return null;
	}

	@RequestMapping(value="/Estacion/{id}", method = RequestMethod.GET)
	public @ResponseBody Estacion getEstacion(@PathVariable Integer id) {
		return null;
	}
	@RequestMapping(value="/Estacion/Ubicacion/{longitud}/{latitud}", method = RequestMethod.GET)
	public @ResponseBody Estacion getEstacion(@PathVariable String longitud,@PathVariable String latitud) {
		return null;
	}
	
	@RequestMapping(value="/Estacion/Horario/{horaInicio}/{horafin}", method = RequestMethod.GET)
	public @ResponseBody Estacion getEstacionHorario(@PathVariable String horaInicio,@PathVariable String horafin) {
		return null;
	}
	
	
}
