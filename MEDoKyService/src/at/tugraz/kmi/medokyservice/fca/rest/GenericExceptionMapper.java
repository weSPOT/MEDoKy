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

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 
 * A generic Exception Mapper for delivering exceptions over the REST interface
 * 
 * @author Bernd Prünster <mail@berndpruenster.org>
 * 
 * @param <Exception>
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

  /**
   * Returns a HTTP 400 Bad Request containing the simple name of the exception
   * class (to enable automatic parsing) and the exception message itself
   * 
   * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
   */
  @Override
  public Response toResponse(Exception ex) {
    ex.printStackTrace();
    return Response.status(400)
        .entity(ex.getClass().getSimpleName() + ":\n" + ex.getMessage())
        .type("text/plain").build();
  }

}
