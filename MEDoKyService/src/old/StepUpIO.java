/*
 * 
 * MEDoKyService:
 * A web service component for learner modelling and learning recommendations.
 * Copyright (C) 2015 KTI, TUGraz, Contact: simone.kopeinik@tugraz.at
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Affero General Public License for more details.  
 * For more information about the GNU Affero General Public License see <http://www.gnu.org/licenses/>.
 * 
 */
package old;

import java.util.ArrayList;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

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
