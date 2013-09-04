package at.tugraz.kmi.medokyservice.resources;

import java.beans.Beans;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Info extends Beans{
	  private String info;
	    	    
	    public Info(){
	    	this.info = "UNSET";
	    }
	    
	    public Info(String text){
	    	this.info  = text;
	    }
	    
	    public String getInfo() {
	            return info;
	    }

	    public void setInfo(String infoText) {
	            this.info = infoText;
	    }
}
