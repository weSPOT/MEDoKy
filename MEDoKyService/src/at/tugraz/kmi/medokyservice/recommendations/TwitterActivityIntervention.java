package at.tugraz.kmi.medokyservice.recommendations;

public class TwitterActivityIntervention extends Intervention {

	public TwitterActivityIntervention(int durationInSec, int intensityLevel,
			RecommendationClassification type) {
		super(durationInSec, intensityLevel, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean trigger() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "This is a first easy Intervention to demonstrate the behavior based on Twitter data.";
	}

}
