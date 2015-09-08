/*
 * 
 * MEDoKyService:
 * A web service component for learner modelling and learning recommendations.
 * Copyright (C) 2015 KTI, TUGraz, Contact: simone.kopeinik@tugraz.at
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Affero General Public License for more details.  
 * For more information about the GNU Affero General Public License see <http://www.gnu.org/licenses/>.
 * 
 */
package at.tugraz.kmi.medokyservice.rec.bl;

import java.util.ArrayList;
import java.util.Set;

import old.LMSLayerIO;
import at.tugraz.kmi.medokyservice.rec.Recommendation;
import at.tugraz.kmi.medokyservice.rec.Recommendations;
import at.tugraz.kmi.medokyservice.rec.classifications.ClassificationManager;
import at.tugraz.kmi.medokyservice.rec.datapreprocessing.UserManager;
import at.tugraz.kmi.medokyservice.rec.resources.UserClassification;

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
			ArrayList<UserClassification> classifications = ClassificationManager.getClassifications(this.userId, 1);
			for (UserClassification classification : classifications){
				Set<Recommendation> recommendations = classification.calculate(this.courseId);
				for (Recommendation r : recommendations)
					this.recommendations.addRecommendation(r);
			}		
			this.recommendations.setComplete();
			this.lmsLayerIO.sendRecommendation(this.courseId, this.userId, this.recommendations);
		}
			
			
		private void updateUserData(){
			// think about when an update of user data will be valuable
		}
	}
