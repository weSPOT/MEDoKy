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
package at.tugraz.kmi.medokyservice.rec;
import java.sql.Timestamp;
import java.util.Date;

import at.tugraz.kmi.medokyservice.fca.db.IDGenerator;
import at.tugraz.kmi.medokyservice.rec.bl.CoreLogic;
import at.tugraz.kmi.medokyservice.rec.resources.RecommendationId;

public class Recommendation{

    private RecommendationClassification type;
    private String recommendationText;
    private String explanation;
    private String link;
    private String linkTitle;
    private String id;
    private String userId;
    private String recommendationAlgorithm;
    private long learningObjectId;
    

	public Recommendation (String userId, RecommendationClassification type, String algo){
		 this.type = type; 
		 this.userId = userId;
		 this.id = userId+this.type.toString()+IDGenerator.getInstance().getID();
		 this.recommendationAlgorithm = algo;
		 System.out.println(this.id);
	}
    /*
    public Recommendation(String text, String userId){
    	this.id = userId+this.type.toString();
    	this.recommendationText = text;
    }*/
    
    public String getRecommendationAlgorithm() {
		return recommendationAlgorithm;
	}

	public void setRecommendationAlgorithm(String recommendationAlgorithm) {
		this.recommendationAlgorithm = recommendationAlgorithm;
	}
	
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
