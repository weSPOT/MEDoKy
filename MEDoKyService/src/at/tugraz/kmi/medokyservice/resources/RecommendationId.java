package at.tugraz.kmi.medokyservice.resources;


import java.beans.Beans;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RecommendationId extends Beans{
	  private String recommendationId;
	    	    
	    public RecommendationId(){
	    	this.recommendationId = "UNSET";
	    }
	    
	    public RecommendationId(String recommendationId){
	    	this.recommendationId  = recommendationId;
	    }
	    
	    public String getRecommendationId() {
	            return this.recommendationId;
	    }

	    public void setRecommendationId(String recommendationId) {
	            this.recommendationId = recommendationId;
	    }
}
