package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningPeer extends UserClassification {

	@Override
	public Recommendation calculate(String courseId, String userId) {
			Recommendation recommendation = new Recommendation();
			recommendation.setType(RecommendationClassification.LearningPeer);
			recommendation.setRecommendation("Why don't you discuss your topic with xyz ");
			recommendation.setExplanation("This is to get help from other students");
			recommendation.setLink("http://www.tugraz.at");
		return recommendation;
	}

}
