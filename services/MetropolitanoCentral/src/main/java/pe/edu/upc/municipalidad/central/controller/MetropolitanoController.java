package pe.edu.upc.municipalidad.central.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
 * @author crossfire - Danielle Delgado
 */
@Controller
public class MetropolitanoController {

	private static final Logger logger = LoggerFactory
			.getLogger(MetropolitanoController.class);

	@Autowired
	private DataFile datefile;

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

//	@RequestMapping(value = "/Estaciones", method = RequestMethod.GET)
//	public @ResponseBody List<Estacion> getEstaciones() {
//		return datefile.getEstaciones();
//	}

	@RequestMapping(value = "/Estaciones", method = RequestMethod.GET)
	public @ResponseBody List<Estacion> getEstaciones( String tipoServicio) {
		logger.info("tipoServicio:"+tipoServicio);
//		try {
//			tipoServicio = URLEncoder.encode(tipoServicio, "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		List<Estacion> lst = null;
		if(tipoServicio==null || tipoServicio.length() == 0){
			lst = datefile.getEstaciones();
		}else{
			lst = datefile.getEstaciones(tipoServicio);
		}
		if(lst!=null){
			for (Estacion e : lst) {
				logger.info(String.valueOf(e.getId()));
				if (e != null) {
					e.setEstadisticas(datefile.getEstadisticas(e.getId()));
					e.setUbicacion(datefile.getUbicaciones(e.getId()));
				}
			}
		}		
		return lst;
	}

	@RequestMapping(value = "/Estacion/{id}", method = RequestMethod.GET)
	public @ResponseBody Estacion getEstacion(@PathVariable Integer id) {
		Estacion e = datefile.getEstacion(new Long(id));
		if (e != null) {
			e.setEstadisticas(datefile.getEstadisticas(e.getId()));
			e.setUbicacion(datefile.getUbicaciones(e.getId()));
		}
		return e;
	}

	@RequestMapping(value = "/Estacion/Horario/{horaInicio}/{horafin}", method = RequestMethod.GET)
	public @ResponseBody List<Estacion> getEstacionHorario(
			String tipoServicio, @PathVariable String horaInicio,
			@PathVariable String horafin) {
//		try {
//			tipoServicio = URLEncoder.encode(tipoServicio, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return datefile.getEstacionHorario(tipoServicio, horaInicio, horafin);
	}

}
