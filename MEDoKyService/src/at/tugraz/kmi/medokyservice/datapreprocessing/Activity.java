package at.tugraz.kmi.medokyservice.datapreprocessing;

public class Activity {
	String time;
	String source;	
	String description;
	ActivityClassifier classifier;

	public Activity(ActivityClassifier classifier){
		this.classifier = classifier; 
	}
	
//	public Activity(ActivityClassifier classifier, String source, String description, String time){
//		this.source = source;
//		this.description = description;
//		this.time = time;
//		this.classifier = classifier;
//	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ActivityClassifier getClassifier() {
		return classifier;
	}

	public void setClassifier(ActivityClassifier classifier) {
		this.classifier = classifier;
	}

}
