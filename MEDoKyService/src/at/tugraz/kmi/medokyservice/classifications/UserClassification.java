package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public abstract class UserClassification {
	
	protected Recommendation recommendation;
	protected String userId;
	
	public UserClassification(String userId, RecommendationClassification type){
		this.userId = userId;
		this.recommendation = new Recommendation(userId, type);
	}
	
	public abstract Recommendation calculate(String courseId); 
}
