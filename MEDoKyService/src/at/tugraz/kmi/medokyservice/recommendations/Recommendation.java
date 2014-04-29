package at.tugraz.kmi.medokyservice.recommendations;


public class Recommendation{

    private RecommendationClassification type;
    private String recommendationText;
    private String explanation;
    
    
    public Recommendation (){}
    
    Recommendation(String text){
    	this.recommendationText = text;
    }
    
    public RecommendationClassification getType() {
		return type;
	}

	public void setType(RecommendationClassification type) {
		this.type = type;
	}

    public void setRecommendation(String recommendation) {
        this.recommendationText = recommendation;
}	
    
    public String getRecommendation() {
            return recommendationText;
    }

    public void setExplanation(String explanation){
    	this.explanation = explanation;
    }

    
    public String getExplanation(){
    	return explanation;
    }
   
}
