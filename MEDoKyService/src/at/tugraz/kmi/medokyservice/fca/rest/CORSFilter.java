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
package at.tugraz.kmi.medokyservice.fca.rest;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * Enable CORS for all requests This is done via filter for 2 reasons: 1. No
 * changes to the resources needed 2. Adaptive header field based on request
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 */
public class CORSFilter implements ContainerResponseFilter, ContainerRequestFilter {

  /**
   * adds a CORS header to all responses
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ContainerResponse filter(ContainerRequest arg0, ContainerResponse arg1) {
    String origin = arg0.getHeaderValue("Origin");
    if (origin != null)
      arg1.getHttpHeaders().add("Access-Control-Allow-Origin", origin);
    List ach = arg1.getHttpHeaders().get("Access-Control-Allow-Headers");

    if (ach == null)
      ach = new ArrayList();
    ach.add("Content-Type");
    arg1.getHttpHeaders().put("Access-Control-Allow-Headers", ach);
    return arg1;
  }

  /**
   * adds a JSON Content-Type header to all incoming requests, because Microsoft
   * Internet Explorer does not support setting a Content-Type other than
   * text/plain to CORS requests
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ContainerRequest filter(ContainerRequest arg0) {

    return arg0;
  }

}
