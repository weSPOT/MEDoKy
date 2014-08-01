package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningActivity extends UserClassification {

	public LearningActivity(String userId) {
		super(userId, RecommendationClassification.LearningActivity);
	}

	@Override
	public Recommendation calculate(String courseId) {
		// TODO Auto-generated method stub
		int rnd = (int) (Math.random() * ( 3 - 0));
		switch (rnd) {
            case 0: recommendation.setRecommendation("Be sure to consider Data from different sources!");
    				recommendation.setExplanation("To get balanced information it is important to consider different data from different sources");
    				recommendation.setLink("http://scholar.google.com");
    				recommendation.setLinkTitle("Search engine");
                    break; 
            case 1: 
        			recommendation.setRecommendation("Before continuing in this phase you may read the help manual!");
        			recommendation.setExplanation("Your initial test scores indicated that you are a novice in this area.");
        			recommendation.setLink("http://dev.inquiry.wespot.net/pages/view/2314/phase-2-operationalisation");
        			recommendation.setLinkTitle("Help");
        			break;
            case 2:	
            	recommendation.setRecommendation("Engage in a blog discussion.");
    			recommendation.setExplanation("You have been very quite lately. Discussing your ideas with other might bring new insights.");
    			recommendation.setLink("http://dev.inquiry.wespot.net/discussion/owner/35744");
    			recommendation.setLinkTitle("Discussion");
		 }
			
		return recommendation;
	}

}
