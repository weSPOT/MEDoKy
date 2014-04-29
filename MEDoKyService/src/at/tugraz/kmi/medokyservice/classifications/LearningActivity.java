package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningActivity extends UserClassification {

	@Override
	public Recommendation calculate(String courseId, String userId) {
		// TODO Auto-generated method stub
		Recommendation recommendation = new Recommendation();
		recommendation.setType(RecommendationClassification.LearningActivity);
		recommendation.setRecommendation("Do a learning activity");
		recommendation.setExplanation("This is to improve your learning");
		return recommendation;
	}

}
