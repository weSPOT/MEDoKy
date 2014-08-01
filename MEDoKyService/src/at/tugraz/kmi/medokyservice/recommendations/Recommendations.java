package at.tugraz.kmi.medokyservice.recommendations;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper; 

@XmlRootElement
public class Recommendations extends Beans{

    private List<Recommendation> recommendations;
    private String status;
        
    public Recommendations(){
    	this.recommendations = new ArrayList<Recommendation>();
    	this.status = "pending";
    }

    
    public void setComplete(){
    	this.status = "complete";
    }
    
    public void setStatus(String status){
    	this.status = status;
    }
    
    @XmlElement 
    public String getStatus(){
    	return this.status;
    }
    
    public void addRecommendation(Recommendation recommendation){
    	this.recommendations.add(recommendation);
    }
    
    /*public void addRecommendation(String text){
    	this.recommendations.add(new Recommendation(text));
    }*/
    
    public void setRecommendations(List<Recommendation> recommendations){
    	this.recommendations = recommendations;
    }
    
   
    @XmlElement 
    public List<Recommendation> getRecommendations(){
    	return this.recommendations;
    }
}
