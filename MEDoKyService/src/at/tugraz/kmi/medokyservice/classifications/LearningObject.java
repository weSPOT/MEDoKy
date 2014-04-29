package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningObject extends UserClassification {

	@Override
	public Recommendation calculate(String courseId, String userId) {
		Recommendation recommendation = new Recommendation();
		recommendation.setType(RecommendationClassification.LearningResource);
		recommendation.setRecommendation("Take a look at this learning material");
		recommendation.setExplanation("This is to improve guide you through information");
		return recommendation;
	}

}
