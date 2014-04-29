package at.tugraz.kmi.medokyservice.io;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import at.tugraz.kmi.medokyservice.recommendations.Recommendations;
import at.tugraz.kmi.medokyservice.resources.LMSLayerIO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class ARLearnIO implements LMSLayerIO {

	Client client;
	
	public ARLearnIO(){
		client = Client.create();	
	}
	
	
	@Override
	public boolean sendRecommendation(String courseId, String userId, Recommendations recommendations) {
		ObjectMapper mapper = new ObjectMapper();
		try {
		
			System.out.println(mapper.writeValueAsString(recommendations));
			String input = mapper.writeValueAsString(recommendations);
			this.performPost("http://wespot-arlearn.appspot.com/rest/MEDoKyCallback/userId/"+userId+"/courseId/"+courseId, input);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }		
		System.out.println("recommendation sent!!");
		return true;
	}
	
	
	private String performPost(String address, String input){
		
		ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
		WebResource webResource = client.resource(address);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
		     + response.getStatus());
		}

		String output = response.getEntity(String.class);
		System.out.println(output);
		return output;	
	}	
}
