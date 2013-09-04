package at.tugraz.kmi.medokyservice.fca.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 
 * A generic Exception Mapper for delivering exceptions over the REST interface
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 * @param <E>
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
