package pe.edu.upc.central.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public final class GCMUtil {

	private static final Logger logger = LoggerFactory.getLogger(GCMUtil.class);
	private static final Executor threadPool = Executors.newFixedThreadPool(5);

	public static Sender newSender(ServletConfig config) {
		String key = (String) config.getServletContext().getAttribute(
				ApiKeyInitializer.ATTRIBUTE_ACCESS_KEY);
		System.out.println(key);
		return new Sender(key);
	}

	public static void asyncSend(List<String> partialDevices,
			final Sender sender) {
		// make a copy
		final List<String> devices = new ArrayList<String>(partialDevices);
		threadPool.execute(new Runnable() {

			public void run() {
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

				MulticastResult multicastResult;
				try {
					multicastResult = sender.send(message, devices, 5);
				} catch (IOException e) {
					logger.info("Error posting messages", e);
					return;
				}
				List<Result> results = multicastResult.getResults();
				// analyze the results
				for (int i = 0; i < devices.size(); i++) {
					String regId = devices.get(i);
					Result result = results.get(i);
					String messageId = result.getMessageId();
					if (messageId != null) {
						logger.info("Succesfully sent message to device: "
								+ regId + "; messageId = " + messageId);
						String canonicalRegId = result
								.getCanonicalRegistrationId();
						if (canonicalRegId != null) {
							// same device has more than on registration id:
							// update it
							logger.info("canonicalRegId " + canonicalRegId);
							Datastore.updateRegistration(regId, canonicalRegId);
						}
					} else {
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							// application has been removed from device -
							// unregister it
							logger.info("Unregistered device: " + regId);
							Datastore.unregister(regId);
						} else {
							logger.info("Error sending message to " + regId
									+ ": " + error);
						}
					}
				}
			}
		});
	}

}