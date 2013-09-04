package at.tugraz.kmi.medokyservice.recommendations;


public class Recommendation{

    private String recommendationText;

    public Recommendation (){}
    
    Recommendation(String text){
    	this.recommendationText = text;
    }
    
    public String getRecommendation() {
            return recommendationText;
    }

    public void setRecommendation(String recommendation) {
            this.recommendationText = recommendation;
    }
}
