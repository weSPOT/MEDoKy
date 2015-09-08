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

import at.tugraz.kmi.medokyservice.rec.Recommendation;
import at.tugraz.kmi.medokyservice.rec.Recommendations;
import at.tugraz.kmi.medokyservice.rec.classifications.ClassificationManager;
import at.tugraz.kmi.medokyservice.rec.resources.UserClassification;

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
