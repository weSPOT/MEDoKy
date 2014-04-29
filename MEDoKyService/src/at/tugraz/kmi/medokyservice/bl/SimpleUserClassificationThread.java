package at.tugraz.kmi.medokyservice.bl;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.classifications.ClassificationManager;
import at.tugraz.kmi.medokyservice.classifications.UserClassification;
import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.Recommendations;

public class SimpleUserClassificationThread extends Thread{


	String userId; 
	String courseId;
	String environment;
	String recommendationId;
	Recommendations recommendations;	
	int number;
	
	
	
		SimpleUserClassificationThread(String userId, String courseId, int number, String environment, String recommendationId){
			this.userId = userId;
			this.courseId = courseId;
			this.environment = environment;
			this.recommendations = new Recommendations();
			this.recommendationId = recommendationId;
			this.number = number;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run(){
			ArrayList<UserClassification> classifications = ClassificationManager.getDummyClassifications(number);
			for (UserClassification classification : classifications){
				Recommendation recommendation = classification.calculate(this.courseId, this.userId);
				if (recommendation != null)
					this.recommendations.addRecommendation(recommendation);
			}
			
			CoreLogic.getInstance().setRecommendation(this.recommendationId, this.recommendations);
			this.recommendations.setComplete();
		}
	
		private void print(String text){
			System.out.println("Class: SimpleUserClassification: "+text);
		}
	}
