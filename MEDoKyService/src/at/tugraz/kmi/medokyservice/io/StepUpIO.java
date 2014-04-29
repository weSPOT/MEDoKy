package at.tugraz.kmi.medokyservice.io;

import java.util.ArrayList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import at.tugraz.kmi.medokyservice.resources.LALayerIO;

public class StepUpIO implements LALayerIO {

	Client client;
	
	public StepUpIO(){
		client = Client.create();
	}
	
	@Override
	public ArrayList<String> getCourses() {
		String input;  
		String response;
		ArrayList<String> courses = new ArrayList<String>();
		int page = 0;
		while (true){
			input = "{\"pag\":\""+page+"\"}";
			response = this.performPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses", input);
			if (response.length()<=2)
				break;
			
			courses.add(response);
			page++;
		}
		return courses;
	}

	@Override
	public ArrayList<String> getStudents(String courseName) {
		String input;  
		String response;
		ArrayList<String> students = new ArrayList<String>();
		int page = 0;
		while (true){
			input = "{\"pag\":\""+page+"\"}";
			response = this.performPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/"+courseName, input);
			if (response.length()<=2)
				break;
			students.add(response);
			page++;
		}
		return students;
	}

	@Override
	public ArrayList<String> getStudentInfo(String courseName, String studentName) {
		String input;  
		String response;
		ArrayList<String> studentInfos = new ArrayList<String>();
		studentName = studentName.replace(" ", "%20");
		if (studentName.contains("http")){
			System.out.println(studentName);
			return studentInfos; 
		}	
		int page = 0;
		while (true){
			input = "{\"pag\":\""+page+"\"}";
			response = this.performPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/"+courseName+"/"+studentName, input);
			if (response.length()<=2){
				break;
			}	
			studentInfos.add(response);
			page++;
		}
		return studentInfos;
	}

	private String performPost(String address, String input){
		
		ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,Boolean.TRUE);
        ClientResponse response = null;
        try{
        	System.err.println("web address "+address);
        	WebResource webResource = client.resource(address);
			response = webResource.type("application/json").post(ClientResponse.class, input);
		}
		catch(Exception e){
			
			System.err.println("Exception web address "+address);
			e.printStackTrace();
			return "";
		}
		
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
		     + response.getStatus());
		}

		//System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		//System.out.println(output);
		return output;	
	}

	
}
