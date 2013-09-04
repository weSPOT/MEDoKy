package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;

public abstract class UserClassification {
	
	private Recommendation recommendation;
	
	public UserClassification(){
		this.recommendation = new Recommendation();
	}
	
	public abstract Recommendation calculate(String courseId, String userId); 
}
