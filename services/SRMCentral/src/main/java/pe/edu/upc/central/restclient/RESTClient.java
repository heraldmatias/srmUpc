package pe.edu.upc.central.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@SuppressWarnings("deprecation")
public abstract class RESTClient {
	
	public static final String FORMAT = "application/json";
	
	protected String getConexion(String url, String format) throws ClientProtocolException,	IOException {
		Client client = Client.create();
		WebResource webResource2 = client.resource(url);
		ClientResponse response2 = webResource2.accept(format).get(ClientResponse.class);
		if (response2.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response2.getStatus());
		}
		String output2 = response2.getEntity(String.class);
		System.out.println("\n============get data============");
		System.out.println(output2);
		return output2;
	}
	@SuppressWarnings({ "resource" })
	protected String postConexion(String url, String format, String jsonObject) throws ClientProtocolException,	IOException {
		HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity input = new StringEntity(jsonObject); //here instead of JSON you can also have XML
        input.setContentType(format);
        post.setEntity(input);
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line += rd.readLine()) != null) {
            System.out.println(line);
        }
		return line;
	}
	
}
