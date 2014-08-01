package at.tugraz.kmi.medokyservice.bl;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.classifications.ClassificationManager;
import at.tugraz.kmi.medokyservice.classifications.UserClassification;
import at.tugraz.kmi.medokyservice.datapreprocessing.UserManager;
import at.tugraz.kmi.medokyservice.recommendations.Recommendation;
import at.tugraz.kmi.medokyservice.recommendations.Recommendations;
import at.tugraz.kmi.medokyservice.resources.LMSLayerIO;

public  class UserClassificationThread extends Thread{


	String userId; 
	UserManager userManager;
	LMSLayerIO lmsLayerIO;
	String courseId;
	Recommendations recommendations;
	
		UserClassificationThread(String courseId, String userId, LMSLayerIO lmsLayerIO){
			this.userId = userId;
			this.lmsLayerIO = lmsLayerIO;
			this.courseId = courseId;
			this.recommendations = new Recommendations();
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run(){
			// FIXME change to exceptions for errors
			ArrayList<UserClassification> classifications = ClassificationManager.getClassifications(this.userId);
			for (UserClassification classification : classifications){
				Recommendation recommendation = classification.calculate(this.courseId);
				if (recommendation != null)
					this.recommendations.addRecommendation(classification.calculate(this.courseId));
			}		
			this.recommendations.setComplete();
			this.lmsLayerIO.sendRecommendation(this.courseId, this.userId, this.recommendations);
		}
			
			
		private void updateUserData(){
			// think about when an update of user data will be valuable
		}
	}
