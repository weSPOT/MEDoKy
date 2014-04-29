package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;

import at.tugraz.kmi.medokyservice.recommendations.Recommendation;

public class ClassificationManager {


	public static ArrayList<UserClassification> getClassifications(){
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		classifications.add(new Communication());
		classifications.add(new Badges());
		return classifications;
	}

	public static ArrayList<UserClassification> getDummyClassifications(
			int number) {
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		
		
		int i=0;
		
		while (i<number){
			System.out.println();
			int rnd = (int) (Math.random() * ( 3 - 0));
			System.out.println(rnd);
			switch (rnd) {
	            case 0:  classifications.add(new LearningActivity());
	                     break; 
	            case 1: classifications.add(new LearningObject());
	            		break;
	            case 2: classifications.add(new LearningPeer());
			 }
			i++;
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