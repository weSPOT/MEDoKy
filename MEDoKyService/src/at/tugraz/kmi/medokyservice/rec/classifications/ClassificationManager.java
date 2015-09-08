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
package at.tugraz.kmi.medokyservice.rec.classifications;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.rec.RecommendationClassification;
import at.tugraz.kmi.medokyservice.rec.resources.LearningObjectRecommendation;
import at.tugraz.kmi.medokyservice.rec.resources.UserClassification;

public class ClassificationManager {


	public static ArrayList<UserClassification> getClassifications(String userId, int number){
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		classifications.add(new LearningObjectRecommendation(userId, number));
	//	classifications.add(new Badges());
		return classifications;
	}

		
	public static ArrayList<UserClassification> getDummyClassifications(int number, String userId) {
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		
		
		int count=0;
		
		while (count<number){
	
			switch (count) {
	            case 0: classifications.add(new LearningObjectRecommendation(userId, 1));
	                    break; 
	            case 1: classifications.add(new LearningActivityRecommendation(userId));
	            		break;
	            case 2: classifications.add(new LearningPeerRecommendation(userId));
	            		break;
	            default:classifications.add(new LearningObjectRecommendation(userId, 1));
	            		break;
			 }
			count++;
		}
		return classifications;
	}


	public static ArrayList<UserClassification> getClassifications(
			String userId, String type) {
		RecommendationClassification recType;
		try{
			recType = RecommendationClassification.valueOf(type);
		} catch (IllegalArgumentException exception) {
			recType = RecommendationClassification.LearningResource;
		} 
		
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		
		switch (recType) {
		    case LearningResource:{
		   		classifications.add(new LearningObjectRecommendation(userId, 1));
		    	}
		    break;
		    case LearningActivity:
		    	classifications.add(new LearningActivityRecommendation(userId));
			break;
		    case LearningPeer:
		    	classifications.add(new LearningPeerRecommendation(userId));
			break;
		}
		return classifications;
	}



//	public static void main(String[] args) throws Exception {
//		ArrayList<UserClassification> classes = ClassificationManager.getClassifications("5", "LearningActivity");
//		for (UserClassification myclass : classes )
//			System.out.println(myclass.toString());
//				for (UserClassification uClass : classes){
//			Recommendation rec = uClass.calculate("10", "132");
//			System.out.println(rec.getRecommendation());
//			
//		}	
//	}

}