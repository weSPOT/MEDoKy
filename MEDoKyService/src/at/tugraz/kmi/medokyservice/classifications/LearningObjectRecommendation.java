package at.tugraz.kmi.medokyservice.classifications;

import java.util.Set;
import at.tugraz.kmi.medokyservice.fca.db.domainmodel.LearningObject;


import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class LearningObjectRecommendation extends UserClassification {

	public LearningObjectRecommendation(String userId){
		super(userId, RecommendationClassification.LearningResource);
	}
	
	@Override
	public Set<Recommendation> calculate(String inquiryId) {
		//Recommendation recommendation = new Recommendation();
		this.runFCARecommendation(inquiryId); 
		
		
		
		//recommendation.setRecommendation("Take a look at this resource");
		//recommendation.setExplanation("This article is related to a concept in your learning context you have not learned yet.");
		
		/*int rnd = (int) (Math.random() * ( 4 - 0));
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
		 }*/
		
		return this.recommendations;
	}
	
	private void runFCARecommendation(String inquiryId){
		FCARecommendation fcaRec = new FCARecommendation(); 
		// TODO: check whether inquiry and userId really are long values??
		Set<LearningObject> los = fcaRec.calculateLoRecommendation(Long.valueOf(inquiryId), Long.valueOf(this.userId));
		for (LearningObject lo : los){
			Recommendation recommendation = new Recommendation(inquiryId, this.type);
			recommendation.setRecommendation("Take a look at this resourse.");
			recommendation.setExplanation("This resource is related to a concept in your learning context you have not learned yet.");
			recommendation.setLink(lo.getData());
			recommendation.setLinkTitle(lo.getName());
			this.recommendations.add(recommendation);
		}
	} 

}
