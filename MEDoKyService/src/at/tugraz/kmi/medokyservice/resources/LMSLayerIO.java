package at.tugraz.kmi.medokyservice.resources;

import at.tugraz.kmi.medokyservice.recommendations.Recommendations;

public interface LMSLayerIO {

	boolean sendRecommendation(String courseId, String userId,
			Recommendations recommendations);
}
