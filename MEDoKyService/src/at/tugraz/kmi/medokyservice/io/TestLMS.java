package at.tugraz.kmi.medokyservice.io;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import at.tugraz.kmi.medokyservice.recommendations.Recommendations;
import at.tugraz.kmi.medokyservice.resources.LMSLayerIO;

import com.sun.jersey.api.client.Client;

public class TestLMS implements LMSLayerIO {

	Client client;
	
	public TestLMS(){
		client = Client.create();	
	}
	
	
	@Override
	public boolean sendRecommendation(String courseId, String userId, Recommendations recommendations) {
		ObjectMapper mapper = new ObjectMapper();
		String input = "";
		try {
			System.out.println(mapper.writeValueAsString(recommendations));
			input = mapper.writeValueAsString(recommendations);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		System.out.println("Result = "+input);
		return true;
	}
}
