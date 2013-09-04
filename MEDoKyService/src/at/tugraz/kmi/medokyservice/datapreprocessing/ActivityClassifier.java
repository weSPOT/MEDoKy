package at.tugraz.kmi.medokyservice.datapreprocessing;

public enum ActivityClassifier {

	blog, 
	twitter,
	stepup,
	atinyarm,
	awarded,
	undefined;

	public static ActivityClassifier getFromString(String activityType) {
		if (activityType.equals("blog"))
			return blog;
		if (activityType.equals("twitter"))
			return twitter;
		if (activityType.equals("stepup"))
			return stepup;
		if (activityType.equals("atinyarm"))
			return atinyarm;
		if (activityType.equals("awarded"))
			return awarded;
			
		return undefined;
	}
}
