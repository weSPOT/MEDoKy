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

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import at.tugraz.kmi.medokyservice.rec.Recommendations;

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
