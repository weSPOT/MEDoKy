package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;

public class ClassificationManager {


	public static ArrayList<UserClassification> getClassifications(String userId){
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
	//	classifications.add(new Communication());
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
	            case 0: classifications.add(new LearningObject(userId));
	                    break; 
	            case 1: classifications.add(new LearningActivity(userId));
	            		break;
	            case 2: classifications.add(new LearningPeer(userId));
	            		break;
	            default:classifications.add(new LearningObject(userId));
	            		break;
			 }
			count++;
		}
		return classifications;
	}



//	public static void main(String[] args) throws Exception {
//		ClassificationManager man = new ClassificationManager();
//		ArrayList<UserClassification> classes = man.getDummyClassifications(5);
//		for (UserClassification uClass : classes){
//			Recommendation rec = uClass.calculate("10", "132");
//			System.out.println(rec.getRecommendation());
//			
//		}	
//	}

}