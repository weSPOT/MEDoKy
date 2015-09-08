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
