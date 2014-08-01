package at.tugraz.kmi.medokyservice.classifications;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningObject extends UserClassification {

	public LearningObject(String userId){
		super(userId, RecommendationClassification.LearningResource);
	}
	
	@Override
	public Recommendation calculate(String courseId) {
		//Recommendation recommendation = new Recommendation();
		recommendation.setRecommendation("Take a look at this resource");
		recommendation.setExplanation("This article is related to a concept in your learning context you have not learned yet.");
		
		int rnd = (int) (Math.random() * ( 4 - 0));
		switch (rnd) {
            case 0: recommendation.setLink("http://www.ducksters.com/science/photosynthesis.php");
    				recommendation.setLinkTitle("Photosynthesis");
                    break; 
            case 1: recommendation.setLink("https://www.youtube.com/watch?v=kL-9TB4qAho");
    				recommendation.setLinkTitle("Aquatic Ecosystems");
    		   		break;
            case 2: recommendation.setLink("http://www.slideshare.net/ang_ruiz/reproduction-in-animals");
    				recommendation.setLinkTitle("Reproduction");	
    				break;
            case 3: recommendation.setLink("http://www.ducksters.com/animals/mammals.php");
    				recommendation.setLinkTitle("Mammals");
		 }
		
		return recommendation;
	}

}
