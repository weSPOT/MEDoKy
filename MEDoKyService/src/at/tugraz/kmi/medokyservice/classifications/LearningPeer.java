package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningPeer extends UserClassification {

	public LearningPeer(String userId) {
		super(userId, RecommendationClassification.LearningPeer);
	}

	@Override
	public Recommendation calculate(String courseId) {
			recommendation.setRecommendation("Why don't you discuss your findings with Jane Doe");
			recommendation.setExplanation("Jane Doe has read similar resorces than you. You may find it helpful to share your perspectives");
		return recommendation;
	}

}
