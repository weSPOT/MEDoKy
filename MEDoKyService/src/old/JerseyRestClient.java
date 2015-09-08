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
