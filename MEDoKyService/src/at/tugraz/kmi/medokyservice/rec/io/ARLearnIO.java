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
package at.tugraz.kmi.medokyservice.rec.io;

import java.io.IOException;

import old.LMSLayerIO;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import at.tugraz.kmi.medokyservice.rec.Recommendations;

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
			e.printStackTrace();
			return false;
		} catch (IOException e) {
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
