package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningPeer extends UserClassification {

	@Override
	public Recommendation calculate(String courseId, String userId) {
			Recommendation recommendation = new Recommendation();
			recommendation.setType(RecommendationClassification.LearningPeer);
			recommendation.setRecommendation("Why don't you discuss your findings with Jane Doe");
			recommendation.setExplanation("Jane Doe has read similar resorces than you. You may find it helpful to share your perspectives");
		return recommendation;
	}

}
