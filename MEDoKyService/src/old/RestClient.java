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
