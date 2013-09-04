package old;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class JerseyRestClient {
	
	Client client; 
	
	public JerseyRestClient(){
		client = Client.create();
	}
	
	public void readGet(String uri){
		WebResource webRes = client.resource(uri);
		ClientResponse response = webRes.get(ClientResponse.class);
		System.out.println(response.getStatus() );
		System.out.println(response.getHeaders().get("Content-Type") );
		String entity = response.getEntity(String.class);
		System.out.println(entity);
	}
	
	public void readPost(String uri){
		WebResource webRes = client.resource(uri);
		ClientResponse response = webRes.post(ClientResponse.class);
		System.out.println(response.getStatus() );
		System.out.println(response.getHeaders().get("Content-Type") );
		String entity = response.getEntity(String.class);
		System.out.println(entity);
	}
	
	public void readXML(String uri){
		
	}
	
	public static void main (String[] args) throws Exception
	  {
		JerseyRestClient client = new JerseyRestClient();
		client.readGet("http://localhost:8080/MEDoKyService/rest/trigger?userId=15");
		client.readPost("http://localhost:8080/MEDoKyService/rest/trigger?userId=15");
	  }   
}
