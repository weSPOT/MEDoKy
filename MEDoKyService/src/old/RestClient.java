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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient
{

  
  public static String doHttpGet (String serverurl) throws Exception
  {
    // check url
    if (serverurl == null || !serverurl.startsWith ("http:"))
    {
      throw new Exception ("wrong url, url=" + serverurl);
    }
    
    // init varaibles
    HttpURLConnection connection = null;
    
    try
    {
      // create the connection
      URL url = new URL (serverurl);
      connection = (HttpURLConnection)url.openConnection ();
      connection.setRequestMethod("GET");
      connection.setDoOutput (false);
      connection.setDoInput (true);
      connection.setConnectTimeout (10000);
      connection.setReadTimeout (10000);
      connection.setUseCaches (false);
      connection.setRequestProperty("Content-Type", "text/plain");      

      connection.connect();
      
      // read from the connection
      StringBuffer buf = new StringBuffer ();
      String line;

      BufferedReader br  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      while ((line = br.readLine()) != null)
        buf.append (line + '\n');
      
      if (connection != null)
        connection.disconnect ();
      
      // the return value
      return buf.toString ();
    }
    catch (Exception exception)
    {
      if (connection != null)
        connection.disconnect ();

      throw new Exception (exception.getMessage ());
    }
  }
  
  public static String doHttpPost (String serverurl, String postdata) throws Exception
  {
    // check url
    if (serverurl == null || !serverurl.startsWith ("http:"))
    {
      throw new Exception ("wrong url, url=" + serverurl);
    }
    
    // init variables
    HttpURLConnection connection = null;
    
    try
    {
      // create the connection
      URL url = new URL (serverurl);
      connection = (HttpURLConnection)url.openConnection ();
      connection.setRequestMethod ("POST");
      connection.setDoOutput (true);
      connection.setDoInput (true);
      connection.setConnectTimeout (10000);
      connection.setReadTimeout (10000);
      connection.setUseCaches (false);
      connection.setRequestProperty("Content-Type", "text/plain");      
      
      // write data to the connection
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
      out.write (postdata);
      out.close();

      // read from the connection
      BufferedReader br  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuffer buf = new StringBuffer ();
      String line;
      while ((line = br.readLine()) != null)
        buf.append (line + '\n');
      
      if (connection != null)
        connection.disconnect ();

      return buf.toString ();
    }
    catch (Exception exception)
    {
      if (connection != null)
        connection.disconnect ();

      throw new Exception (exception.getMessage ());
    }
  }
  

  public static void main (String[] args) throws Exception
  {
    String returndata = null;
    
    // be aware that the return data string contains a newline at the end (see code above)
    
    returndata = RestClient.doHttpGet ("http://localhost:8080/MEDoKyService/rest/trigger");
    System.out.println (returndata);
    
    returndata = RestClient.doHttpGet ("http://localhost:8080/MEDoKyService/rest/trigger?userId=15");
    System.out.println (returndata);
    
    returndata = RestClient.doHttpPost("http://localhost:8080/MEDoKyService/rest/trigger?userId=15", "?userId=15");
    System.out.println (returndata);
//    
//    returndata = RestClient.doHttpGet ("http://localhost:8080/restdemo/admin?propkey=key1");
//    System.out.println (returndata);
    
  }
  
  
  
}
