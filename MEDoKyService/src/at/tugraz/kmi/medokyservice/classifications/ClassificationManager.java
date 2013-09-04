package at.tugraz.kmi.medokyservice.classifications;

import java.util.ArrayList;

public class ClassificationManager {


	public static ArrayList<UserClassification> getClassifications(){
		ArrayList<UserClassification> classifications= new ArrayList<UserClassification>();
		classifications.add(new Communication());
		classifications.add(new Badges());
		return classifications;
	}
}
