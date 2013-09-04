package at.tugraz.kmi.medokyservice.io;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import at.tugraz.kmi.medokyservice.datapreprocessing.UserManager;


// tutorial: http://wiki.fasterxml.com/JacksonInFiveMinutes
// api: http://fasterxml.github.com/jackson-core/javadoc/2.1.1/

public class JsonReader {

	private static final boolean String = false;
	JsonFactory jsonFactory;
	ArrayList<String> categories;
	ArrayList<String> sources;
	ArrayList<String> users;
	UserManager userManager;
	
	public JsonReader(){
		jsonFactory = new JsonFactory();
		categories = new ArrayList<String>();
		sources = new ArrayList<String>();
		userManager = new UserManager();
		users = new ArrayList<String>();
	}
	
	public UserManager getUserManager(){
		return userManager;
	}
	
	public boolean parseUserActivity(URL url){
	
		int counter = 0;
		
		try {
			JsonParser jp = jsonFactory.createJsonParser(url);
			jsonFactory.enable(JsonParser.Feature.ALLOW_COMMENTS);
			jp.nextToken();			
			while (jp.nextToken() != JsonToken.END_ARRAY){
				counter++;
				System.out.println("set: "+counter);
				
				if (counter == 1)
					System.out.println("set: "+counter);
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					
					String fieldname = jp.getCurrentName();
//					jp.nextToken(); // move to value
					if ("category".equals(fieldname)) {
						jp.nextToken();
						if(!this.categories.contains(jp.getText())){
							categories.add(jp.getText());
						}
						System.out.println(fieldname+" = "+jp.getText());
					}
					else if ("source".equals(fieldname)){
						jp.nextToken();
						if(!this.sources.contains(jp.getText())){
							sources.add(jp.getText());
						}
						System.out.println(fieldname+" = "+jp.getText());
					}
					else if ("username".equals(fieldname)){
						jp.nextToken();
						if(!this.users.contains(jp.getText())){
							users.add(jp.getText());
						}
						System.out.println(fieldname+" = "+jp.getText());
					}
					else if ("starttime".equals(fieldname)){
						jp.nextToken();
//						if(!this.sources.contains(jp.getText())){
//							sources.add(jp.getText());
//						}
						System.out.println(fieldname+" = "+jp.getText());
					}
					
					
//					System.out.println("category= "+jp.getCurrentName()+"  "+jp.getText());
//				} else if ("source".equals(namefield)) {
//					System.out.println("source: "+jp.getText());
//				}					
				//System.out.println(namefield);
//				jp.nextToken();
//				if ()
//				//System.out.println("Name: "+jp.getCurrentName()+" , text: "+jp.getText()+" ,value as String: "+jp.getValueAsString());
				}
			}	
			//System.out.println(jp.getText());
			} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (counter<=1)
			return false;
		return true;
			
	}
	
	public void printMetaInf(){
		System.out.println("///////////////////////////////////////////////////////////////////////////////////////");
		int count = 0;
		for(String cat:categories){
			count++;
			System.out.println(count+". category= "+cat);
		}
		System.out.println("///////////////////////////////////////////////////////////////////////////////////////");
	
		count = 0;
		for(String cat:sources){
			count++;
			System.out.println(count+". source= "+cat);
		}
	}
	
	public static void main(String[] args) {
		
		JsonReader json = new JsonReader();
		
		try {
			int page = 1;
			System.out.println("PAGE: "+page);
			while (json.parseUserActivity(new URL("http://ariadne.cs.kuleuven.be/stepup/rest/getopendb?query=select%20*%20from%20action%20where%20username%3D%27bram_gotink%27&pag="+page))){
				page ++;
				System.out.println("PAGE: "+page);
			}
			json.printMetaInf();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
