package at.tugraz.kmi.medokyservice.classifications;

import java.util.HashSet;
import java.util.Set;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public abstract class UserClassification {
	
	protected String userId;
	protected RecommendationClassification type;
	protected Set<Recommendation> recommendations;
	
	public UserClassification(String userId, RecommendationClassification type){
		this.userId = userId;
		this.type = type;
		this.recommendations = new HashSet<Recommendation>();
	}
	
	public abstract Set<Recommendation> calculate(String courseId); 
}
