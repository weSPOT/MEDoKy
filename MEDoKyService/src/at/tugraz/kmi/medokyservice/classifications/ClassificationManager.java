package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.recommendations.RecommendationClassification;

public class ClassificationManager {


	public static ArrayList<UserClassification> getClassifications(String userId){
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		classifications.add(new LearningObjectRecommendation(userId));
	//	classifications.add(new Badges());
		return classifications;
	}

		
	public static ArrayList<UserClassification> getDummyClassifications(int number, String userId) {
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		
		
		int count=0;
		
		while (count<number){
			System.out.println();
			//int rnd = 0; //(int) (Math.random() * ( 3 - 0));
			//System.out.println(rnd);
			switch (count) {
	            case 0: classifications.add(new LearningObjectRecommendation(userId));
	                    break; 
	            case 1: classifications.add(new LearningActivityRecommendation(userId));
	            		break;
	            case 2: classifications.add(new LearningPeerRecommendation(userId));
	            		break;
	            default:classifications.add(new LearningObjectRecommendation(userId));
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
		    case LearningResource:
		    	classifications.add(new LearningObjectRecommendation(userId));
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