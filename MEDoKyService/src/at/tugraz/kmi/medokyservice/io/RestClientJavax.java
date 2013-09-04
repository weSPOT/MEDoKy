package at.tugraz.kmi.medokyservice.io;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class RestClientJavax
{

    public static void main(String[] args) throws IOException {
    	 ClientConfig config = new DefaultClientConfig();
    	 Client client = Client.create(config);
    	 WebResource webResource = client.resource(UriBuilder.fromUri("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses").build());
    	 MultivaluedMap formData = new MultivaluedMapImpl();
    	 formData.add("page", "0");
    	 ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, formData);
    	 System.out.println("Response " + response.getEntity(String.class));
    	}  
	}
