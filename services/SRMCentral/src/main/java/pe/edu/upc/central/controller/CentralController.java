package pe.edu.upc.central.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.edu.upc.central.bean.Estacion;
import pe.edu.upc.central.bean.Persona;
import pe.edu.upc.central.service.CentralService;
import pe.edu.upc.central.util.Datastore;
import pe.edu.upc.central.util.GCMUtil;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CentralController {

	private static final Logger logger = LoggerFactory
			.getLogger(CentralController.class);

	private static final String ATTRIBUTE_STATUS = "status";
	private static final int MULTICAST_SIZE = 1000;

	@Autowired
	private CentralService centralService;

	@Autowired
	private ServletConfig config;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("HOME");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		
		System.out.println(config);
		Sender sender = GCMUtil.newSender(config);
		System.out.println(sender);
		return "home";
	}

	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public @ResponseBody
	String registrar(String regId) {
		Datastore.register(regId);
		return "OK";
	}

	@RequestMapping(value = "/desregistrar", method = RequestMethod.POST)
	public @ResponseBody
	String desregistrar(String regId) {
		Datastore.unregister(regId);
		return "OK";
	}

	@RequestMapping(value = "/mensaje", method = RequestMethod.POST)
	public @ResponseBody
	String mensaje(HttpServletRequest req, Model model) throws IOException {
		Sender sender = GCMUtil.newSender(config);

		List<String> devices = Datastore.getDevices();
		String status;
		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {
			// NOTE: check below is for demonstration purposes; a real
			// application
			// could always send a multicast, even for just one recipient
			if (devices.size() == 1) {
				// send a single message using plain post
				String registrationId = devices.get(0);
				// Message message = new Message.Builder().build();
				Message message = new Message.Builder()

						// If multiple messages are sent using the same
						// .collapseKey()
						// the android target device, if it was offline during
						// earlier message
						// transmissions, will only receive the latest message
						// for that key when
						// it goes back on-line.
						// .collapseKey(collapseKey)
						.timeToLive(30).delayWhileIdle(true)
						.addData("message", "mesnaje desde servidor").build();
				System.out.println(message);
				System.out.println(message.getCollapseKey());
				System.out.println(message.getData());
				Result result = sender.send(message, registrationId, 5);
				status = "Sent message to one device: " + result;
				System.out.println(status);
			} else {
				// send a multicast message using JSON
				// must split in chunks of 1000 devices (GCM limit)
				int total = devices.size();
				List<String> partialDevices = new ArrayList<String>(total);
				int counter = 0;
				int tasks = 0;
				for (String device : devices) {
					counter++;
					partialDevices.add(device);
					int partialSize = partialDevices.size();
					if (partialSize == MULTICAST_SIZE || counter == total) {
						GCMUtil.asyncSend(partialDevices, sender);
						partialDevices.clear();
						tasks++;
					}
				}
				status = "Asynchronously sending " + tasks
						+ " multicast messages to " + total + " devices";
				System.out.println(status);
			}
		}
		// req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
		model.addAttribute(ATTRIBUTE_STATUS, status.toString());
		return "OK";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

//	@RequestMapping(value = "/Estaciones", method = RequestMethod.GET)
//	public @ResponseBody
//	List<Estacion> getEstaciones() {
//		return centralService.getEstaciones();
//	}

	@RequestMapping(value = "/Estaciones", method = RequestMethod.GET)
	public @ResponseBody
	List<Estacion> getEstaciones(String tipoServicio) {
		if(tipoServicio==null||tipoServicio.length()==0){
			tipoServicio = "";
		}		
		return centralService.getEstaciones(tipoServicio);
	}

	@RequestMapping(value = "/Estacion/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Estacion getEstacion(@PathVariable Integer id) {
		Estacion e = centralService.getEstacion(new Long(id));
		return e;
	}

	@RequestMapping(value = "/Estacion/Horario/{tipoServicio}/{horaInicio}/{horafin}", method = RequestMethod.GET)
	public @ResponseBody
	List<Estacion> getEstacionHorario(@PathVariable String tipoServicio,
			@PathVariable String horaInicio, @PathVariable String horafin) {
		return centralService.getEstacionHorario(tipoServicio, horaInicio,
				horafin);
	}

	@RequestMapping(value = "/validar/{dni}", method = RequestMethod.GET)
	public @ResponseBody
	Persona validarPersonaDNI(@PathVariable String dni) {
		return centralService.validarPersonaDni(dni);
	}

	@RequestMapping(value = "/pago", method = RequestMethod.POST)
	public @ResponseBody
	int pagotarjeta(String regId,String servicio,String numeroTarjeta,String monto) {
		Sender sender = GCMUtil.newSender(config);
		String registrationId = regId;
		// Message message = new Message.Builder().build();
		Message message = new Message.Builder()

				// If multiple messages are sent using the same
				// .collapseKey()
				// the android target device, if it was offline during
				// earlier message
				// transmissions, will only receive the latest message
				// for that key when
				// it goes back on-line.
				// .collapseKey(collapseKey)
				.timeToLive(30).delayWhileIdle(true)
				.addData("message", "Resultado de transferencia: Pago Realizado satisfactoriamente\n"+
									"Servicio: "+servicio+"\n"+
									"Numero de Tarjeta: "+numeroTarjeta+"\n"+
									"Monto en soles: S/. "+monto+" nuevo(s) sole(s) \n"+
									"Numero de Transferencia: "+(new Random().nextInt(1565165))
									).build();
		System.out.println(message);
		System.out.println(message.getCollapseKey());
		System.out.println(message.getData());
		Result result;
		try {
			result = sender.send(message, registrationId, 5);
			String status = "Sent message to one device: " + result;
			System.out.println(status);

			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
