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
package at.tugraz.kmi.medokyservice.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * adapted from: http://eclipsesource.com/blogs/2012/11/02/integrating-gson-into-a-jax-rs-based-application/
 * */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class GsonHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

  private Gson gson;

  private Gson gson() {
    if (gson == null)
      gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
    return gson;
  }

  @Override
  public boolean isReadable(Class<?> type, Type genericType, java.lang.annotation.Annotation[] annotations,
      MediaType mediaType) {
    return true;
  }

  @Override
  public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
    InputStreamReader in = new InputStreamReader(entityStream, Charset.forName("UTF-8"));
    if (type.equals(genericType))
      genericType = type;
    try {
      Object result = gson().fromJson(in, genericType);
      return result;
    } catch (Throwable t) {
      t.printStackTrace();
      throw new IOException(t.getMessage());
    } finally {
      in.close();
    }
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return true;
  }

  @Override
  public long getSize(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return -1;
  }

  @Override
  public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException,
      WebApplicationException {
    OutputStreamWriter writer = new OutputStreamWriter(entityStream, Charset.forName("UTF-8"));
    if (type.equals(genericType))
      genericType = type;
    try {
      gson().toJson(object, genericType, writer);
    } catch (Throwable t) {
      t.printStackTrace();
      new IOException(t.getMessage());
    } finally {
      writer.close();
    }
  }
}