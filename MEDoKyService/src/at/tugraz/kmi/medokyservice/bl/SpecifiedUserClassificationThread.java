package at.tugraz.kmi.medokyservice.bl;

import java.util.ArrayList;
import java.util.Set;

import at.tugraz.kmi.medokyservice.classifications.ClassificationManager;
import at.tugraz.kmi.medokyservice.classifications.UserClassification;
import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.Recommendations;

public class SpecifiedUserClassificationThread extends Thread{


	String userId; 
	String courseId;
	String environment;
	String recommendationId;
	String type;
	Recommendations recommendations;	
	
	SpecifiedUserClassificationThread(String userId, String courseId, String environment, String type, String recommendationId){
		this.userId = userId;
		this.courseId = courseId;
		this.environment = environment;
		this.recommendations = new Recommendations();
		this.recommendationId = recommendationId;
		this.type = type;
	}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run(){
			//ArrayList<UserClassification> classifications = ClassificationManager.getDummyClassifications(number, userId);
			ArrayList<UserClassification> classifications = ClassificationManager.getClassifications(userId, type);
			for (UserClassification classification : classifications){
				Set<Recommendation> recommendations = classification.calculate(this.courseId);
				for (Recommendation r : recommendations)
					this.recommendations.addRecommendation(r);
			}
			
			CoreLogic.getInstance().setRecommendation(this.recommendationId, this.recommendations);
			this.recommendations.setComplete();
		}
	
		private void print(String text){
			System.out.println("Class: SimpleUserClassification: "+text);
		}
	}
