package at.tugraz.kmi.medokyservice.classifications;

import java.util.Set;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningPeerRecommendation extends UserClassification {

	public LearningPeerRecommendation(String userId) {
		super(userId, RecommendationClassification.LearningPeer);
	}

	@Override
	public Set<Recommendation> calculate(String courseId) {
			Recommendation recommendation = new Recommendation(courseId, this.type); 
			recommendation.setRecommendation("Why don't you discuss your findings with Jane Doe");
			recommendation.setExplanation("Jane Doe has read similar resorces than you. You may find it helpful to share your perspectives");
			this.recommendations.add(recommendation);
		return this.recommendations;
	}

}
