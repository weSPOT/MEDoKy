package at.tugraz.kmi.medokyservice.recommendations;
import java.sql.Timestamp;
import java.util.Date;

import at.tugraz.kmi.medokyservice.bl.CoreLogic;
import at.tugraz.kmi.medokyservice.fca.db.IDGenerator;
import at.tugraz.kmi.medokyservice.resources.RecommendationId;

public class Recommendation{

    private RecommendationClassification type;
    private String recommendationText;
    private String explanation;
    private String link;
    private String linkTitle;
    private String id;
    private String userId;
    private long learningObjectId;
    

	public Recommendation (String userId, RecommendationClassification type){
		 this.type = type; 
		 this.userId = userId;
		 this.id = userId+this.type.toString()+IDGenerator.getInstance().getID();
		 System.out.println(this.id);
	}
    /*
    public Recommendation(String text, String userId){
    	this.id = userId+this.type.toString();
    	this.recommendationText = text;
    }*/
    
    public RecommendationClassification getType() {
		return type;
	}


    public void setRecommendation(String recommendation) {
        this.recommendationText = recommendation;
    }	
    
    public String getRecommendation() {
            return recommendationText;
    }

    public void setExplanation(String explanation){
    	this.explanation = explanation;
    }
    
    public String getExplanation(){
    	return explanation;
    }
   
    public void setLink(String link) {
		this.link = link;
	}
    
    public String getLink() {
		return link;
	}
    
    public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
    
    public String getLinkTitle() {
		return linkTitle;
	}
    
	public String getRecommendationText() {
		return recommendationText;
	}

	public void setRecommendationText(String recommendationText) {
		this.recommendationText = recommendationText;
	}

	public String getUuId() {
		return id;
	}

	public void setUuId(String uuId) {
		this.id = uuId;
	}

	public String toString(){
		return "id:"+this.id+", text:"+this.recommendationText;
	}

  public long getLearningObjectId() {
    return learningObjectId;
  }

  public void setLearningObjectId(long learningObjectId) {
    this.learningObjectId = learningObjectId;
  }
	
}
