package at.tugraz.kmi.medokyservice.fca.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * Enable CORS for all requests This is done via filter for 2 reasons: 1. No
 * changes to the resources needed 2. Adaptive header field based on request
 * 
 * @author Bernd Prünster <bernd.pruenster@gmail.com>
 * 
 */
public class CORSFilter implements ContainerResponseFilter,
    ContainerRequestFilter {

  /**
   * adds a CORS header to all responses
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ContainerResponse filter(ContainerRequest arg0, ContainerResponse arg1) {
    String origin = arg0.getHeaderValue("Origin");
    System.out.println("CORS " + arg0.getAbsolutePath());
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

    List ach = arg0.getRequestHeader("Content-Type");
    if (ach == null)
      ach = new ArrayList();
    ach.add(MediaType.APPLICATION_JSON);
    arg0.getRequestHeaders().put("Content-Type", ach);
    return arg0;
  }

}
