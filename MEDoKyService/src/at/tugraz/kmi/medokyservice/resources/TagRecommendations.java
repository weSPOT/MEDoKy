package at.tugraz.kmi.medokyservice.resources;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;

@XmlRootElement
public class TagRecommendations extends Beans{
	
	private List<String> recommendations; 
	
	public TagRecommendations(){
		this.recommendations = new ArrayList<String>();
	}
	 
	public void setRecommendations(){
		for (int i=0; i<6; i++)
			recommendations.add("tag "+i);
	}
	
    @XmlElement 
    public List<String> getRecommendations(){
    	return this.recommendations;
    }

}
